# Mysql JPA

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>

Spring Boot checks the usual root classpath locations for files that fit the following naming format:
* schema.sql
* data.sql
* schema-${platform}.sql
* data-${platform}.sql

The last two filenames match to the application property spring.datasource.platform.
 

By default, Boot automatically creates table structures from any classes annotated with @Entity. 
It’s simple to override this behavior with the following property settings:

    spring.datasource.initialization-mode=always
    spring.jpa.hibernate.ddl-auto=none

Test:
    
     curl http://localhost:8080/posts
