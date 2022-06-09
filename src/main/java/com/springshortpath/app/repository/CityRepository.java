package com.springshortpath.app.repository;

import com.springshortpath.app.model.City;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.UUID;

public interface CityRepository extends Neo4jRepository<City,UUID> {

    @Query("MATCH (city:City) OPTIONAL MATCH (city)-[r:ROUTES]->(route:Route) RETURN city, collect(r), collect(route)")
    List<City> listAll();

    @Query("MATCH (city:City {id: $cityId}) OPTIONAL MATCH (city)-[r:ROUTES]->(route:Route) RETURN city, collect(r), collect(route)")
    City getById(UUID cityId);

    @Query("MATCH (city:City {name: $cityName}) RETURN city")
    City getByCityName(String cityName);

    @Query("CREATE (city:City {id: randomUUID(), name: $cityName}) RETURN city")
    City saveCity(String cityName);

    @Query("MATCH (city:City {id: $cityId}) SET city.name = $cityName RETURN city")
    City updateCity(UUID cityId, String cityName);

    @Query("MATCH (city:City {id: $cityId}) DELETE city")
    void deleteCity(UUID cityId);
}
