package com.springshortpath.app.service.impl;


import com.springshortpath.app.payload.response.PathShortestConnectionResponse;
import com.springshortpath.app.payload.response.PathShortestTimeResponse;
import com.springshortpath.app.repository.ShortestPathRepository;
import com.springshortpath.app.service.ShortestPathService;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Relationship;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ShortestPathServiceImpl implements ShortestPathService {

    private final ShortestPathRepository shortestPathRepository;

    @Override
    public Mono<PathShortestConnectionResponse> getShortestPath(String from, String to) {

        final Flux<PathValue> rows = shortestPathRepository.shortestPath(from, to);
        return rows
                .map(it -> this.convert(it.asPath()))
                .take(1)
                .next()
                .switchIfEmpty(Mono.empty());

    }

    @Override
    public Mono<PathShortestTimeResponse> getShortestPathInTime(String from, String to) {

        final Flux<PathValue> rows = shortestPathRepository.shortestPathInTime(from, to);
        return rows
                .map(it -> this.convertTimePath(it.asPath()))
                .take(1)
                .next()
                .switchIfEmpty(Mono.empty());
    }


    private PathShortestConnectionResponse convert(org.neo4j.driver.types.Path connection) {

        String departureCity = connection.start().get("name").asString();
        String arriveCity = connection.end().get("name").asString();
        int length = connection.length();

        return new PathShortestConnectionResponse(departureCity, arriveCity, length);
    }

    private PathShortestTimeResponse convertTimePath(org.neo4j.driver.types.Path connection) {

        String departureCity = connection.start().get("name").asString();
        String arriveCity = connection.end().get("name").asString();
        Stream<Relationship> targetStream = StreamSupport.stream(connection.relationships().spliterator(), false);
        int totalInTime = targetStream.mapToInt(it -> it.get("duration").asInt()).sum();

        return new PathShortestTimeResponse(departureCity, arriveCity, totalInTime);
    }
}
