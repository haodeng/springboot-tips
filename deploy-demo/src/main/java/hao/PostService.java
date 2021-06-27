package hao;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    Iterable<Post> getPosts() {
        return postRepository.findAll();
    }

    Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    Post savePost(Post Post) {
        return postRepository.save(Post);
    }

    boolean postExists(Long id) {
        return postRepository.existsById(id);
    }

    void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
