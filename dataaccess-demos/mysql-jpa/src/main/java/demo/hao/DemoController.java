package demo.hao;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/paged")
    Iterable<Post> getPagedPosts(@RequestParam(defaultValue = "0") Integer pageNo,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(defaultValue = "id") String sortBy) {

//        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("name").descending());

        return postRepository.findAll(
                PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
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

}

