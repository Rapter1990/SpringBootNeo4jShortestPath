package com.springshortpath.app.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.Duration;
import java.time.LocalDateTime;

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

    private long calculateDuration() {

        final LocalDateTime departureDate =
                LocalDateTime.of(0, 1, 1, getHours(departureTime), getMinutes(departureTime));
        final LocalDateTime arrivalDate =
                LocalDateTime.of(0, 1, 1, getHours(arriveTime), getMinutes(arriveTime));

        return Duration.between(departureDate, arrivalDate).toHours();
    }

    private int getHours(final String time) {
        return Integer.parseInt(time.split(":")[0]);
    }

    private int getMinutes(final String time) {
        return Integer.parseInt(time.split(":")[1]);
    }
}
