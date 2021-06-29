package demo.hao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DemoScheduler {
    private static Logger logger = LoggerFactory.getLogger(DemoScheduler.class);

    @Scheduled(initialDelay = 1000, fixedRate = 3000)
    public void run() {
        logger.info("Job 1: " + Instant.now().toString());
    }

    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    public void run2() {
        logger.info("Job 2: " + Instant.now().toString());
    }

    // Every 15s
    @Scheduled(cron = "*/15 * * * * *")
    public void runCron() {
        logger.info("Cron job: " + Instant.now().toString());
    }
}
