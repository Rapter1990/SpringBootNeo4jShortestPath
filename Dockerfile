FROM openjdk:11 AS build

COPY pom.xml mvnw ./
RUN chmod +x mvnw
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -Dmaven.test.skip

# For Java 11,
FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR SpringBootNeo4jShortestPath

COPY --from=build target/*.jar SpringBootNeo4jShortestPath.jar

ENTRYPOINT ["java","-jar","SpringBootNeo4jShortestPath.jar"]