package com.springshortpath.app.mapper;

import com.springshortpath.app.model.City;
import com.springshortpath.app.model.Route;
import com.springshortpath.app.payload.response.CityResponse;
import com.springshortpath.app.payload.response.RouteResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CityMapper {

    public CityResponse mapFromCityToCityResponse(City city){

        CityResponse cityResponse = new CityResponse();
        cityResponse.setId(city.getId() != null ? city.getId() : null );
        cityResponse.setName(city.getName());

        List<RouteResponse> routeResponseList = new RouteMapper()
                .mapRouteListToRouteResponseList(new ArrayList<>(city.getRoutes()));

        cityResponse.setRoutes(routeResponseList);

        return cityResponse;
    }

    public List<CityResponse> mapCityListToCityResponseList(final List<City> cityList) {
        return cityList != null
                ? cityList
                .stream()
                .map(this::mapFromCityToCityResponse)
                .collect(Collectors.toList())
                : null;
    }
}
