package com.springshortpath.app.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponse {

    private UUID id;
    private String from;
    private String destination;
    private String departureTime;
    private String arriveTime;
    private Double duration;
}
