package demo.hao;

import com.sun.tools.javac.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", Matchers.containsInAnyOrder(1, 2, 3)))
                .andExpect(jsonPath("$[*].name", Matchers.containsInAnyOrder("test 1", "test 2", "test 3")));
    }

    @Test
    void getPostsInBDDWay() throws Exception {
        BDDMockito.given(postService.getPosts())
                .willReturn(List.of(new Post(1L, "test 1"),
                        new Post(2L, "test 2"),
                        new Post(3L, "test 3")));

        mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", Matchers.containsInAnyOrder(1, 2, 3)))
                .andExpect(jsonPath("$[*].name", Matchers.containsInAnyOrder("test 1", "test 2", "test 3")));
    }

    @Test
    void getPostById() throws Exception {
        Mockito.when(postService.getPostById(1L))
                .thenReturn(Optional.of(new Post(1L, "test 1")));

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("test 1")));

    }
}