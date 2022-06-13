package ru.pantherapi.analyzer.storage.contract.model;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.pantherapi.analyzer.storage.contract.element.model.ContractElementModel;

import java.util.List;

@Data
@Accessors(chain = true)
public class ContractModel {
    private String id;
    private String name;
    private String version;
    private String description;
    private List<ContractElementModel> elements;
}
