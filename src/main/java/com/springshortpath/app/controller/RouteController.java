package com.springshortpath.app.controller;

import com.springshortpath.app.dto.RouteDTO;
import com.springshortpath.app.model.Route;
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

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<Route> getByRouteId(@PathVariable(value = "routeId") UUID routeId) {
        LOGGER.info("RouteController | getByRouteId is started");
        LOGGER.info("RouteController | getByRouteId | routeId : " + routeId);
        return new ResponseEntity<>(routeService.getById(routeId), HttpStatus.OK);
    }

    @GetMapping("/{cityId}/routes")
    public ResponseEntity<List<Route>> getAllRoutes(@PathVariable(value = "cityId") UUID cityId){
        LOGGER.info("RouteController | getAllRoutes is started");
        LOGGER.info("RouteController | getAllRoutes | cityId : " + cityId);
        List<Route> routeList = routeService.listAllByCityId(cityId);
        return new ResponseEntity<>(routeList, HttpStatus.OK);
    }

    @PostMapping(value = "/{cityId}/{destinationCityId}/create-route", consumes = "application/json")
    public ResponseEntity<Route> createRoute(@PathVariable(value = "cityId") UUID cityId,
                                             @PathVariable(value = "destinationCityId") UUID destinationCityId,
                                             @RequestBody RouteDTO routeDTO) {

        LOGGER.info("RouteController | createRoute is started");
        LOGGER.info("RouteController | createRoute | cityId : " + cityId);

        RouteDTO dto = new RouteDTO(routeDTO.getFrom(),routeDTO.getDestination(),
                routeDTO.getDepartureTime(),routeDTO.getArriveTime());


        Route savedRoute = routeService.save(cityId, destinationCityId, dto);
        return new ResponseEntity<>(savedRoute, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{cityId}/update-route/{routeId}",consumes = "application/json")
    public ResponseEntity<Route> updateRoute(@PathVariable(value = "cityId") UUID cityId,
                                            @PathVariable(value = "routeId") UUID routeId,
                                            @RequestBody RouteDTO routeDTO) {

        LOGGER.info("RouteController | updateRoute is started");
        LOGGER.info("RouteController | updateRoute | cityId : " + cityId);
        LOGGER.info("RouteController | updateRoute | routeId : " + routeId);

        RouteDTO dto = new RouteDTO(routeDTO.getFrom(),routeDTO.getDestination(),
                routeDTO.getDepartureTime(),routeDTO.getArriveTime());

        Route updatedRoute = routeService.update(cityId, routeId, dto);
        return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
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
