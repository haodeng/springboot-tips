
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        
Run redis

    docker start redis

By default, boot connects to localhost:6379

Need a RedisOperations

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
    
Test:

    curl http://localhost:8080/posts
    curl http://localhost:8080/posts/100
