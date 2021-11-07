package demo.hao;

import java.util.List;
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
        postRepository.deleteAll();

        postRepository.saveAll(List.of(
                new Post("100", "test 1"),
                new Post("101", "test 2"),
                new Post("102", "test 3")
        ));
    }
}


