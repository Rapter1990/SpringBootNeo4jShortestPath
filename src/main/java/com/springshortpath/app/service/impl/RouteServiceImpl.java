package com.springshortpath.app.service.impl;

import com.springshortpath.app.dto.RouteDTO;
import com.springshortpath.app.model.Route;
import com.springshortpath.app.repository.RouteRepository;
import com.springshortpath.app.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @Override
    public List<Route> listAllByCityId(UUID cityId) {
        return routeRepository.listAllByCityId(cityId);
    }

    @Override
    public Route getById(UUID routeId) {
        return routeRepository.getById(routeId);
    }

    @Override
    public Route save(UUID cityId, UUID destinationCityId, RouteDTO routeDTO) {

        String from = routeDTO.getFrom();
        String destination = routeDTO.getDestination();
        String departureTime = routeDTO.getDepartureTime();
        String arriveTime = routeDTO.getArriveTime();
        double duration = routeDTO.getDuration();

        return routeRepository.saveRoute(cityId,destinationCityId,from,destination,departureTime,
                arriveTime,duration);
    }

    @Override
    public Route update(UUID cityId, UUID routeId, RouteDTO routeDTO) {

        String from = routeDTO.getFrom();
        String destination = routeDTO.getDestination();
        String departureTime = routeDTO.getDepartureTime();
        String arriveTime = routeDTO.getArriveTime();
        double duration = routeDTO.getDuration();


        return routeRepository.updateRoute(cityId,routeId,from, destination,departureTime,
                arriveTime,duration);
    }

    @Override
    public void delete(UUID cityId, UUID routeId) {
        routeRepository.deleteRoute(cityId,routeId);
    }
}
