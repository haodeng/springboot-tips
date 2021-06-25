# Reactive

Project Reactor defines two types of Publisher:
* Mono:: emits 0 or 1 element 
* Flux:: emits 0 to n elements, a defined number or boundless

This aligns brilliantly with imperative constructs. A method may return an object of 
* type T 
* or an Iterable<T>.

## Tomcat/Jetty vs Netty
In the imperative world of Spring Boot, Tomcat is the default servlet engine used for web applications

Netty is a proven and performant asynchronous engine

* Spring MVC --> tomcat/jetty (Sync, < Servlet 3.1)
* Spring webFlux --> Netty (Async)

## Reactive Data Access
JPA was built on an imperative specification and is thus inherently blocking. 

Reactive Relational Database Connectivity (R2DBC) project.
R2DBC builds on Project Reactor’s implementation of Reactive Streams and is fully reactive and nonblocking.

For example: in memory r2dbc h2 DB

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-r2dbc</artifactId>
    </dependency>
    <dependency>
        <groupId>io.r2dbc</groupId>
        <artifactId>r2dbc-h2</artifactId>
        <scope>runtime</scope>
    </dependency>

Use ReactiveCrudRepository:
    
    public interface PostRepository extends ReactiveCrudRepository<Post, String> {}

## RSocket for Fully Reactive Interprocess Communication
RSocket is a blazing-fast binary protocol that can be used over TCP, WebSocket, and Aeron transport mechanisms.

RSocket builds on the reactive streams paradigm and Project Reactor, enabling fully interconnected systems of applications while providing mechanisms that increase flexibility and resilience.

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-rsocket</artifactId>
        </dependency>
 
Use 8081 for rsocket
        
    spring.rsocket.server.port=8081

Demo:

    curl http://localhost:8080/posts

    curl http://localhost:8080/post-stream
    data:{"id":1,"name":"test 1"}
    
    data:{"id":2,"name":"test 2"}
    
    data:{"id":3,"name":"test 3"}
    ...

## Reactive MongoDb
checkout: https://github.com/haodeng/spring-reactive-mongo-crud