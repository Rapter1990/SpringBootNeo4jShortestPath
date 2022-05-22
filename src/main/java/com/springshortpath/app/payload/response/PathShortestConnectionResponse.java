package com.springshortpath.app.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PathShortestConnectionResponse {

    private String departureCity;
    private String arrivalCity;
    private Integer totalConnections;

    public PathShortestConnectionResponse(PathShortestConnectionResponse pathShortestConnectionResponse) {
        this.departureCity = pathShortestConnectionResponse.getDepartureCity();
        this.arrivalCity = pathShortestConnectionResponse.getArrivalCity();
        this.totalConnections = pathShortestConnectionResponse.getTotalConnections();
    }
}
