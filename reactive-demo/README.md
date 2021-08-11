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

## Detect blocking code
Java agent to detect blocking calls from non-blocking threads.

Use blockhound in dev

        <dependency>
		    <groupId>io.projectreactor.tools</groupId>
		    <artifactId>blockhound</artifactId>
		    <version>1.0.6.RELEASE</version>
		</dependency>
		
		static {
                BlockHound.install();
        }
        
Demo:

    curl http://localhost:8080/bad/example1
    2021-08-10 22:38:02.209 ERROR 19686 --- [ctor-http-nio-5] a.w.r.e.AbstractErrorWebExceptionHandler : [7954e591-1]  500 Server Error for HTTP GET "/bad/example1"
    
    reactor.blockhound.BlockingOperationError: Blocking call! java.lang.Thread.sleep
    	at java.lang.Thread.sleep(Thread.java) ~[na:1.8.0_211]
    	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException: 
    Error has been observed at the following site(s):
    	|_ checkpoint ⇢ HTTP GET "/bad/example1" [ExceptionHandlingWebHandler]
    	
    	
    curl http://localhost:8080/bad/example2
    2021-08-10 22:37:20.285 ERROR 19686 --- [ctor-http-nio-4] a.w.r.e.AbstractErrorWebExceptionHandler : [4af28e38-1]  500 Server Error for HTTP GET "/bad/example2"
    
    reactor.blockhound.BlockingOperationError: Blocking call! java.lang.Thread.sleep
    	at java.lang.Thread.sleep(Thread.java) ~[na:1.8.0_211]
    	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException: 
    Error has been observed at the following site(s):
    	|_ checkpoint ⇢ Handler demo.hao.BadExamplesController#getGreeting_blocking() [DispatcherHandler]
    	|_ checkpoint ⇢ HTTP GET "/bad/example2" [ExceptionHandlingWebHandler]	
    
    
    curl http://localhost:8080/bad/example3
    Hi    
    
    curl http://localhost:8080/bad/example4
    2021-08-11 09:41:47.260 ERROR 68146 --- [ctor-http-nio-3] a.w.r.e.AbstractErrorWebExceptionHandler : [f53c89c5-1]  500 Server Error for HTTP GET "/bad/example4"
    
    reactor.blockhound.BlockingOperationError: Blocking call! java.lang.Thread.sleep
    	at java.lang.Thread.sleep(Thread.java) ~[na:1.8.0_211]
    	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException: 
    Error has been observed at the following site(s):
    	|_ checkpoint ⇢ HTTP GET "/bad/example4" [ExceptionHandlingWebHandler]
    Stack trace:
    		at java.lang.Thread.sleep(Thread.java) ~[na:1.8.0_211]
    		at demo.hao.BadExamplesController.getGreeting_stillBlocking(BadExamplesController.java:58) ~[classes/:na]
    		at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_211]
    		at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_211]
    		at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_211]
    		at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_211]
    		

Fix the blocking code by:
    
    return Mono.fromCallable(blockingService::getGreeting)
                    // properly schedule above blocking call on
                    // scheduler meant for blocking tasks
                    .subscribeOn(Schedulers.boundedElastic());	