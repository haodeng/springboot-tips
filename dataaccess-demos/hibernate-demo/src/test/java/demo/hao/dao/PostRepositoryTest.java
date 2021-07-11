package demo.hao.dao;

import demo.hao.model.Address;
import demo.hao.model.Post;
import demo.hao.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test_findPostsByUserEmail() {
        // Given:
        User user = userRepository.save(new User("Hao", "Deng", "test@demo.dk", "123", Boolean.TRUE, new Address()));
        assertThat(user).isNotNull();
        postRepository.saveAndFlush(new Post("test post", Collections.emptySet(), null, user));

        // When:
        Collection<Post> posts = postRepository.findPostsByUserEmail("test@demo.dk");

        // Then:
        assertThat(posts).hasSize(1);
        Post post = posts.iterator().next();
        assertThat(post.getName()).isEqualTo("test post");
    }
}
