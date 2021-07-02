package demo.hao;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
class DataLoader {
    private final PostRepository postRepository;

    public DataLoader(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostConstruct
    private void loadData() {
        postRepository.deleteAll();

        postRepository.saveAll(
                IntStream.range(1, 100)
                        .mapToObj(i -> new Post("test " + i))
                        .collect(Collectors.toList())
        );
    }
}


