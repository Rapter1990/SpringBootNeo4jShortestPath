package com.springshortpath.app;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.containers.Neo4jLabsPlugin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class AppApplicationTests {

    static Neo4jContainer<?> container = new Neo4jContainer<>("neo4j:4.3-community");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Driver driver;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> "123456");
        registry.add("spring.neo4j.uri", () -> container.getBoltUrl());
    }

    private final UUID cityId1 = UUID.randomUUID();
    private final UUID cityId2 = UUID.randomUUID();
    private final UUID routeId = UUID.randomUUID();

    @BeforeAll
    static void startContainer() {
        container.start();
    }


    @BeforeEach
    void initializeData() {
        try (Session session = driver.session()) {
            session.run("MATCH (n) detach delete n").consume();
            session.run("CREATE (:City{id:$cityId1, name:'Istanbul'})" +
                            "-[:ROUTES]->(:Route{id:$routeId,from:'Istanbul', destination:'Ankara', duration: 2}) " +
                            "-[:ROUTES]->(:City{id:$cityId2, name:'Ankara'})",
                    Map.of("cityId1", cityId1.toString(), "cityId2", cityId2.toString(), "routeId", routeId.toString()))
                    .consume();
        }
    }

    @Test
    void listCities() throws Exception {
        try {
            mockMvc.perform(get("/api/v1/city/cities").accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0]['name']").value("Istanbul"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void cityById() throws Exception {
        try {
            mockMvc.perform(get("/api/v1/city/id/" + cityId1).accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Istanbul"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void cityByName() throws Exception {
        try {
            mockMvc.perform(get("/api/v1/city/name/Istanbul").accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Istanbul"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateCity(){
        try {
            mockMvc.perform(put("/api/v1/city/id/" + cityId1)
                    .contentType("application/json")
                    .content("{\"name\" : \"Ankara\"}")
                    .accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Ankara"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addRoute(){

    }

    @Test
    void listRoutesByCity(){

    }

    @Test
    void deleteRoute(){

    }

    @Test
    void shortestPath(){

    }

    @Test
    void shortestPathInTime(){

    }

    @AfterAll
    static void tearDownContainer() {
        container.stop();
    }
}
