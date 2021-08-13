package demo.hao;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {TestDemoApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerIntegrationTest {
    @Autowired
    TestRestTemplate restTemplate;

    // When started, DataLoader already loads 3 posts

    @Test
    void getPosts() {
        ResponseEntity<String> response = restTemplate
                .exchange("/posts",
                        HttpMethod.GET,
                        null,
                        String.class);

        String expected = "[{\"id\":1,\"name\":\"test 1\"},{\"id\":2,\"name\":\"test 2\"},{\"id\":3,\"name\":\"test 3\"}]";
        assertThat(response.getBody()).isEqualTo(expected);

        // alternatively, use JsonPath test
        Object dataObject = JsonPath.parse(response.getBody()).read("$[?(@.id == 2)]");
        String dataString = dataObject.toString();
        assertThat(dataString).contains("2");
        assertThat(dataString).contains("test 2");
    }

    @Test
    void getPostById() {
        ResponseEntity<String> response = restTemplate
                .exchange("/posts/1",
                        HttpMethod.GET,
                        null,
                        String.class);

        String expected = "{\"id\":1,\"name\":\"test 1\"}";
        assertThat(response.getBody()).isEqualTo(expected);
    }
}