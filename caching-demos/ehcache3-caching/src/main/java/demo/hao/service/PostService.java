package demo.hao.service;


import demo.hao.dao.PostRepository;
import demo.hao.model.Post;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@CacheConfig(cacheNames={"post"})
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @SneakyThrows
    public Iterable<Post> getPosts() {
        log.info("getPosts, Sleep for 5s, simulate a slow backend");
        Thread.sleep(2 * 1000);

        return postRepository.findAll();
    }

    @SneakyThrows
    @Cacheable(key="#id")
    public Optional<Post> getPostById(Long id) {
        log.info("getPostById, Sleep for 5s, simulate a slow backend");
        Thread.sleep(2 * 1000);

        return postRepository.findById(id);
    }

    @CachePut(key = "#post.id")
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public boolean postExists(Long id) {
        return postRepository.existsById(id);
    }

    @CacheEvict(key = "#id")
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
