package com.springshortpath.app.mapper;

import com.springshortpath.app.model.Route;
import com.springshortpath.app.payload.response.RouteResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RouteMapper {


    public RouteResponse mapFromRouteToRouteResponse(Route route){

        RouteResponse routeResponse = new RouteResponse();
        routeResponse.setFrom(route.getFrom());
        routeResponse.setArriveTime(route.getArriveTime());
        routeResponse.setDepartureTime(route.getDepartureTime());
        routeResponse.setDestination(route.getDestination());
        routeResponse.setDestinationCity(route.getDestinationCity().getName());
        routeResponse.setDuration(route.getDuration());
        routeResponse.setId(route.getId());

        return routeResponse;
    }

    public List<RouteResponse> mapRouteListToRouteResponseList(final List<Route> routeList) {
        return routeList != null
                ? routeList
                .stream()
                .map(this::mapFromRouteToRouteResponse)
                .collect(Collectors.toList())
                : null;
    }
}
