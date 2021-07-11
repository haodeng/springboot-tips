## Hibernate second level cache
It is designed to be unaware of the actual cache provider, we choose ehcache as provider.
For hibernate < 5.3, EhCache 2 is used

    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-ehcache</artifactId>  
    </dependency>

For hibernate >= 5.3, use jcache and its impl EhCache3
     
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-jcache</artifactId>
        </dependency>
        <dependency>
            <groupId>org.ehcache</groupId>
            <artifactId>ehcache</artifactId>
            <version>3.9.3</version>
        </dependency>


Add to properties:

    hibernate.cache.use_second_level_cache=true
    # For hibernate >= 5.3
    hibernate.cache.region.factory_class=org.hibernate.cache.jcache.JCacheRegionFactory
    
    # For hibernate < 5.3
    #hibernate.cache.region.factory_class=org.hibernate.cache.ehcache.EhCacheRegionFactory
    
Mark entity Cacheable

    @Cacheable
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    class Post {
    
Demo

    curl http://localhost:8080/posts/100
    {"id":100,"name":"test 1"}
    
    In log:
    Hibernate: select post0_.id as id1_0_0_, post0_.name as name2_0_0_ from post post0_ where post0_.id=?
    
    # 2nd same request
    curl http://localhost:8080/posts/100
    {"id":100,"name":"test 1"}
    
    # No select sql in the log, take from cache directly

## Test jpa

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.hamcrest</groupId>
                    <artifactId>hamcrest-core</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>junit</groupId>
                    <artifactId>junit</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

Add annotation:

    @ExtendWith(SpringExtension.class)
    @DataJpaTest
    public class PostRepositoryTest {
        @Autowired
        private PostRepository postRepository;
        ...