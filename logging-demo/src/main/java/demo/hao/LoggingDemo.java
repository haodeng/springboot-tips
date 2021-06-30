package demo.hao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;

@Slf4j
@Component
public class LoggingDemo {

    @PostConstruct
    void init() {
        log.info("start logging demo");
    }

    @Scheduled(initialDelay = 1000, fixedRate = 3000)
    public void run() {
        log.debug("Job 1: " + Instant.now().toString());

        // do something
    }

}
