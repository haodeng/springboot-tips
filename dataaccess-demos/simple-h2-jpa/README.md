The H2 database driver scope runtime indicates that it will be present in the runtime and test classpath but not in the compile classpath.

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>

## More queries 
In PostRepository add:

findBy{Property name}

findBy{Property name}|IgnoreCase|Containing

        Iterable<Post> findByName(String name);
        Iterable<Post> findByNameIgnoreCase(String name);
        Iterable<Post> findByName(String name, Sort sort);
    
        Iterable<Post> findByNameContaining(String partialName);
        Iterable<Post> findByNameIgnoreCaseContaining(String partialName);
        Iterable<Post> findByNameContaining(String partialName, Sort sort);

## Query and Native query

        @Query("select p from Post p where p.name = ?1")
        Optional<Post> findByNameQuery(String name);
    
        @Query(value = "SELECT * FROM POST WHERE NAME = ?1", nativeQuery = true)
        Optional<Post> findByNameNativeQuery(String name);
        
        // Modifying Queries
        @Transactional
        @Modifying
        @Query("update Post p set p.name = ?1 where p.id = ?2")
        int setPostNameById(String name, Long postId);
        
## Filter, search by params
Use "Example"

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
        
## Transactional

    @GetMapping("/batch-update-failed")
    @Transactional
    public void demoTransactional() {
    
Demo

    curl http://localhost:8080/posts
    
    # exception, all updates are rolled back
    curl http://localhost:8080/posts/batch-update-failed
    
    # no records should be updated
    curl http://localhost:8080/posts
    
    curl http://localhost:8080/posts/search/name/test%201
    [{"id":"1","name":"test 1"}]
    
    curl http://localhost:8080/posts/search/partialname/test
    [{"id":"1","name":"test 1"},{"id":"2","name":"test 2"},{"id":"3","name":"test 3"}]
    
    curl http://localhost:8080/posts/search/partialname/Test/ignorecase
    [{"id":"1","name":"test 1"},{"id":"2","name":"test 2"},{"id":"3","name":"test 3"}]
    
    curl "http://localhost:8080/posts/name_nativequery/test%202"
    {"name":"test 2","id":2,"description":"spring:test 2","category":"spring"}



     