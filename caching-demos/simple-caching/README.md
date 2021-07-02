## Service layer caching
Supported Cache Providers
* Generic
* JCache (JSR-107) (EhCache 3, Hazelcast, Infinispan, and others)
* EhCache 2.x
* Hazelcast
* Infinispan
* Couchbase
* Redis
* Caffeine
* Simple

f you do not add any specific cache library, Spring Boot auto-configures a simple provider that uses concurrent maps in memory.

 a simple implementation using a ConcurrentHashMap as the cache store is configured.
 
 To disable caching in certain env:
 
    spring.cache.type=none 

Caching annotation
* @Cacheable: Triggers cache population.
* @CacheEvict: Triggers cache eviction.
* @CachePut: Updates the cache without interfering with the method execution.
* @Caching: Regroups multiple cache operations to be applied on a method.
* @CacheConfig: Shares some common cache-related settings at class-level.

Add cacheable to service

    @Cacheable("posts")
    public Iterable<Post> getPosts() {
    
    @Cacheable(value="post", key="#id")
    public Optional<Post> getPostById(Long id) {
        
    @Caching(put = @CachePut(cacheNames = "post", key = "#post.id"),
                evict = @CacheEvict(cacheNames = "posts", allEntries = true))
    public Post savePost(Post post) {
    
    @Caching(evict = {
                @CacheEvict(cacheNames = "post", key = "#id"),
                @CacheEvict(cacheNames = "posts", allEntries = true)})
    public void deletePost(Long id) {
    
Demo

    # 1st time slow
    curl http://localhost:8080/posts
    
    # fast
    curl http://localhost:8080/posts
    
    # 1st time slow
    curl http://localhost:8080/posts/1
    # fast
    curl http://localhost:8080/posts/1
    
    # add post, will add to 'post' cache too
    curl -H "Content-Type: application/json" -d '{"id":4,"name":"test 4"}' -X POST http://localhost:8080/posts/4
    # fast
    curl http://localhost:8080/posts/4
    
    # update post, will update 'post' cache too
    curl -H "Content-Type: application/json" -d '{"id":4,"name":"test 4 updated"}' -X PUT http://localhost:8080/posts/4
    # fast
    curl http://localhost:8080/posts/4
    


