package com.springshortpath.app.controller;

import com.springshortpath.app.dto.RouteDTO;
import com.springshortpath.app.model.Route;
import com.springshortpath.app.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/route")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<Route> getByPostId(@PathVariable(value = "routeId") Long routeId) {
        return new ResponseEntity<>(routeService.getById(routeId), HttpStatus.OK);
    }

    @GetMapping("/{cityId}/routes")
    public ResponseEntity<List<Route>> getAllPosts(@PathVariable(value = "cityId") Long cityId){
        List<Route> routeList = routeService.listAllByCityId(cityId);
        return new ResponseEntity<>(routeList, HttpStatus.OK);
    }

    @PostMapping(value = "/{cityId}/create-route", consumes = "application/json")
    public ResponseEntity<Route> createPost(@PathVariable(value = "cityId") Long cityId, @RequestBody RouteDTO routeDTO) {
        Route savedRoute = routeService.save(cityId, routeDTO);
        return new ResponseEntity<>(savedRoute, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{cityId}/update-route/{routeId}",consumes = "application/json")
    public ResponseEntity<Route> updateUser(@PathVariable(value = "cityId") Long cityId,
                                            @PathVariable(value = "routeId") Long routeId,
                                            @RequestBody RouteDTO routeDTO) {

        Route updatedRoute = routeService.update(cityId, routeId, routeDTO);
        return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
    }

    @DeleteMapping("{cityId}/delete-route/{routeId}")
    public ResponseEntity<String> deletePost(@PathVariable(value = "cityId") Long cityId,
                                             @PathVariable(value = "routeId") Long routeId) {

        routeService.delete(cityId, routeId);
        return new ResponseEntity<>("Route is deleted.", HttpStatus.OK);
    }
}
