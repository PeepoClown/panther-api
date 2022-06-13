package ru.pantherapi.analyzer.storage.artifact.model;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.pantherapi.analyzer.storage.contract.model.ContractModel;

import java.util.List;

@Data
@Accessors(chain = true)
public class ArtifactModel {
    private String id;
    private String name;
    private String version;
    private List<ContractModel> providedContracts;
    private List<ContractModel> appliedContracts;
}
