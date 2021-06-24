package demo.hao;

import com.sun.tools.javac.util.List;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
class DataLoader {
    private final RedisOperations<String, Post> redisOperations;

    public DataLoader(RedisOperations<String, Post> redisOperations) {
        this.redisOperations = redisOperations;
    }

    @PostConstruct
    private void loadData() {
        List.of(new Post(100L, "test 1"),
                new Post(101L, "test 2"),
                new Post(102L, "test 3")
        ).forEach(this::savePost);
    }

    private void savePost(Post post) {
        redisOperations.opsForValue()
                .set(String.valueOf(post.getId()), post);
    }
}


