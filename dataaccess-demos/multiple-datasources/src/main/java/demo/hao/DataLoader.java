package demo.hao;

import java.util.List;
import demo.hao.dao.ds1.PostRepository;
import demo.hao.dao.ds2.UserRepository;
import demo.hao.model.ds1.Post;
import demo.hao.model.ds2.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
class DataLoader {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public DataLoader(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void loadData() {
        postRepository.saveAll(List.of(
                new Post("post 1"),
                new Post("post 2"),
                new Post("post 3")
        ));

        userRepository.saveAll(List.of(
                new User("user 1"),
                new User("user 2"),
                new User("user 3")
        ));
    }
}


