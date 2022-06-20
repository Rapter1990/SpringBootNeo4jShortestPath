package com.springshortpath.app.controller;

import com.springshortpath.app.dto.CityDTO;
import com.springshortpath.app.mapper.CityMapper;
import com.springshortpath.app.model.City;
import com.springshortpath.app.payload.response.CityResponse;
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

    private final CityMapper cityMapper;

    public CityController(CityService cityService,CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @GetMapping("/id/{cityId}")
    public CityResponse getCityById(@PathVariable(value = "cityId") UUID cityId) {
        LOGGER.info("CityController | getByCityId is started");
        LOGGER.info("CityController | getByCityId | cityId : " + cityId);

        City city = cityService.getById(cityId);

        CityResponse cityResponse = cityMapper.mapFromCityToCityResponse(city);

        LOGGER.info("CityController | getByCityId | cityResponse : " + cityResponse.toString());

        return cityResponse;
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
    public ResponseEntity<List<CityResponse>> getAllCities(){
        LOGGER.info("CityController | getAllCities is started");
        List<City> cityList = cityService.listAll();
        LOGGER.info("CityController | getAllCities | cityList : " + cityList.toString());

        List<CityResponse> cityResponseList = cityMapper.mapCityListToCityResponseList(cityList);

        LOGGER.info("CityController | getAllCities | cityResponseList : " + cityResponseList.toString());

        return new ResponseEntity<>(cityResponseList, HttpStatus.OK);
    }

    @GetMapping("/name/{cityName}")
    public CityResponse getCityByName(@PathVariable(value = "cityName") String cityName) {
        LOGGER.info("CityController | getByCityName is started");
        LOGGER.info("CityController | getByCityName | cityName : " + cityName);

        City city = cityService.getByCityName(cityName);

        CityResponse cityResponse = cityMapper.mapFromCityToCityResponse(city);

        LOGGER.info("CityController | getCityByName | cityResponse : " + cityResponse.toString());

        return cityResponse;

    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CityResponse> createCity(@RequestBody CityDTO cityDTO) {
        LOGGER.info("CityController | createCity is started");
        LOGGER.info("CityController | createCity | city name : " + cityDTO.getName());
        City city = new City(cityDTO.getName());
        City savedCity = cityService.saveCity(city);

        CityResponse cityResponse = cityMapper.mapFromCityToCityResponse(city);

        LOGGER.info("CityController | createCity | cityResponse : " + cityResponse.toString());

        return new ResponseEntity<>(cityResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<CityResponse> updateCity(@PathVariable(value = "cityId") UUID cityId, @RequestBody CityDTO cityDTO) {
        LOGGER.info("CityController | updateCity is started");
        LOGGER.info("CityController | updateCity | cityId : " + cityId);
        LOGGER.info("CityController | updateCity | update city name : " + cityDTO.getName());
        City city = new City(cityDTO.getName());
        City updatedCity = cityService.updateCity(cityId,city);
        LOGGER.info("CityController | updateCity | updatedCity : " + updatedCity.toString());

        CityResponse cityResponse = cityMapper.mapFromCityToCityResponse(city);

        LOGGER.info("CityController | updateCity | cityResponse : " + cityResponse.toString());

        return new ResponseEntity<>(cityResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<String> deleteCity(@PathVariable(value = "cityId") UUID cityId) {
        LOGGER.info("CityController | deleteCity is started");
        LOGGER.info("CityController | deleteCity | cityId : " + cityId);
        cityService.deleteCity(cityId);
        return new ResponseEntity<>("City is deleted.", HttpStatus.OK);

    }
}
