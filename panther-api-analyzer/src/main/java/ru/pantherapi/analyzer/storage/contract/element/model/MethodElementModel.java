package ru.pantherapi.analyzer.storage.contract.element.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MethodElementModel
        extends ContractElementModel {
    private String returnDataType;
    private List<String> argsDataType;
}
