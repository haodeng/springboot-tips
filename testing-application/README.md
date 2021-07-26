# Testing
Reuse h2 jpa project for testing

        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

## Unit test PostController
Mock PostService
    
    @MockBean
    private PostService postService;


## Test reactively
PostControllerReactiveTest

Instruct the test instance to load the PositionController

    @WebFluxTest(controllers = {PostController.class})
    
    @Autowired
    private WebTestClient client;

## Test imperatively
PostControllerTest

If using imperative way of testing, use:

    @WebMvcTest(value = PostController.class)

    @Autowired
    private MockMvc mockMvc;

    
## Unit test Repository
Use

    @DataJpaTest
@DataJpaTest annotation loads and configures an PostRepository bean, there is no need to mock it anyway.

## Other test slices
* @JsonTest
* @WebMvcTest
* @WebFluxText
* @DataJpaTest
* @JdbcTest
* @DataJdbcTest
* @JooqTest
* @DataMongoTest
* @DataNeo4jTest
* @DataRedisTest
* @DataLdapTest
* @RestClientTest
* @AutoConfigureRestDocs
* @WebServiceClientTest

## Integration test PostController
No need to mock PostService, run app as test server and make real http calls

    @SpringBootTest(classes = {TestDemoApplication.class},
            webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    
### Reactive
PostControllerIntegrationReactiveTest

        @Autowired
        private WebTestClient client;
        ...

### Imperative
PostControllerIntegrationTest

For imperative testing, use TestRestTemplate

    @Autowired
    TestRestTemplate restTemplate;

### Integration test with real db
h2 is the default test db, but sometimes the sql works different in other dbs, for example adding or minus date function.
We want to test with a real db.
By far, the best solution is test container.

        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>mysql</artifactId>
            <version>1.15.3</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>1.15.3</version>
            <scope>test</scope>
        </dependency>

Create mysql container in integration test

    @Testcontainers
    @SpringBootTest(classes = {TestDemoApplication.class},
            webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    class PostControllerIntegrationWithRealMysqlDBTest {
        @Autowired
        TestRestTemplate restTemplate;
   
        private static final DockerImageName MYSQL_80_IMAGE = DockerImageName.parse("mysql:8.0.24");
    
        @Container
        public static MySQLContainer<?> mysql = new MySQLContainer<>(MYSQL_80_IMAGE);
    
        @DynamicPropertySource
        static void registerPostgresqlProperties(DynamicPropertyRegistry registry) {
            registry.add("spring.jpa.database-platform", MySQL8Dialect.class::getName);
            registry.add("spring.datasource.url", () -> mysql.getJdbcUrl());
            registry.add("spring.datasource.username", () -> mysql.getUsername());
            registry.add("spring.datasource.password", () -> mysql.getPassword());
        }
        
 
        
## Mocking
Springboot auto import Mockito, two ways to mock

### BDDMockito (recommended, more readable)

    BDDMockito.given(postService.getPosts())
                    .willReturn(List.of(new Post(1L, "test 1"),
                            new Post(2L, "test 2"),
                            new Post(3L, "test 3")));

### Mockito

    Mockito.when(postService.getPosts())
                    .thenReturn(List.of(new Post(1L, "test 1"),
                            new Post(2L, "test 2"),
                            new Post(3L, "test 3")));    