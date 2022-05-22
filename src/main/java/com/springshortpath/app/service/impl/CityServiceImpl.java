package com.springshortpath.app.service.impl;

import com.springshortpath.app.model.City;
import com.springshortpath.app.repository.CityRepository;
import com.springshortpath.app.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<City> listAll() {
        return cityRepository.listAll();
    }

    @Override
    public City getById(Long cityId) {
        return cityRepository.getById(cityId);
    }

    @Override
    public City getByCityName(String cityName) {
        return cityRepository.getByCityName(cityName);
    }

    @Override
    public City saveCity(City city) {
        return cityRepository.saveCity(city.getName());
    }

    @Override
    public City updateCity(Long cityId, City city) {
        return cityRepository.updateCity(cityId,city.getName());
    }

    @Override
    public void deleteCity(Long cityId) {
        cityRepository.deleteCity(cityId);
    }
}
