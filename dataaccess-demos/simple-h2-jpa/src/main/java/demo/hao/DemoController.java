package demo.hao;


import org.springframework.data.domain.Sort;
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

    @GetMapping("/search/name/{name}")
    Iterable<Post> searchByName(@PathVariable String name) {
        return postRepository.findByName(name);
    }

    @GetMapping("/search/name/{name}/ignorecase")
    Iterable<Post> searchByNameIgnoreCase(@PathVariable String name) {
        return postRepository.findByNameIgnoreCase(name);
    }

    @GetMapping("/search/name/{name}/sort")
    Iterable<Post> searchByNameSorted(@PathVariable String name) {
        return postRepository.findByName(name, Sort.by(Sort.Direction.DESC, "name"));
    }

    @GetMapping("/search/partialname/{partialName}")
    Iterable<Post> searchByNameContaining(@PathVariable String partialName) {
        return postRepository.findByNameContaining(partialName);
    }

    @GetMapping("/search/partialname/{partialName}/ignorecase")
    Iterable<Post> searchByNameContainingIgnoreCase(@PathVariable String partialName) {
        return postRepository.findByNameIgnoreCaseContaining(partialName);
    }

    @GetMapping("/search/partialname/{partialName}/sort")
    Iterable<Post> searchByNameSortDesc(@PathVariable String partialName) {
        return postRepository.findByNameContaining(partialName, Sort.by(Sort.Direction.DESC, "name"));
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

