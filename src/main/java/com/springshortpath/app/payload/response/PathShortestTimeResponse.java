package com.springshortpath.app.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PathShortestTimeResponse {

    private String departureCity;
    private String arrivalCity;
    private Integer totalInTime;

    public PathShortestTimeResponse(PathShortestTimeResponse pathShortestTimeResponse) {
        this.departureCity = pathShortestTimeResponse.getDepartureCity();
        this.arrivalCity = pathShortestTimeResponse.getArrivalCity();
        this.totalInTime = pathShortestTimeResponse.getTotalInTime();
    }

}
