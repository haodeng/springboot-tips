# SpringBoot Notes

## Data
* h2
* mysql
* redis
* mongodb
* Neo4j (Graph db)

Spring Data’s stated mission is “to provide a familiar and consistent, Spring-based programming model for data access while still retaining the special traits of the underlying data store.” 

Regardless of database or platform, Spring Data’s goal is to make the developer’s use of data as simple and as powerful as humanly possible.


## Property Configuration
* @Value
* @ConfigurationProperties
* Using Profiles to configure environment specific configuration
* Check properties from actuator/env

## Dev tools
* Live load

## Reactive
Reactive should be end to end solution.

If interactions between the user’s browser and the backend application are nonblocking but the app has to wait for a blocking interaction with the database, the result is a blocking system.

* WebFlux
* Reactive Data Access (via r2dbc h2)
* Reactive MongoDb

## Testing
* Unit testing (Controllers and Repository)
* Integration test

## Security
A big topic
* Form based authentication, authorization
* OpenId connect authentication, Oauth2 authorization (Via 3rd party authorization server, eg: okta)
* Resource server
* Authorization server (Still an experiment project)

checkout: https://github.com/haodeng/spring-security-tips

## Deploy
* Spring Boot Executable JAR
* "Fully Executable" Spring Boot JAR
* Build image (docker should be running)
* publish image to docker hub
* Build & publish image without docker (jib plugin)
* Inspect a image
