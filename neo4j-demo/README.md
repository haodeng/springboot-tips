
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-neo4j</artifactId>
        </dependency>
        
Add config:

    spring.neo4j.authentication.username=neo4j
    spring.neo4j.authentication.password=test

Entity class should annotate with @Node

    @Node
    class Post {
    ...
    
Start neo4j

    docker run --name neo4j -p7474:7474 -p7687:7687 -d --env NEO4J_AUTH=neo4j/test neo4j:latest

Test:

    curl http://localhost:8080/posts
    curl http://localhost:8080/posts/1