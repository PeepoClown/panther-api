package ru.pantherapi.model.artefact;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.pantherapi.model.contract.ApiContract;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
public class ApiArtefact
        implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -5064129843629315510L;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotBlank
    @JsonProperty("version")
    private String version;

    @Valid
    @NotNull
    @JsonProperty("providedContracts")
    private List<ApiContract> providedContracts;

    @Valid
    @NotNull
    @JsonProperty("appliedContracts")
    private List<ApiContract> appliedContracts;
}
