package com.springshortpath.app.repository;

import com.springshortpath.app.model.City;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CityRepository extends Neo4jRepository<City,Long> {

    @Query("MATCH (cities:City) RETURN cities")
    List<City> listAll();

    @Query("MATCH (city:City {id: $cityId}) RETURN city")
    City getById(Long cityId);

    @Query("MATCH (city:City {name: $cityName}) RETURN city")
    City getByCityName(String cityName);

    @Query("CREATE (city:City {name: $cityName}) RETURN city")
    City saveCity(String cityName);

    @Query("MATCH (city:City {name: $cityName}) SET city.id = $cityId RETURN city")
    City updateCity(Long cityId, String cityName);

    @Query("MATCH (city:City {id: $cityId}) DELETE city")
    void deleteCity(Long cityId);
}
