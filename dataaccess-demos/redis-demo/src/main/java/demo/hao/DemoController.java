package demo.hao;


import org.springframework.data.redis.core.RedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
class DemoController {
    private final RedisOperations<String, Post> redisOperations;

    public DemoController(RedisOperations<String, Post> redisOperations) {
        this.redisOperations = redisOperations;
    }

    @GetMapping
    Iterable<Post> getPosts() {
        return redisOperations.keys("*")
                .stream()
                .map(id -> redisOperations.opsForValue().get(id))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    Optional<Post> getPostById(@PathVariable String id) {
        Iterator iterator = redisOperations.keys(id)
                .iterator();

        return iterator.hasNext()
                ? Optional.ofNullable(redisOperations.opsForValue().get(iterator.next()))
                : Optional.empty();
    }

}

