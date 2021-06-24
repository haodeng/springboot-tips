
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>

config

    spring:
      data:
        mongodb:
          database: DemoDb
          host: localhost
          port: 27017

MongoRepository has crud and paging support:

    interface PostRepository extends MongoRepository<Post, String> {}

Use @Document for post entity

             
Start mongodb

    docker start mongodb

Test

    curl http://localhost:8080/posts
