package com.springshortpath.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDTO {

    private String destination;
    private String departureTime;
    private String arriveTime;
    private Long duration;

}
