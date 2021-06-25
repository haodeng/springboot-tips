package demo.hao;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;

@RestController
@RequestMapping("/posts")
class DemoController {
    private final PostRepository postRepository;

    public DemoController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @ResponseBody
    @GetMapping
    Flux<Post> getPosts() {
        return postRepository.findAll();
    }

    @MessageMapping("post-stream")
    public Flux<Post> postStream() {
        // supply 1 post per sec
        return Flux.interval(Duration.ofSeconds(1))
                .flatMap(id -> postRepository.findById(id));
    }


    @GetMapping("/{id}")
    Mono<Post> getPostById(@PathVariable Long id) {
        return postRepository.findById(id);
    }

    @PostMapping
    Mono<Post> postPost(@RequestBody Post Post) {
        return postRepository.save(Post);
    }

}

