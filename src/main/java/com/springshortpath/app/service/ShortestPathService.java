package com.springshortpath.app.service;

import com.springshortpath.app.payload.response.PathShortestConnectionResponse;
import com.springshortpath.app.payload.response.PathShortestTimeResponse;
import reactor.core.publisher.Mono;

public interface ShortestPathService {

    public Mono<PathShortestConnectionResponse> getShortestPath(String from, String to);

    public Mono<PathShortestTimeResponse> getShortestPathInTime(String from, String to);

}
