package ru.pantherapi.analyzer.storage.artifact.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
import ru.pantherapi.analyzer.storage.contract.entity.Contract;

import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Data
@Accessors(chain = true)
@Node("Artifact")
public class Artifact {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    @Property("id")
    private String id;

    @Property("name")
    private String name;

    @Property("version")
    private String version;

    @Relationship(type = "PROVIDE", direction = OUTGOING)
    private Set<Contract> providedContracts;

    @Relationship(type = "APPLY", direction = OUTGOING)
    private Set<Contract> appliedContracts;
}
