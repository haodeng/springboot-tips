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
