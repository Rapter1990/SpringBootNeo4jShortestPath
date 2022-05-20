package com.springshortpath.app.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue
    private Long id;

    private String destination;

    private String departureTime;

    private String arriveTime;

    private Long duration;
}
