package demo.hao;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    /**
     * /search/filtered?name=test%201&category=java
     * /search/filtered?name=test%201
     * /search/filtered?category=java
     */
    @GetMapping("/search/filtered")
    Iterable<Post> search(@RequestParam(required = false) String name,
                          @RequestParam(required = false) String category) {
        Post probe = new Post(name, category);
        //default, ExampleMatcher.matchingAll()
        Example<Post> example = Example.of(probe);

        return postRepository.findAll(example);
    }

    @GetMapping("/search/filtered_any")
    Iterable<Post> searchMatchAny(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String category) {
        Post probe = new Post(name, category);
        Example<Post> example = Example.of(probe, ExampleMatcher.matchingAny());

        return postRepository.findAll(example);
    }

    @GetMapping("/batch-update-failed")
    @Transactional
    public void demoTransactional() {
        getPosts().forEach(post -> {
            if (post.getId().equals(3)) {
                throw new RuntimeException("demo batch update failed, pls ignore");
            }

            post.setName(post.getName() + "_updated");
            postRepository.save(post);
        });
    }

}

