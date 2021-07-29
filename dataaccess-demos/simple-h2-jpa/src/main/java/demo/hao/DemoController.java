package demo.hao;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    Optional<PostDto> getPostById(@PathVariable Long id) {
        return postRepository.findById(id).map(PostDto::new);
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
    List<PostDto> searchByName(@PathVariable String name) {
        return postRepository.findByName(name)
                .stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/search/name/{name}/ignorecase")
    List<PostDto> searchByNameIgnoreCase(@PathVariable String name) {
        return postRepository.findByNameIgnoreCase(name)
                .stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/search/name/{name}/sort")
    List<PostDto> searchByNameSorted(@PathVariable String name) {
        return postRepository.findByName(name, Sort.by(Sort.Direction.DESC, "name"))
                .stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/search/partialname/{partialName}")
    List<PostDto> searchByNameContaining(@PathVariable String partialName) {
        return postRepository.findByNameContaining(partialName)
                .stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/search/partialname/{partialName}/ignorecase")
    List<PostDto> searchByNameContainingIgnoreCase(@PathVariable String partialName) {
        return postRepository.findByNameIgnoreCaseContaining(partialName)
                .stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/search/partialname/{partialName}/sort")
    List<PostDto> searchByNameSortDesc(@PathVariable String partialName) {
        return postRepository.findByNameContaining(partialName,
                Sort.by(Sort.Direction.DESC, "name"))
                .stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    /**
     * /search/filtered?name=test%201&category=java
     * /search/filtered?name=test%201
     * /search/filtered?category=java
     */
    @GetMapping("/search/filtered")
    List<PostDto> search(@RequestParam(required = false) String name,
                          @RequestParam(required = false) String category) {
        Post probe = new Post(name, category);
        //default, ExampleMatcher.matchingAll()
        Example<Post> example = Example.of(probe);

        return postRepository.findAll(example)
                .stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/search/filtered_any")
    List<PostDto> searchMatchAny(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String category) {
        Post probe = new Post(name, category);
        Example<Post> example = Example.of(probe, ExampleMatcher.matchingAny());

        return postRepository.findAll(example)
                .stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/name_query/{name}")
    Optional<PostDto> getPostByNameQuery(@PathVariable String name) {
        return postRepository.findByNameQuery(name).map(PostDto::new);
    }

    @GetMapping("/name_nativequery/{name}")
    Optional<PostDto> getPostByNameNativeQuery(@PathVariable String name) {
        return postRepository.findByNameNativeQuery(name).map(PostDto::new);
    }

    @PutMapping("name/{id}")
    ResponseEntity<Post> setPostNameById(@PathVariable Long id,
                                         @RequestBody String name) {
        postRepository.setPostNameById(name, id);
        return new ResponseEntity<>(postRepository.findById(id).get(), HttpStatus.OK);
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

