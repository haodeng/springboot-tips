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
                new Post("1", "test 1"),
                new Post("2", "test 2"),
                new Post("3", "test 3")
        ));
    }
}


