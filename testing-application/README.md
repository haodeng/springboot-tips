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