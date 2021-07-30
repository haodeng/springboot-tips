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
    List<PostDto> getPosts() {
        return postRepository.findAll()
                .stream()
                .map(dao -> AppUtils.daoToDto(dao))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    Optional<PostDto> getPostById(@PathVariable Long id) {
        return AppUtils.daoToDto(postRepository.findById(id));
    }

    @PostMapping
    Post postPost(@RequestBody PostDto postDto) {
        return postRepository.save(AppUtils.dtoToDao(postDto));
    }

    @PutMapping("/{id}")
    ResponseEntity<Post> putPost(@PathVariable Long id, @RequestBody PostDto post) {

        return (postRepository.existsById(id))
                ? new ResponseEntity<>(postRepository.save(AppUtils.dtoToDao(post)), HttpStatus.OK)
                : new ResponseEntity<>(postRepository.save(AppUtils.dtoToDao(post)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }

    @GetMapping("/search/name/{name}")
    List<PostDto> searchByName(@PathVariable String name) {
        return postRepository.findByName(name)
                .stream()
                .map(dao -> AppUtils.daoToDto(dao))
                .collect(Collectors.toList());
    }

    @GetMapping("/search/name/{name}/ignorecase")
    List<PostDto> searchByNameIgnoreCase(@PathVariable String name) {
        return postRepository.findByNameIgnoreCase(name)
                .stream()
                .map(dao -> AppUtils.daoToDto(dao))
                .collect(Collectors.toList());
    }

    @GetMapping("/search/name/{name}/sort")
    List<PostDto> searchByNameSorted(@PathVariable String name) {
        return postRepository.findByName(name, Sort.by(Sort.Direction.DESC, "name"))
                .stream()
                .map(dao -> AppUtils.daoToDto(dao))
                .collect(Collectors.toList());
    }

    @GetMapping("/search/partialname/{partialName}")
    List<PostDto> searchByNameContaining(@PathVariable String partialName) {
        return postRepository.findByNameContaining(partialName)
                .stream()
                .map(dao -> AppUtils.daoToDto(dao))
                .collect(Collectors.toList());
    }

    @GetMapping("/search/partialname/{partialName}/ignorecase")
    List<PostDto> searchByNameContainingIgnoreCase(@PathVariable String partialName) {
        return postRepository.findByNameIgnoreCaseContaining(partialName)
                .stream()
                .map(dao -> AppUtils.daoToDto(dao))
                .collect(Collectors.toList());
    }

    @GetMapping("/search/partialname/{partialName}/sort")
    List<PostDto> searchByNameSortDesc(@PathVariable String partialName) {
        return postRepository.findByNameContaining(partialName,
                Sort.by(Sort.Direction.DESC, "name"))
                .stream()
                .map(dao -> AppUtils.daoToDto(dao))
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
                .map(dao -> AppUtils.daoToDto(dao))
                .collect(Collectors.toList());
    }

    @GetMapping("/search/filtered_any")
    List<PostDto> searchMatchAny(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String category) {
        Post probe = new Post(name, category);
        Example<Post> example = Example.of(probe, ExampleMatcher.matchingAny());

        return postRepository.findAll(example)
                .stream()
                .map(dao -> AppUtils.daoToDto(dao))
                .collect(Collectors.toList());
    }

    @GetMapping("/name_query/{name}")
    Optional<PostDto> getPostByNameQuery(@PathVariable String name) {
        return AppUtils.daoToDto(postRepository.findByNameQuery(name));
    }

    @GetMapping("/name_nativequery/{name}")
    Optional<PostDto> getPostByNameNativeQuery(@PathVariable String name) {
        return AppUtils.daoToDto(postRepository.findByNameNativeQuery(name));
    }

    @PutMapping("name/{id}")
    ResponseEntity<PostDto> setPostNameById(@PathVariable Long id, @RequestBody String name) {
        postRepository.setPostNameById(name, id);
        return new ResponseEntity<>(AppUtils.daoToDto(postRepository.getById(id)), HttpStatus.OK);
    }

    @GetMapping("/batch-update-failed")
    @Transactional
    public void demoTransactional() {
        getPosts().stream().map(dto -> AppUtils.dtoToDao(dto)).forEach(post -> {
            if (post.getId().equals(3)) {
                throw new RuntimeException("demo batch update failed, pls ignore");
            }

            post.setName(post.getName() + "_updated");
            postRepository.save(post);
        });
    }

}

