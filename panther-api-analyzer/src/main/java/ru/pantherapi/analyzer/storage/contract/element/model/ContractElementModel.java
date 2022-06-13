package ru.pantherapi.analyzer.storage.contract.element.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class ContractElementModel {
    private String id;
    private String name;
    private String description;
}
