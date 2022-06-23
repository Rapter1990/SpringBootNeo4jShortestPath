package com.springshortpath.app;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.Neo4jContainer;
import org.neo4j.driver.Record;
import org.testcontainers.containers.Neo4jLabsPlugin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
public class AppApplicationTests {

    static Neo4jContainer<?> container = new Neo4jContainer<>("neo4j:4.3-community")
            .withLabsPlugins(Neo4jLabsPlugin.APOC);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Driver driver;

    @Autowired
    private WebTestClient webTestClient;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> container.getAdminPassword());
        registry.add("spring.neo4j.uri", () -> container.getBoltUrl());
    }

    private final UUID cityId1 = UUID.randomUUID();
    private final UUID cityId2 = UUID.randomUUID();
    private final UUID cityId3 = UUID.randomUUID();

    private final UUID routeId1 = UUID.randomUUID();
    private final UUID routeId2 = UUID.randomUUID();
    private final UUID routeId3 = UUID.randomUUID();

    @BeforeAll
    static void startContainer() {
        container.start();
    }


    @BeforeEach
    void initializeData() {
        try (Session session = driver.session()) {
            session.run("MATCH (n) detach delete n").consume();
            session.run("CREATE (:City{id:$cityId1, name:'Istanbul'})" +
                            "-[:ROUTES]->(:Route{id:$routeId1,from:'Istanbul', destination:'Ankara', duration: 2}) " +
                            "-[:ROUTES]->(:City{id:$cityId2, name:'Ankara'})",
                    Map.of("cityId1", cityId1.toString(), "cityId2", cityId2.toString(), "routeId1", routeId1.toString()))
                    .consume();

            session.run("CREATE (:City{id:$cityId1, name:'Istanbul'})" +
                            "-[:ROUTES]->(:Route{id:$routeId2,from:'Istanbul', destination:'Antalya', duration: 3}) " +
                            "-[:ROUTES]->(:City{id:$cityId3, name:'Antalya'})",
                    Map.of("cityId1", cityId1.toString(), "cityId3", cityId3.toString(), "routeId2", routeId2.toString()))
                    .consume();

            session.run("CREATE (:City{id:$cityId2, name:'Ankara'})" +
                            "-[:ROUTES]->(:Route{id:$routeId3,from:'Ankara', destination:'Antalya', duration: 2}) " +
                            "-[:ROUTES]->(:City{id:$cityId3, name:'Antalya'})",
                    Map.of("cityId2", cityId2.toString(), "cityId3", cityId3.toString(), "routeId3", routeId3.toString()))
                    .consume();
        }
    }

    @Test
    void listCities() throws Exception {
        try {
            mockMvc.perform(get("/api/v1/city/cities").accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0]['name']").value("Istanbul"))
                    .andExpect(jsonPath("$[1]['name']").value("Ankara"));
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

            // ensure optional match works and returns a city without a route relationship
            mockMvc.perform(get("/api/v1/city/id/" + cityId2).accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Ankara"));

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
            mockMvc.perform(put("/api/v1/city/" + cityId1)
                    .contentType("application/json")
                    .content("{\"name\" : \"Antalya\"}")
                    .accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Antalya"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addRoute() throws Exception {
        mockMvc.perform(post("/api/v1/route/" + cityId1 + "/" + cityId2 + "/create-route")
                .contentType("application/json")
                .content("{\"from\" : \"Istanbul\", \"destination\" : \"Ankara\", \"departureTime\" : \"9:00\", \"arriveTime\" : \"11:00\"}")
                .accept("application/json"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.from").value("Istanbul"))
                .andExpect(jsonPath("$.destination").value("Ankara"));

        try (Session session = driver.session()) {
            List<Record> records = session.run("MATCH (c:City) return c.name as cityName").list();
            assertThat(records).hasSize(2);
            assertThat(records).map(record -> record.get("cityName").asString())
                    .containsExactlyInAnyOrder("Ankara", "Istanbul");
        }
    }

    @Test
    void getRouteById() throws Exception {
        try {
            mockMvc.perform(get("/api/v1/route/" + routeId1).accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.from").value("Istanbul"))
                    .andExpect(jsonPath("$.destination").value("Ankara"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void listRoutesByCity() throws Exception {
        mockMvc.perform(get("/api/v1/route/" +  cityId1 + "/routes").accept("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]['from']").value("Istanbul"))
                .andExpect(jsonPath("$[0]['destination']").value("Ankara"));
    }

    @Test
    void updateRoute(){
        try {
            mockMvc.perform(put("/api/v1/route/" + cityId1 + "/update-route/" + routeId1)
                    .contentType("application/json")
                    .content("{\"from\" : \"Istanbul\", \"destination\" : \"Antalya\", \"departureTime\" : \"9:00\", \"arriveTime\" : \"11:30\"}")
                    .accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.from").value("Istanbul"))
                    .andExpect(jsonPath("$.destination").value("Antalya"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteRoute() throws Exception {
        mockMvc.perform(delete("/api/v1/route/" + cityId1 + "/delete-route/" + routeId1)
                .accept("application/json"))
                .andDo(print())
                .andExpect(status().isOk());

        try (Session session = driver.session()) {
            long routeCount = session.run("MATCH (r:ROUTE) return count(r) as routeCount").single().get("routeCount").asLong();
            assertThat(routeCount).isEqualTo(0L);
        }
    }

    @Test
    void shortestPath(){
        webTestClient.method(HttpMethod.GET).uri("/api/v1/shortestpath/shortest-path")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue("{\"from\":\"Istanbul\", \"destination\":\"Ankara\"}")
                .header("content-type", "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\"departureCity\":\"Istanbul\",\"arrivalCity\":\"Ankara\",\"totalConnections\":1}");
    }

    @Test
    void shortestPathInTime(){
        webTestClient.method(HttpMethod.GET).uri("/api/v1/shortestpath/shortest-path-in-time")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue("{\"from\":\"Istanbul\", \"destination\":\"Ankara\"}")
                .header("content-type", "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\"departureCity\":\"Istanbul\",\"arrivalCity\":\"Ankara\",\"totalInTime\":2}");
    }

    @Test
    void shortestPathNewConnection(){
        webTestClient.method(HttpMethod.GET).uri("/api/v1/shortestpath/shortest-path")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue("{\"from\":\"Istanbul\", \"destination\":\"Antalya\"}")
                .header("content-type", "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\"departureCity\":\"Istanbul\",\"arrivalCity\":\"Antalya\",\"totalConnections\":1}");
    }

    @Test
    void shortestPathInTimeNewConnection(){
        webTestClient.method(HttpMethod.GET).uri("/api/v1/shortestpath/shortest-path-in-time")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue("{\"from\":\"Istanbul\", \"destination\":\"Antalya\"}")
                .header("content-type", "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\"departureCity\":\"Istanbul\",\"arrivalCity\":\"Antalya\",\"totalInTime\":3}");
    }

    @AfterAll
    static void tearDownContainer() {
        container.stop();
    }
}
