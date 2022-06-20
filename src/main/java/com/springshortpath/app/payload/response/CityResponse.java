package com.springshortpath.app.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityResponse {

    private UUID id;
    private String name;
    private List<RouteResponse> routes;
}
