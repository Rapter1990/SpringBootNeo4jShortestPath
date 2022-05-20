package com.springshortpath.app.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
@Data
@ToString
@NoArgsConstructor
@Getter
@Setter
public class City {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "ROUTES", direction = Relationship.Direction.OUTGOING)
    private Set<Route> routes = new HashSet<>();

    public City(String name) {
        this.name = name;
    }
}
