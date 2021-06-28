package demo.hao;

import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@WebFluxTest(controllers = {PostController.class})
class PostControllerReactiveTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private PostService postService;

    private Post post1, post2, post3;

    @BeforeEach
    void setup() {
        post1 = new Post(1L, "test 1");
        post2 = new Post(2L, "test 2");
        post3 = new Post(3L, "test 3");

        // PostController is not fully reactive
//        Mockito.when(postService.getPosts())
//                .thenReturn(Flux.just(post1, post2, post3).toIterable());

        Mockito.when(postService.getPosts())
                .thenReturn(List.of(post1, post2, post3));

        Mockito.when(postService.getPostById(1L))
                .thenReturn(Mono.just(post1).blockOptional());
    }

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