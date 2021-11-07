package demo.hao;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PostRepositoryTest {
    @Autowired
    private PostRepository repository;

    private Post post1, post2;

    @BeforeEach
    void setUp() {
        post1 = new Post(1L, "test 1");
        post2 = new Post(2L, "test 2");

        repository.saveAll(List.of(post1, post2));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void testFindById() {
        assertEquals(Optional.of(post1), repository.findById(post1.getId()));
        assertEquals(Optional.of(post2), repository.findById(post2.getId()));
    }

    @Test
    void testFindAll() {
        assertEquals(List.of(post1, post2), repository.findAll());
    }

}
