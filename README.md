# Calculating Shortest Path through Neo4j in Spring Boot

<img src="screenshots/springboot_neo4j.png" alt="Main Information" width="800" height="300">

### 📖 Information

<ul style="list-style-type:disc">
  <li>Its aims to calculate the shortest path in terms of total connection and duration between two nodes</li>
  <li>Here is the explanation of the project
      <ul>
        <li>Implement the process of CRUD for both City and its Route</li>
        <li>Use Dijkstra Single-Source Shortest Path algorithm supported by Neo4j to calculate the path</li>
      </ul>
  </li>
</ul>

### 🔨 Run the App

<b>Maven></b>

<b>1 )</b> Download Neo4j Desktop 1.4.15

<b>2 )</b> Create a new Project and then create a new local dmbs with its version 4.2.1

<b>3 )</b> Click Start Button

<b>4 )</b> Download your project from this link `https://github.com/Rapter1990/SpringBootNeo4jShortestPath`

<b>5 )</b> Go to the project's home directory :  `cd SpringBootNeo4jShortestPath`

<b>6 )</b> Create a jar file though this command `mvn clean install`

<b>7 )</b> Run the project though this command `mvn spring-boot:run`


<b>Docker</b>

<b>1 )</b> Install <b>Docker Desktop</b>. Here is the installation <b>link</b> : https://docs.docker.com/docker-for-windows/install/

<b>2 )</b> Open <b>Terminal</b> under <b>resources</b> folder to run <b>Minio</b> on <b>Docker</b> Container
```
    docker-compose up -d
```
<b>3 )</b> Open <b>Neo4j Browser</b> in the brower
```
    http://localhost:7474/browser/
```

Explore Rest APIs
<table style="width:100%">
  <tr>
    <th>Method</th>
    <th>Url</th>
    <th>Description</th>
    <th>Valid Request Body</th>
    <th>Valid Request Params</th>
    <th>Valid Request Params and Body</th>
    <th>No Request or Params</th>
  </tr>
  <tr>
      <td>GET</td>
      <td>getCityById</td>
      <td>Get City By Id</td>
      <td></td>
      <td><a href="README.md#getCityById">Info</a></td>
      <td></td>
      <td></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>getAllCities</td>
      <td>Get All Cities</td>
      <td></td>
      <td></td>
      <td></td>
      <td><a href="README.md#getAllCities">Info</a></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>getCityByName</td>
      <td>Get City By Name</td>
      <td></td>
      <td><a href="README.md#getCityByName">Info</a></td>
      <td></td>
      <td></td>
  </tr>
  <tr>
      <td>POST</td>
      <td>createCity</td>
      <td>Create City</td>
      <td></td>
      <td><a href="README.md#getCityByName">Info</a></td>
      <td></td>
      <td></td>
  </tr>
  <tr>
      <td>PUT</td>
      <td>updateCity</td>
      <td>Update City</td>
      <td></td>
      <td></td>
      <td><a href="README.md#updateCity">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>DELETE</td>
      <td>deleteCity</td>
      <td>Delete City</td>
      <td></td>
      <td><a href="README.md#deleteCity">Info</a></td>
      <td></td>
      <td></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>getByRouteId</td>
      <td>Get Route By Id</td>
      <td></td>
      <td><a href="README.md#getByRouteId">Info</a></td>
      <td></td>
      <td></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>getAllRoutes</td>
      <td>Get All Routes By City Id</td>
      <td></td>
      <td><a href="README.md#getAllRoutes">Info</a></td>
      <td></td>
      <td></td>
  </tr>
  <tr>
      <td>POST</td>
      <td>createRoute</td>
      <td>Create Route by City Id and Destination City Id</td>
      <td></td>
      <td></td>
      <td><a href="README.md#createRoute">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>PUT</td>
      <td>updateRoute</td>
      <td>Update Route by City Id and Destination City Id</td>
      <td></td>
      <td></td>
      <td><a href="README.md#updateRoute">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>DELETE</td>
      <td>deleteRoute</td>
      <td>Delete Route By City Id and Route City</td>
      <td></td>
      <td><a href="README.md#deleteRoute">Info</a></td>
      <td></td>
      <td></td>
  </tr>
  <tr>
       <td>GET</td>
       <td>getShortestPath</td>
       <td>Get Shortest Path by Total Connection</td>
       <td><a href="README.md#getShortestPath">Info</a></td>
       <td></td>
       <td></td>
       <td></td>
  </tr>
  <tr>
       <td>GET</td>
       <td>getShortestPathInTime</td>
       <td>Get Shortest Path by Total Hours</td>
       <td><a href="README.md#getShortestPathInTime">Info</a></td>
       <td></td>
       <td></td>
       <td></td>
  </tr>
</table>


### Used Dependencies
* Spring Boot Web
* Spring Boot Test
* Neo4j
* Docker
* Mapper

## Valid Request Body

##### <a id="createCity">Create City
```
    http://localhost:8077/api/v1/city

    {
        "name" : "İstanbul"
    }
```

##### <a id="getShortestPath">Get Shortest Path by Total Connection
```
    http://localhost:8077/api/v1/shortestpath/shortest-path

    {
        "from" : "İstanbul",
        "destination" : "Ankara"
    }
```

##### <a id="getShortestPathInTime">Get Shortest Path by Total Hours
```
    http://localhost:8077/api/v1/shortestpath/shortest-path-in-time

    {
        "from" : "İstanbul",
        "destination" : "Ankara"
    }
```



## Valid Request Params

##### <a id="getCityById">Get City By Id
```
    http://localhost:8077/api/v1/city/id/{cityId}
```

##### <a id="getCityByName">Get City By Name
```
    http://localhost:8077/api/v1/city/name/{cityName}
```

##### <a id="deleteCity">Delete City By Id
```
    http://localhost:8077/api/v1/city/{cityId}
```

##### <a id="getByRouteId">Get Route By Id
```
    http://localhost:8077/api/v1/route/{routeId}
```

##### <a id="getAllRoutes">Get All Routes By City Id
```
    http://localhost:8077/api/v1/route/{cityId}/routes
```

##### <a id="deleteRoute">Delete Route By City Id and Route City
```
    http://localhost:8077/api/v1/route/{cityId}/routes
```

## Valid Request Params and Body

##### <a id="updateCity">Update City
```
    http://localhost:8077/api/v1/city/{cityId}

    {
        "name" : "Ankara"
    }
```

##### <a id="createRoute">Create Route by City Id and Destination City Id
```
    http://localhost:8077/api/v1/route/{cityId}/{destinationCityId}/create-route

    {
        "from" : "İstanbul",
        "destination" : "Ankara",
        "departureTime" : "9:00",
        "arriveTime" : "11:30"
    }
```

##### <a id="updateRoute">Update Route by City Id and Destination City Id
```
    http://localhost:8077/api/v1/route/{cityId}/update-route/{routeId}

    {
        "from" : "Ankara",
        "destination" : "Antalya",
        "departureTime" : "9:00",
        "arriveTime" : "11:00"
    }
```

## No Request or Params

##### <a id="getAllCities">Get All Cities
```
    http://localhost:8077/api/v1/city/cities
```


### Screenshots

<details>
<summary>Click here to show the screenshots of project</summary>
    <p> Figure 1 </p>
    <img src ="screenshots/screenshot_1.PNG">
    <p> Figure 2 </p>
    <img src ="screenshots/screenshot_2.PNG">
</details>    
    