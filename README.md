# SpringBoot Notes

## Data access
* h2
  * Query creation by method names and keywords
  * Query and native query
  * Auditing
* mysql
  * paging & sorting
  * Customize hikari
* Customize hikari connection (demo via mysql-jpa)
* pagination and sorting (mysql-jpa)
* transactional (mysql-jpa)
* Multi jpa datasources
* redis
* mongodb
* Neo4j (Graph db)
* Hibernate related:
  * second-level cache
  * solve N+1 issue

Spring Data’s stated mission is “to provide a familiar and consistent, Spring-based programming model for data access while still retaining the special traits of the underlying data store.” 

Regardless of database or platform, Spring Data’s goal is to make the developer’s use of data as simple and as powerful as humanly possible.

## Distributed Transaction
* use Atomikos

## Property Configuration
* @Value
* @ConfigurationProperties
* Using Profiles to configure environment specific configuration
* Check properties from actuator/env
* Internationalization (i18n)

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
* Integration test with real db
* Mocking

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
* Inspect an image

## Logging
* Customize console log
* Inject logger via Lombok
* Config logging for active profile
* Logging to multiple log files

## Caching
* service layer cache (demo via spring default simple caching)
* ehcache3 cache

## Other
* Spring retry
* Scheduling
