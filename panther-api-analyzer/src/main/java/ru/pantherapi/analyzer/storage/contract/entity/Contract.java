package ru.pantherapi.analyzer.storage.contract.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
import ru.pantherapi.analyzer.storage.contract.element.entity.ContractElement;

import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Data
@Accessors(chain = true)
@Node("Contract")
public class Contract {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    @Property("id")
    private String id;

    @Property("name")
    private String name;

    @Property("version")
    private String version;

    @Relationship(type = "CONTAINS", direction = OUTGOING)
    private Set<ContractElement> elements;

    @Property("description")
    private String description;
}
