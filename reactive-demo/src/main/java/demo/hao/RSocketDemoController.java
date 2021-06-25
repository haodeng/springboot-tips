package demo.hao;

import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Controller
public class RSocketDemoController {
    private final RSocketRequester requester;

    public RSocketDemoController(RSocketRequester.Builder builder) {
        this.requester = builder.tcp("localhost", 8081);
    }

    // posts supplied by DemoController via Rsocket
    @ResponseBody
    @GetMapping(value = "/post-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Post> getPostStream() {
        return requester.route("post-stream")
                .data("Requesting posts")
                .retrieveFlux(Post.class);
    }

}
