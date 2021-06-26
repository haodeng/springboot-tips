package demo.hao;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/posts")
class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    Iterable<Post> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{id}")
    Optional<Post> getPostById(@PathVariable Long id) {
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

