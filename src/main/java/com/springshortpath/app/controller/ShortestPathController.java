package com.springshortpath.app.controller;

import com.springshortpath.app.payload.request.PathRequest;
import com.springshortpath.app.payload.response.PathShortestConnectionResponse;
import com.springshortpath.app.payload.response.PathShortestTimeResponse;
import com.springshortpath.app.service.ShortestPathService;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v1/shortestpath")
public class ShortestPathController {

    private final ShortestPathService shortestPathService;

    public ShortestPathController(ShortestPathService shortestPathService) {
        this.shortestPathService = shortestPathService;
    }

    /*
    {
      "arrivalCity": "A",
      "departureCity": "B",
      "totalConnections": 1
    }
     */
    @GetMapping("/shortest-path")
    public Mono<PathShortestConnectionResponse> getShortestPath(@RequestBody PathRequest pathRequest) {

        return shortestPathService.getShortestPath(pathRequest.getFrom(), pathRequest.getDestination())
                .map(PathShortestConnectionResponse::new)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Error")));
    }

    /*
    {
      "arrivalCity": "A",
      "departureCity": "B",
      "totalHours": 5
    }
     */
    @GetMapping("/shortest-path-in-time")
    public Mono<PathShortestTimeResponse>  getShortestPathInTime(@RequestBody PathRequest pathRequest) {

        return shortestPathService.getShortestPathInTime(pathRequest.getFrom(), pathRequest.getDestination())
                .map(PathShortestTimeResponse::new)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Error")));
    }

    @ResponseStatus(
            value = HttpStatus.NOT_FOUND,
            reason = "Illegal arguments")
    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgumentHandler() {

    }
}
