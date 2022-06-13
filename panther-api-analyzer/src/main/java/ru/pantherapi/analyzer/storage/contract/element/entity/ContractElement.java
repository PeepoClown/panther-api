package ru.pantherapi.analyzer.storage.contract.element.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Data
@Accessors(chain = true)
@Node("ContractElement")
public abstract class ContractElement {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    @Property("id")
    private String id;

    @Property("name")
    private String name;

    @Property("description")
    private String description;
}
