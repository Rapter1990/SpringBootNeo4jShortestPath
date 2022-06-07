package com.springshortpath.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDTO {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private String from;
    private String destination;
    private String departureTime;
    private String arriveTime;
    private Double duration;

    public RouteDTO(String from,String destination,String departureTime, String arriveTime) {
        this.from = from;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arriveTime = arriveTime;
        this.duration = calculateDuration();
    }

    private double calculateDuration() {

        LOGGER.info("RouteDTO | calculateDuration is started");

        final LocalDateTime departureDate =
                LocalDateTime.of(0, 1, 1, getHours(departureTime), getMinutes(departureTime));
        final LocalDateTime arrivalDate =
                LocalDateTime.of(0, 1, 1, getHours(arriveTime), getMinutes(arriveTime));

        LOGGER.info("RouteDTO | calculateDuration | departureDate : " + departureDate);
        LOGGER.info("RouteDTO | calculateDuration | arrivalDate : " + arrivalDate);

        Duration duration = Duration.between(departureDate, arrivalDate);

        double result = duration.toHours() + duration.toMinutesPart()/60f;

        LOGGER.info("RouteDTO | calculateDuration | duration result : " + result);

        return result;
    }

    private int getHours(final String time) {
        LOGGER.info("RouteDTO | getHours | hour : " + time.split(":")[0]);
        return Integer.parseInt(time.split(":")[0]);
    }

    private int getMinutes(final String time) {
        LOGGER.info("RouteDTO | getMinutes | minute : " + time.split(":")[1]);
        return Integer.parseInt(time.split(":")[1]);
    }
}
