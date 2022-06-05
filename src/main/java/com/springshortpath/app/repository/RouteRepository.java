package com.springshortpath.app.repository;

import com.springshortpath.app.model.Route;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.UUID;

public interface RouteRepository extends Neo4jRepository<Route,UUID> {

    @Query("MATCH (city:City {id: $cityId})<-[:ROUTES]-(route:Route) RETURN route")
    List<Route> listAllByCityId(UUID cityId);

    @Query("MATCH (route:Route {id: $routeId}) RETURN route")
    Route getById(UUID routeId);

    @Query("CREATE (city:City {id: $cityId})-[:ROUTES]->(route:Route {id: randomUUID(), from: $from, destination: $destination, departureTime: $departureTime," +
            "arriveTime: $arriveTime, duration: $duration}) " +
            "RETURN route")
    Route saveRoute(UUID cityId, String from, String destination, String departureTime,
                    String arriveTime, Long duration);

    @Query("MATCH (city:City {id: $cityId})-[:ROUTES]->(route:Route {id: $routeId}) " +
            "SET route.from = $from, route.destination = $destination,route.departureTime = $departureTime," +
            "route.arriveTime = $arriveTime, route.duration = $duration RETURN route")
    Route updateRoute(UUID cityId, UUID routeId, String from, String destination,String departureTime,
                      String arriveTime,Long duration);

    @Query("MATCH (city:City {id: $cityId})-[:ROUTES]->(route:Route {id: $routeId}) DELETE route")
    void deleteRoute(UUID cityId, UUID routeId);
}
