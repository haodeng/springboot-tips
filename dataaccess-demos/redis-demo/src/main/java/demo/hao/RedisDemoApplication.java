package demo.hao;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class RedisDemoApplication {

    @Bean
    public RedisOperations<String, Post> redisOperations(RedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Post> serializer =
                new Jackson2JsonRedisSerializer<>(Post.class);

        RedisTemplate<String, Post> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());

        return template;
    }


    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }
}
