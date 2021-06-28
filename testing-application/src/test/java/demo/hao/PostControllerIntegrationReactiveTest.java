package demo.hao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@SpringBootTest(classes = {TestDemoApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerIntegrationReactiveTest {
    @Autowired
    private WebTestClient client;
    private Post post1, post2, post3;

    @BeforeEach
    void setup() {
        post1 = new Post(1L, "test 1");
        post2 = new Post(2L, "test 2");
        post3 = new Post(3L, "test 3");
    }

    // When started, DataLoader already loads 3 posts in h2 db

    @Test
    void getPosts() {

        StepVerifier.create(client.get()
                .uri("/posts")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Post.class)
                .getResponseBody())
                .expectNext(post1)
                .expectNext(post2)
                .expectNext(post3)
                .verifyComplete();
    }

    @Test
    void getPostById() {
        StepVerifier.create(client.get()
                .uri("/posts/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Post.class)
                .getResponseBody())
                .expectNext(post1)
                .verifyComplete();
    }
}