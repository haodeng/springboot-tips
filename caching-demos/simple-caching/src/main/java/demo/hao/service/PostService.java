package demo.hao.service;


import demo.hao.dao.PostRepository;
import demo.hao.model.Post;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @SneakyThrows
    @Cacheable("posts")
    public Iterable<Post> getPosts() {
        log.info("getPosts, Sleep for 5s, simulate a slow backend");
        Thread.sleep(5 * 1000);

        return postRepository.findAll();
    }

    @SneakyThrows
    @Cacheable(value="post", key="#id")
    public Optional<Post> getPostById(Long id) {
        log.info("getPostById, Sleep for 5s, simulate a slow backend");
        Thread.sleep(5 * 1000);

        return postRepository.findById(id);
    }

    @Caching(put = @CachePut(cacheNames = "post", key = "#post.id"),
            evict = @CacheEvict(cacheNames = "posts", allEntries = true))
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public boolean postExists(Long id) {
        return postRepository.existsById(id);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "post", key = "#id"),
            @CacheEvict(cacheNames = "posts", allEntries = true)
    })
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
