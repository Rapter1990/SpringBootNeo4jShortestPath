package com.springshortpath.app.service;

import com.springshortpath.app.model.City;

import java.util.List;
import java.util.UUID;

public interface CityService {

    List<City> listAll();

    City getById(UUID cityId);

    City getByCityName(String cityName);

    City saveCity(City city);

    City updateCity(UUID cityId, City city);

    void deleteCity(UUID cityId);
}
