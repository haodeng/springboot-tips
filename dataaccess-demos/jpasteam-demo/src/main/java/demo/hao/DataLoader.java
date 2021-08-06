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
                new Post("test 3", "java"),
                new Post("test 4", "k8s"),
                new Post("test 5", "java"),
                new Post("test 6", "spring"),
                new Post("test 7", "java"),
                new Post("test 8", "java"),
                new Post("test 9", "java"),
                new Post("test 10", "spring")
        ));
    }
}


