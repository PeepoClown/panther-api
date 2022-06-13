package ru.pantherapi.analyzer.storage.contract.element.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Node(primaryLabel = "FieldElement", labels = "ContractElement")
public class FieldElement
        extends ContractElement {
    @Property("dataType")
    private String dataType;
}
