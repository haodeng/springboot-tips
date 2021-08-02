package demo.hao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostAmqpService {
    private final PostRepository repository;

    public PostAmqpService(PostRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "post-exchange", type = ExchangeTypes.TOPIC),
                    key = "new-post"))
    public void processNewPostViaAmqp(Post post) {
        log.debug("Consuming => " + post);
        this.repository.save(post);
    }
}
