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
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/city")
public class CityController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/id/{cityId}")
    public City getCityById(@PathVariable(value = "cityId") UUID cityId) {
        LOGGER.info("CityController | getByCityId is started");
        LOGGER.info("CityController | getByCityId | cityId : " + cityId);
        return cityService.getById(cityId);
    }

    /*
    [
        {
            "id": 0,
            "name": "A",
            "routes": [
                {
                    "from": "A",
                    "destiny": "B",
                    "departureTime": "10:00",
                    "arrivalTime": "11:00"
                }
            ]
        },
        ...
    ]
     */
    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities(){
        LOGGER.info("CityController | getAllCities is started");
        List<City> cityList = cityService.listAll();
        LOGGER.info("CityController | getAllCities | cityList : " + cityList.toString());
        return new ResponseEntity<>(cityList, HttpStatus.OK);
    }

    @GetMapping("/name/{cityName}")
    public City getCityByName(@PathVariable(value = "cityName") String cityName) {
        LOGGER.info("CityController | getByCityName is started");
        LOGGER.info("CityController | getByCityName | cityName : " + cityName);
        return cityService.getByCityName(cityName);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<City> createCity(@RequestBody CityDTO cityDTO) {
        LOGGER.info("CityController | createCity is started");
        LOGGER.info("CityController | createCity | city name : " + cityDTO.getName());
        City city = new City(cityDTO.getName());
        City savedCity = cityService.saveCity(city);
        return new ResponseEntity<>(savedCity, HttpStatus.CREATED);
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<City> updateCity(@PathVariable(value = "cityId") UUID cityId, @RequestBody CityDTO cityDTO) {
        LOGGER.info("CityController | updateCity is started");
        LOGGER.info("CityController | updateCity | cityId : " + cityId);
        LOGGER.info("CityController | updateCity | update city name : " + cityDTO.getName());
        City city = new City(cityDTO.getName());
        City updatedCity = cityService.updateCity(cityId,city);
        LOGGER.info("CityController | updateCity | updatedCity : " + updatedCity.toString());
        return new ResponseEntity<>(updatedCity, HttpStatus.OK);
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<String> deleteCity(@PathVariable(value = "cityId") UUID cityId) {
        LOGGER.info("CityController | deleteCity is started");
        LOGGER.info("CityController | deleteCity | cityId : " + cityId);
        cityService.deleteCity(cityId);
        return new ResponseEntity<>("City is deleted.", HttpStatus.OK);

    }
}
