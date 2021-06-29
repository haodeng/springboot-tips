package demo.hao.controller;


import demo.hao.dao.ds1.PostRepository;
import demo.hao.model.ds1.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/posts")
class PostController {
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    Iterable<Post> getPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Post> getPostById(@PathVariable Long id) {
        return postRepository.findById(id);
    }

    @PostMapping
    Post postPost(@RequestBody Post Post) {
        return postRepository.save(Post);
    }

    @PutMapping("/{id}")
    ResponseEntity<Post> putPost(@PathVariable Long id,
                                 @RequestBody Post Post) {

        return (postRepository.existsById(id))
                ? new ResponseEntity<>(postRepository.save(Post), HttpStatus.OK)
                : new ResponseEntity<>(postRepository.save(Post), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }

}

