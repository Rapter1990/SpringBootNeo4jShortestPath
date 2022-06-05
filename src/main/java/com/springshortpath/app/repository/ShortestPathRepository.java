package com.springshortpath.app.repository;

import com.springshortpath.app.model.City;
import org.neo4j.driver.internal.value.PathValue;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ShortestPathRepository extends ReactiveNeo4jRepository<City, UUID> {

    @Query("MATCH p=shortestPath((a:City {name:$from})-[*]->(b:City {name:$to})) RETURN p")
    Flux<PathValue> shortestPath(@Param("from") String from, @Param("to") String to);

    @Query("MATCH (a:City {name: $from})\n"
            + "MATCH (b:City {name: $to})\n"
            + "CALL apoc.algo.dijkstra(a, b, 'ROUTES', 'duration')\n"
            + "YIELD path, weight\n"
            + "RETURN path\n"
            + "ORDER BY weight ASC LIMIT 1")
    Flux<PathValue> shortestPathInTime(@Param("from") String from, @Param("to") String to);
}
