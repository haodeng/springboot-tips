package demo.hao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;
import java.util.stream.StreamSupport;

@SpringBootTest(classes = {TestDemoApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerIntegrationReactiveTest {
    @Autowired
    private WebTestClient client;

    // When started, DataLoader already loads 3 posts

    @Test
    void getPosts() {

        Iterable iterable = client.get()
                .uri("/posts")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Iterable.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(3, StreamSupport.stream(iterable.spliterator(), false).count());
    }

    @Test
    void getPostById() {
        Map postMap = client.get()
                .uri("/posts/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Map.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(1, postMap.get("id"));
        Assertions.assertEquals("test 1", postMap.get("name"));
    }
}