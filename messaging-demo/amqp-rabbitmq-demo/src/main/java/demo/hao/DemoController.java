package demo.hao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
public class DemoController {
    private final AmqpTemplate template;

    public DemoController(AmqpTemplate template) {
        this.template = template;
    }

    @PostMapping("/posts")
    ResponseEntity<?> pushPostToRabbit(@RequestBody Post post) {
        this.template.convertAndSend(
                "post-exchange",
                "new-post",
                post);

        return ResponseEntity.created(URI.create("/posts")).build();
    }
}
