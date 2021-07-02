package demo.hao;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
class DemoController {
    private final PostRepository postRepository;

    public DemoController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    Iterable<Post> getPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Post> getPostById(@PathVariable String id) {
        return postRepository.findById(id);
    }

    @PostMapping
    Post postPost(@RequestBody Post Post) {
        return postRepository.save(Post);
    }

    @PutMapping("/{id}")
    ResponseEntity<Post> putPost(@PathVariable String id,
                                 @RequestBody Post Post) {

        return (postRepository.existsById(id))
                ? new ResponseEntity<>(postRepository.save(Post), HttpStatus.OK)
                : new ResponseEntity<>(postRepository.save(Post), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    void deletePost(@PathVariable String id) {
        postRepository.deleteById(id);
    }

    @GetMapping("/batch-update-failed")
    @Transactional
    public void demoTransactional() {
        getPosts().forEach(post -> {
            if (post.getId().equals("3")) {
                throw new RuntimeException("demo batch update failed, pls ignore");
            }

            post.setName(post.getName() + "_updated");
            postRepository.save(post);
        });
    }

}

