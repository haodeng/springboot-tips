package demo.hao;

import com.sun.tools.javac.util.List;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
class DataLoader {
    private final PostRepository postRepository;

    public DataLoader(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostConstruct
    private void loadData() {
        postRepository.saveAll(List.of(
                new Post(1L, "test 1"),
                new Post(2L, "test 2"),
                new Post(3L, "test 3")
        ));
    }
}


