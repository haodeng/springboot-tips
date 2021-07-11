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

## N+1 issue

    curl http://localhost:8080/posts

Check logs:

    Hibernate: select post0_.id as id1_2_, post0_.created_date as created_2_2_, post0_.last_modified_date as last_mod3_2_, post0_.category_id as category5_2_, post0_.name as name4_2_, post0_.user_id as user_id6_2_ from posts post0_
    Hibernate: select category0_.id as id1_0_0_, category0_.created_date as created_2_0_0_, category0_.last_modified_date as last_mod3_0_0_, category0_.description as descript4_0_0_, category0_.name as name5_0_0_ from categories category0_ where category0_.id=?
    Hibernate: select user0_.id as id1_4_0_, user0_.created_date as created_2_4_0_, user0_.last_modified_date as last_mod3_4_0_, user0_.address_1 as address_4_4_0_, user0_.address_2 as address_5_4_0_, user0_.city as city6_4_0_, user0_.country as country7_4_0_, user0_.postcode as postcode8_4_0_, user0_.email as email9_4_0_, user0_.enabled as enabled10_4_0_, user0_.first_name as first_n11_4_0_, user0_.last_name as last_na12_4_0_, user0_.telephone as telepho13_4_0_ from users user0_ where user0_.id=?
    Hibernate: select category0_.id as id1_0_0_, category0_.created_date as created_2_0_0_, category0_.last_modified_date as last_mod3_0_0_, category0_.description as descript4_0_0_, category0_.name as name5_0_0_ from categories category0_ where category0_.id=?
    Hibernate: select category0_.id as id1_0_0_, category0_.created_date as created_2_0_0_, category0_.last_modified_date as last_mod3_0_0_, category0_.description as descript4_0_0_, category0_.name as name5_0_0_ from categories category0_ where category0_.id=?
    Hibernate: select user0_.id as id1_4_0_, user0_.created_date as created_2_4_0_, user0_.last_modified_date as last_mod3_4_0_, user0_.address_1 as address_4_4_0_, user0_.address_2 as address_5_4_0_, user0_.city as city6_4_0_, user0_.country as country7_4_0_, user0_.postcode as postcode8_4_0_, user0_.email as email9_4_0_, user0_.enabled as enabled10_4_0_, user0_.first_name as first_n11_4_0_, user0_.last_name as last_na12_4_0_, user0_.telephone as telepho13_4_0_ from users user0_ where user0_.id=?
    Hibernate: select comments0_.post_id as post_id1_3_0_, comments0_.comments_id as comments2_3_0_, comment1_.id as id1_1_1_, comment1_.created_date as created_2_1_1_, comment1_.last_modified_date as last_mod3_1_1_, comment1_.description as descript4_1_1_ from posts_comments comments0_ inner join comments comment1_ on comments0_.comments_id=comment1_.id where comments0_.post_id=?
    Hibernate: select comments0_.post_id as post_id1_3_0_, comments0_.comments_id as comments2_3_0_, comment1_.id as id1_1_1_, comment1_.created_date as created_2_1_1_, comment1_.last_modified_date as last_mod3_1_1_, comment1_.description as descript4_1_1_ from posts_comments comments0_ inner join comments comment1_ on comments0_.comments_id=comment1_.id where comments0_.post_id=?
    Hibernate: select comments0_.post_id as post_id1_3_0_, comments0_.comments_id as comments2_3_0_, comment1_.id as id1_1_1_, comment1_.created_date as created_2_1_1_, comment1_.last_modified_date as last_mod3_1_1_, comment1_.description as descript4_1_1_ from posts_comments comments0_ inner join comments comment1_ on comments0_.comments_id=comment1_.id where comments0_.post_id=?
    Hibernate: select comments0_.post_id as post_id1_3_0_, comments0_.comments_id as comments2_3_0_, comment1_.id as id1_1_1_, comment1_.created_date as created_2_1_1_, comment1_.last_modified_date as last_mod3_1_1_, comment1_.description as descript4_1_1_ from posts_comments comments0_ inner join comments comment1_ on comments0_.comments_id=comment1_.id where comments0_.post_id=?
    Hibernate: select comments0_.post_id as post_id1_3_0_, comments0_.comments_id as comments2_3_0_, comment1_.id as id1_1_1_, comment1_.created_date as created_2_1_1_, comment1_.last_modified_date as last_mod3_1_1_, comment1_.description as descript4_1_1_ from posts_comments comments0_ inner join comments comment1_ on comments0_.comments_id=comment1_.id where comments0_.post_id=?

Lots of queries to users, categories and comments table

Solution:
Use EntityGraph, overide findAll

    @Override
    @EntityGraph(attributePaths = {"user", "category", "comments"})
    List<Post> findAll();

Now
    
    curl http://localhost:8080/posts
    
Check logs:

    Hibernate: select post0_.id as id1_2_0_, comment2_.id as id1_1_1_, category3_.id as id1_0_2_, user4_.id as id1_4_3_, post0_.created_date as created_2_2_0_, post0_.last_modified_date as last_mod3_2_0_, post0_.category_id as category5_2_0_, post0_.name as name4_2_0_, post0_.user_id as user_id6_2_0_, comment2_.created_date as created_2_1_1_, comment2_.last_modified_date as last_mod3_1_1_, comment2_.description as descript4_1_1_, comments1_.post_id as post_id1_3_0__, comments1_.comments_id as comments2_3_0__, category3_.created_date as created_2_0_2_, category3_.last_modified_date as last_mod3_0_2_, category3_.description as descript4_0_2_, category3_.name as name5_0_2_, user4_.created_date as created_2_4_3_, user4_.last_modified_date as last_mod3_4_3_, user4_.address_1 as address_4_4_3_, user4_.address_2 as address_5_4_3_, user4_.city as city6_4_3_, user4_.country as country7_4_3_, user4_.postcode as postcode8_4_3_, user4_.email as email9_4_3_, user4_.enabled as enabled10_4_3_, user4_.first_name as first_n11_4_3_, user4_.last_name as last_na12_4_3_, user4_.telephone as telepho13_4_3_ from posts post0_ left outer join posts_comments comments1_ on post0_.id=comments1_.post_id left outer join comments comment2_ on comments1_.comments_id=comment2_.id left outer join categories category3_ on post0_.category_id=category3_.id left outer join users user4_ on post0_.user_id=user4_.id

Only one query to get all
 
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