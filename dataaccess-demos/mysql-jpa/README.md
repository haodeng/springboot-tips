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
     
     docker start mysql
     curl http://localhost:8080/posts

# Customize hikari
By default boot already using Hikari, just need customize it, eg:

    spring.datasource.hikari.connection-timeout=50000
    spring.datasource.hikari.idle-timeout=300000
    spring.datasource.hikari.max-lifetime=900000

## Paging and sorting
Use PagingAndSortingRepository which extends CrudRepository

Page number values start with 0

A Pagable need to put to find:

    postRepository.findAll(
                    PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));

Sort asc or desc:

    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("name").descending());

Demo:

    curl "http://localhost:8080/posts/paged?pageNo=1&pageSize=5"
    curl "http://localhost:8080/posts/paged?pageNo=10&pageSize=5&sortBy=name"