package hao;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
class DataLoader {
    private final PostRepository postRepository;

    public DataLoader(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostConstruct
    private void loadData() {
        java.util.List<Post> posts = new ArrayList();
        posts.add(new Post(1L, "test 1"));
        posts.add(new Post(2L, "test 2"));
        posts.add(new Post(3L, "test 3"));

        postRepository.saveAll(posts);
    }
}


