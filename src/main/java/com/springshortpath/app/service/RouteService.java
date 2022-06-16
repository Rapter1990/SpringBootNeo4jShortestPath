package com.springshortpath.app.service;

import com.springshortpath.app.dto.RouteDTO;
import com.springshortpath.app.model.Route;

import java.util.List;
import java.util.UUID;

public interface RouteService {

    List<Route> listAllByCityId(UUID cityId);

    Route getById(UUID routeId);

    Route save(UUID cityId, UUID destinationCityId, RouteDTO routeDTO);

    Route update(UUID cityId, UUID routeId, RouteDTO routeDTO);

    void delete(UUID cityId, UUID routeId);
}
