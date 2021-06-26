package demo.hao;

import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@WebFluxTest(controllers = {PostController.class})
class PostControllerTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private PostService postService;

    @Test
    void getPosts() {
        Mockito.when(postService.getPosts())
                .thenReturn(List.of(new Post(1L, "test1"),
                        new Post(2L, "test2"),
                        new Post(3L, "test3"),
                        new Post(4L, "test4")));

        Iterable iterable = client.get()
                .uri("/posts")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Iterable.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(4, StreamSupport.stream(iterable.spliterator(), false).count());
    }

    @Test
    void getPostById() {
        Mockito.when(postService.getPostById(1L))
                .thenReturn(Optional.of(new Post(1L, "test1")));

        Map postMap = client.get()
                .uri("/posts/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Map.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(1, postMap.get("id"));
        Assertions.assertEquals("test1", postMap.get("name"));
    }
}