package ru.pantherapi.scanner.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import ru.pantherapi.model.artifact.ApiArtifact;
import ru.pantherapi.model.contract.ApiContract;
import ru.pantherapi.model.contract.element.ApiContractElement;
import ru.pantherapi.model.contract.element.ApiContractField;
import ru.pantherapi.model.contract.element.ApiContractMethod;
import ru.pantherapi.scanner.annotation.ApiContractInteractionType;
import ru.pantherapi.scanner.annotation.IndexedApiContract;
import ru.pantherapi.scanner.annotation.IndexedApiContractElement;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.element.ElementKind.FIELD;
import static javax.lang.model.element.ElementKind.METHOD;
import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.StandardLocation.CLASS_OUTPUT;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static ru.pantherapi.scanner.annotation.ApiContractInteractionType.APPLIED;
import static ru.pantherapi.scanner.annotation.ApiContractInteractionType.PROVIDED;

@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes("ru.pantherapi.scanner.annotation.IndexedApiContract")
public class IndexedApiAnnotationProcessor
        extends AbstractProcessor {

    private static final String INDEXED_API_CONTRACTS_PATH = "META-INF/api/artifact.json";

    private final ObjectWriter objectWriter = new ObjectMapper()
            .writerWithDefaultPrettyPrinter();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        try {
            var annotatedTypes = annotations
                    .stream()
                    .map(annotation -> env.getElementsAnnotatedWith(annotation)
                            .stream()
                            .filter(TypeElement.class::isInstance)
                            .map(TypeElement.class::cast)
                            .collect(toList()))
                    .flatMap(Collection::stream)
                    .collect(toList());
            var contracts = buildContracts(annotatedTypes);
            writeConfigurationFile(contracts);
            return true;
        } catch (Exception e) {
            super.processingEnv.getMessager().printMessage(ERROR, "Failed to process indexed classes");
            e.printStackTrace();
            return false;
        }
    }

    private List<ImmutablePair<ApiContract, ApiContractInteractionType>> buildContracts(List<TypeElement> elements) {
        return elements
                .stream()
                .map(typeElement -> ImmutablePair.of(toApiContractModel(typeElement), getContractInteractionType(typeElement)))
                .collect(toList());
    }

    private ApiContractInteractionType getContractInteractionType(TypeElement element) {
        var apiContractAnnotation = element.getAnnotation(IndexedApiContract.class);
        return apiContractAnnotation.interactionType();
    }

    private ApiContract toApiContractModel(TypeElement element) {
        var apiContractAnnotation = element.getAnnotation(IndexedApiContract.class);
        return new ApiContract()
                .setName(isBlank(apiContractAnnotation.value()) ? String.valueOf(element.getSimpleName()) : apiContractAnnotation.value())
                .setVersion(apiContractAnnotation.version())
                .setDescription(apiContractAnnotation.description())
                .setElements(toApiContractElements(element.getEnclosedElements(), apiContractAnnotation.onlyExplicitlyIncluded()));
    }

    private List<ApiContractElement> toApiContractElements(List<? extends Element> elements, boolean onlyExplicitlyIncluded) {
        return elements.stream()
                .filter(this::isFieldOrMethod)
                .filter(element -> isNotExcluded(element, onlyExplicitlyIncluded))
                .map(element -> {
                    var annotation = element.getAnnotation(IndexedApiContractElement.class);
                    return FIELD == element.getKind()
                            ? buildContractFieldElement(element, annotation)
                            : buildContractMethodElement(element, annotation);
                })
                .collect(toList());
    }

    private ApiContractField buildContractFieldElement(Element element, IndexedApiContractElement annotation) {
        boolean isAnnotated = nonNull(annotation);
        return (ApiContractField) new ApiContractField()
                .setDataType(isNotBlank(annotation.dataType()) ? annotation.dataType() : String.valueOf(element.asType()))
                .setName(getContractElementName(element, annotation))
                .setDescription(isAnnotated ? annotation.description() : EMPTY);
    }

    private ApiContractMethod buildContractMethodElement(Element element, IndexedApiContractElement annotation) {
        var executableElement = (ExecutableElement) element;
        return (ApiContractMethod) new ApiContractMethod()
                .setReturnDataType(isNotBlank(annotation.dataType()) ? annotation.dataType() : String.valueOf(executableElement.getReturnType()))
                .setArgsDataType(executableElement.getParameters()
                        .stream()
                        .map(param -> String.valueOf(param.asType()))
                        .collect(toList()))
                .setName(getContractElementName(element, annotation))
                .setDescription(annotation.description());
    }

    private String getContractElementName(Element element, IndexedApiContractElement annotation) {
        return nonNull(annotation)
                ? (isBlank(annotation.value()) ? String.valueOf(element.getSimpleName()) : annotation.value())
                : String.valueOf(element.getSimpleName());
    }

    private boolean isFieldOrMethod(Element element) {
        return FIELD == element.getKind() || METHOD == element.getKind();
    }

    private boolean isNotExcluded(Element element, boolean onlyExplicitlyIncluded) {
        if (nonNull(element.getAnnotation(IndexedApiContractElement.Exclude.class))) {
            return false;
        }
        return !onlyExplicitlyIncluded || nonNull(element.getAnnotation(IndexedApiContractElement.Include.class));
    }

    private void writeConfigurationFile(List<ImmutablePair<ApiContract, ApiContractInteractionType>> contracts) {
        try {
            var providedContracts = contracts
                    .stream()
                    .filter(contract -> PROVIDED == contract.right)
                    .map(ImmutablePair::getLeft)
                    .collect(toList());
            var appliedContracts = contracts
                    .stream()
                    .filter(contract -> APPLIED == contract.right)
                    .map(ImmutablePair::getLeft)
                    .collect(toList());

            var artifact = new ApiArtifact()
                    .setProvidedContracts(providedContracts)
                    .setAppliedContracts(appliedContracts);
            var artifactConfigFile = super.processingEnv.getFiler().createResource(CLASS_OUTPUT, "", INDEXED_API_CONTRACTS_PATH);
            try (var writer = artifactConfigFile.openWriter()) {
                writer.write(objectWriter.writeValueAsString(artifact));
            }
        } catch (IOException e) {
            if (!(e instanceof FilerException)) {
                super.processingEnv.getMessager().printMessage(ERROR, "Failed to write indexed model to configuration file");
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
