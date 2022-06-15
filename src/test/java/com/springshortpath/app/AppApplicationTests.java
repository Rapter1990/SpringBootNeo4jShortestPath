package com.springshortpath.app;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;

@SpringBootTest
class AppApplicationTests {

    static Neo4jContainer<?> container = new Neo4jContainer<>("neo4j:4.3-community");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> "123456");
        registry.add("spring.neo4j.uri", () -> container.getBoltUrl());
    }

    @BeforeAll
    static void startContainer() {
        container.start();
    }


    @BeforeEach
    void initializeData() {

    }

    @Test
    void listCities() {

    }

    @Test
    void cityById(){

    }

    @Test
    void cityByName(){

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
