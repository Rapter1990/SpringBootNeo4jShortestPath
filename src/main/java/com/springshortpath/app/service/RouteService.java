package com.springshortpath.app.service;

import com.springshortpath.app.dto.RouteDTO;
import com.springshortpath.app.model.Route;

import java.util.List;

public interface RouteService {

    List<Route> listAllByCityId(Long cityId);

    Route getById(Long routeId);

    Route save(Long cityId, RouteDTO routeDTO);

    Route update(Long cityId, Long routeId, RouteDTO routeDTO);

    void delete(Long cityId, Long routeId);
}
