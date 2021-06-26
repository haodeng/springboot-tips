# Testing
Reuse h2 jpa project for testing

        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

## Unit test PostController (Reactive)
Mock PostService
    
    @MockBean
    private PostService postService;

Instruct the test instance to load the PositionController

    @WebFluxTest(controllers = {PostController.class})

If using imperative way of testing, use:

    @Autowired
    private MockMvc mockMvc;
    
    @WebMvcTest(value = PostController.class)
    

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

## Integration test PostController (Reactive)
No need to mock PostService, run app as test server and make real http calls

    @SpringBootTest(classes = {TestDemoApplication.class},
            webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    class PostControllerIntegrationTest {
        @Autowired
        private WebTestClient client;
        ...

For imperative testing, use TestRestTemplate
     