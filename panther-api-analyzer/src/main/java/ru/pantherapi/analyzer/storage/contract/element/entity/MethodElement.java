package ru.pantherapi.analyzer.storage.contract.element.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.List;

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Node(primaryLabel = "MethodElement", labels = "ContractElement")
public class MethodElement
        extends ContractElement {
    @Property("returnDataType")
    private String returnDataType;

    @Property("argsDataType")
    private List<String> argsDataType;
}
