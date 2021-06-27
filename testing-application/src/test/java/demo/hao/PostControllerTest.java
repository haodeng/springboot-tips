package demo.hao;

import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.stream.StreamSupport;

@WebMvcTest(controllers = {PostController.class})
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    void getPosts() throws Exception {
        Mockito.when(postService.getPosts())
                .thenReturn(List.of(new Post(1L, "test 1"),
                        new Post(2L, "test 2"),
                        new Post(3L, "test 3")));

        String response = mockMvc
                .perform(MockMvcRequestBuilders.get("/posts"))
                .andReturn()
                .getResponse().getContentAsString();

        String expected = "[{\"id\":1,\"name\":\"test 1\"},{\"id\":2,\"name\":\"test 2\"},{\"id\":3,\"name\":\"test 3\"}]";
        Assertions.assertEquals(expected, response);
    }

    @Test
    void getPostById() throws Exception {
        Mockito.when(postService.getPostById(1L))
                .thenReturn(Optional.of(new Post(1L, "test 1")));

        String response = mockMvc
                .perform(MockMvcRequestBuilders.get("/posts/1"))
                .andReturn()
                .getResponse().getContentAsString();

        String expected = "{\"id\":1,\"name\":\"test 1\"}";
        Assertions.assertEquals(expected, response);
    }
}