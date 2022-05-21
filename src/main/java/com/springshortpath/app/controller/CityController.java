package com.springshortpath.app.controller;

import com.springshortpath.app.dto.CityDTO;
import com.springshortpath.app.model.City;
import com.springshortpath.app.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/city")
public class CityController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/{cityId}")
    public City getByCityId(@PathVariable(value = "cityId") Long cityId) {
        LOGGER.info("CityController | getByCityId is started");
        LOGGER.info("CityController | getByCityId | cityId : " + cityId);
        return cityService.getById(cityId);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities(){
        LOGGER.info("CityController | getAllCities is started");
        List<City> cityList = cityService.listAll();
        return new ResponseEntity<>(cityList, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<City> createCity(@RequestBody CityDTO cityDTO) {
        LOGGER.info("CityController | createCity is started");
        LOGGER.info("CityController | createCity | city name : " + cityDTO.getName());
        City city = new City(cityDTO.getName());
        City savedCity = cityService.saveCity(city);
        return new ResponseEntity<>(savedCity, HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<City> updateCity(@RequestBody CityDTO cityDTO) {
        LOGGER.info("CityController | updateCity is started");
        LOGGER.info("CityController | updateCity | update city name : " + cityDTO.getName());
        City city = cityService.getByCityName(cityDTO.getName());
        City updatedCity = cityService.updateCity(city.getId(),city);
        return new ResponseEntity<>(updatedCity, HttpStatus.OK);
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<String> deleteCity(@PathVariable(value = "cityId") Long cityId) {
        LOGGER.info("CityController | deleteCity is started");
        LOGGER.info("CityController | deleteCity | cityId : " + cityId);
        cityService.deleteCity(cityId);
        return new ResponseEntity<>("City is deleted.", HttpStatus.OK);
    }
}
