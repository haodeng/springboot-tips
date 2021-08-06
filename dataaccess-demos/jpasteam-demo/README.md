# JPA stream
An Open Source Library to Express Hibernate/JPA Queries as Java Streams

https://jpastreamer.org/


Spring JPA already doing a great job, jpa stream is just another way to write fluent data access code.

        <dependency>
            <groupId>com.speedment.jpastreamer</groupId>
            <artifactId>jpastreamer-core</artifactId>
            <version>1.0.1</version>
        </dependency>
        <dependency>
            <groupId>com.speedment.jpastreamer.integration.spring</groupId>
            <artifactId>spring-boot-jpastreamer-autoconfigure</artifactId>
            <version>1.0.1</version>
        </dependency>

Example:

    public List<Post> getPostsByPage(int offset, int limit) {
        return jpaStreamer.stream(Post.class)
                .sorted(Post$.name)
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }
    
Demo

    curl http://localhost:8080/posts
    curl "http://localhost:8080/posts/page?offset=5&limit=2"
    curl http://localhost:8080/posts/5
    
    curl http://localhost:8080/posts/group_by_category
    {
      "spring": [
        {
          "id": 2,
          "name": "test 2",
          "category": "spring"
        },
        {
          "id": 6,
          "name": "test 6",
          "category": "spring"
        },
        {
          "id": 10,
          "name": "test 10",
          "category": "spring"
        }
      ],
      "java": [
        {
          "id": 1,
          "name": "test 1",
          "category": "java"
        },
        {
          "id": 3,
          "name": "test 3",
          "category": "java"
        },
        {
          "id": 5,
          "name": "test 5",
          "category": "java"
        },
        {
          "id": 7,
          "name": "test 7",
          "category": "java"
        },
        {
          "id": 8,
          "name": "test 8",
          "category": "java"
        },
        {
          "id": 9,
          "name": "test 9",
          "category": "java"
        }
      ],
      "k8s": [
        {
          "id": 4,
          "name": "test 4",
          "category": "k8s"
        }
      ]
    }d