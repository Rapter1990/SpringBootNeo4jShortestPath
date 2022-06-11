package com.springshortpath.app.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Node
@Data
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Route {

    @Id
    @Property
    private UUID id;

    @Property
    private String from;

    @Property
    private String destination;

    @Property
    private String departureTime;

    @Property
    private String arriveTime;

    @Property
    private Double duration;
}
