package com.springshortpath.app;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.Neo4jContainer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

@SpringBootTest
class AppApplicationTests {

    static Neo4jContainer<?> container = new Neo4jContainer<>("neo4j:4.3-community");

    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> "123456");
        registry.add("spring.neo4j.uri", () -> container.getBoltUrl());
    }

    private final UUID cityId1 = UUID.randomUUID();

    @BeforeAll
    static void startContainer() {
        container.start();
    }


    @BeforeEach
    void initializeData() {

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
            mockMvc.perform(put("/api/v1/city/id/" + cityId1).accept("application/json"))
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
