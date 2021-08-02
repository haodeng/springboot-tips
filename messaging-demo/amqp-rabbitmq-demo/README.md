        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
        
Push message to rabbitmq

    @PostMapping("/posts")
    ResponseEntity<?> pushPostToRabbit(@RequestBody Post post) {
        this.template.convertAndSend(
                "post-exchange",
                "new-post",
                post);

        return ResponseEntity.created(URI.create("/posts")).build();
    }
    
Consumer message and handle

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "post-exchange", type = ExchangeTypes.TOPIC),
                    key = "new-post"))
    public void processNewPostViaAmqp(Post post) {
        log.debug("Consuming => " + post);
        this.repository.save(post);
    }
    
Test

run RabbitAmqpDemoTest using TestContainer

        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>rabbitmq</artifactId>
            <scope>test</scope>
        </dependency>