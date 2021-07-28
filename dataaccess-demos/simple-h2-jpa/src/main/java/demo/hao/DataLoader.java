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
                new Post("test 1", "java"),
                new Post("test 2", "spring"),
                new Post("test 3", "java")
        ));
    }
}


