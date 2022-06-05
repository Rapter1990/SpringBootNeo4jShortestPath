package com.springshortpath.app.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Node
@Data
@ToString
@NoArgsConstructor
@Getter
@Setter
public class City {

    @Id
    @Property
    private UUID id;

    @Property
    private String name;

    @Relationship(type = "ROUTES", direction = Relationship.Direction.OUTGOING)
    private Set<Route> routes = new HashSet<>();

    public City(String name) {
        this.name = name;
    }
}
