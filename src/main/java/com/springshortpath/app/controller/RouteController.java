package com.springshortpath.app.controller;

import com.springshortpath.app.dto.RouteDTO;
import com.springshortpath.app.mapper.RouteMapper;
import com.springshortpath.app.model.Route;
import com.springshortpath.app.payload.response.RouteResponse;
import com.springshortpath.app.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/route")
public class RouteController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final RouteService routeService;

    private final RouteMapper routeMapper;

    public RouteController(RouteService routeService,RouteMapper routeMapper) {
        this.routeService = routeService;
        this.routeMapper = routeMapper;
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<RouteResponse> getByRouteId(@PathVariable(value = "routeId") UUID routeId) {
        LOGGER.info("RouteController | getByRouteId is started");
        LOGGER.info("RouteController | getByRouteId | routeId : " + routeId);

        Route route = routeService.getById(routeId);

        RouteResponse routeResponse = routeMapper.mapFromRouteToRouteResponse(route);

        LOGGER.info("RouteController | getByRouteId | routeResponse : " + routeResponse.toString());

        return new ResponseEntity<>(routeResponse, HttpStatus.OK);
    }

    @GetMapping("/{cityId}/routes")
    public ResponseEntity<List<RouteResponse>> getAllRoutes(@PathVariable(value = "cityId") UUID cityId){
        LOGGER.info("RouteController | getAllRoutes is started");
        LOGGER.info("RouteController | getAllRoutes | cityId : " + cityId);
        List<Route> routeList = routeService.listAllByCityId(cityId);

        List<RouteResponse> routeResponsesList = routeMapper.mapRouteListToRouteResponseList(routeList);

        LOGGER.info("RouteController | getAllRoutes | routeResponsesList : " + routeResponsesList.toString());

        return new ResponseEntity<>(routeResponsesList, HttpStatus.OK);
    }

    @PostMapping(value = "/{cityId}/{destinationCityId}/create-route", consumes = "application/json")
    public ResponseEntity<RouteResponse> createRoute(@PathVariable(value = "cityId") UUID cityId,
                                             @PathVariable(value = "destinationCityId") UUID destinationCityId,
                                             @RequestBody RouteDTO routeDTO) {

        LOGGER.info("RouteController | createRoute is started");
        LOGGER.info("RouteController | createRoute | cityId : " + cityId);
        LOGGER.info("RouteController | createRoute | destinationCityId : " + destinationCityId);

        RouteDTO dto = new RouteDTO(routeDTO.getFrom(),routeDTO.getDestination(),
                routeDTO.getDepartureTime(),routeDTO.getArriveTime());


        Route savedRoute = routeService.save(cityId, destinationCityId, dto);

        RouteResponse routeResponse = routeMapper.mapFromRouteToRouteResponse(savedRoute);

        LOGGER.info("RouteController | createRoute | routeResponse : " + routeResponse.toString());

        return new ResponseEntity<>(routeResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{cityId}/update-route/{routeId}",consumes = "application/json")
    public ResponseEntity<RouteResponse> updateRoute(@PathVariable(value = "cityId") UUID cityId,
                                            @PathVariable(value = "routeId") UUID routeId,
                                            @RequestBody RouteDTO routeDTO) {

        LOGGER.info("RouteController | updateRoute is started");
        LOGGER.info("RouteController | updateRoute | cityId : " + cityId);
        LOGGER.info("RouteController | updateRoute | routeId : " + routeId);

        RouteDTO dto = new RouteDTO(routeDTO.getFrom(),routeDTO.getDestination(),
                routeDTO.getDepartureTime(),routeDTO.getArriveTime());

        Route updatedRoute = routeService.update(cityId, routeId, dto);

        RouteResponse routeResponse = routeMapper.mapFromRouteToRouteResponse(updatedRoute);

        LOGGER.info("RouteController | updateRoute | routeResponse : " + routeResponse.toString());

        return new ResponseEntity<>(routeResponse, HttpStatus.OK);
    }

    @DeleteMapping("{cityId}/delete-route/{routeId}")
    public ResponseEntity<String> deleteRoute(@PathVariable(value = "cityId") UUID cityId,
                                             @PathVariable(value = "routeId") UUID routeId) {

        LOGGER.info("RouteController | deleteRoute is started");
        LOGGER.info("RouteController | deleteRoute | cityId : " + cityId);
        LOGGER.info("RouteController | deleteRoute | routeId : " + routeId);

        routeService.delete(cityId, routeId);
        return new ResponseEntity<>("Route is deleted.", HttpStatus.OK);
    }
}
