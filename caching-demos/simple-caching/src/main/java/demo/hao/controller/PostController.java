package demo.hao.controller;


import demo.hao.model.Post;
import demo.hao.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    Iterable<Post> getPosts() {
        log.info("getPosts");
        return postService.getPosts();
    }

    @GetMapping("/{id}")
    Optional<Post> getPostById(@PathVariable Long id) {
        log.info("getPostById: " + id);
        return postService.getPostById(id);
    }

    @PostMapping
    Post postPost(@RequestBody Post Post) {
        return postService.savePost(Post);
    }

    @PutMapping("/{id}")
    ResponseEntity<Post> putPost(@PathVariable Long id,
                                 @RequestBody Post Post) {

        return postService.postExists(id)
                ? new ResponseEntity<>(postService.savePost(Post), HttpStatus.OK)
                : new ResponseEntity<>(postService.savePost(Post), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

}

