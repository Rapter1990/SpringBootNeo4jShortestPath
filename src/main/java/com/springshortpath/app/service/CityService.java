package com.springshortpath.app.service;

import com.springshortpath.app.model.City;

import java.util.List;

public interface CityService {

    List<City> listAll();

    City getById(Long cityId);

    City getByCityName(String cityName);

    City saveCity(City city);

    City updateCity(Long cityId, City city);

    void deleteCity(Long cityId);
}
