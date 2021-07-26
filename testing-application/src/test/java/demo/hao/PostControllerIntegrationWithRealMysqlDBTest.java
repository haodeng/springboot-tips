package demo.hao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.MySQL8Dialect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Testcontainers
@SpringBootTest(classes = {TestDemoApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerIntegrationWithRealMysqlDBTest {
    @Autowired
    TestRestTemplate restTemplate;

    // When started, DataLoader already loads 3 posts

    private static final DockerImageName MYSQL_80_IMAGE = DockerImageName.parse("mysql:8.0.24");


    // Add MYSQL_ROOT_HOST environment so that we can root login from anywhere for testing purposes
    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>(MYSQL_80_IMAGE);

    @DynamicPropertySource
    static void registerPostgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.jpa.database-platform", MySQL8Dialect.class::getName);
        registry.add("spring.datasource.url", () -> mysql.getJdbcUrl());
        registry.add("spring.datasource.username", () -> mysql.getUsername());
        registry.add("spring.datasource.password", () -> mysql.getPassword());
    }

    @Test
    void getPosts() {
        ResponseEntity<String> response = restTemplate
                .exchange("/posts",
                        HttpMethod.GET,
                        null,
                        String.class);

        String expected = "[{\"id\":1,\"name\":\"test 1\"},{\"id\":2,\"name\":\"test 2\"},{\"id\":3,\"name\":\"test 3\"}]";
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void getPostById() {
        ResponseEntity<String> response = restTemplate
                .exchange("/posts/1",
                        HttpMethod.GET,
                        null,
                        String.class);

        String expected = "{\"id\":1,\"name\":\"test 1\"}";
        Assertions.assertEquals(expected, response.getBody());
    }
}