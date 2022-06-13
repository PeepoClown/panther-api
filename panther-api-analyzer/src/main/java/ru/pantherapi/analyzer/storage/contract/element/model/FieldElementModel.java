package ru.pantherapi.analyzer.storage.contract.element.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FieldElementModel
        extends ContractElementModel {
    private String dataType;
}
