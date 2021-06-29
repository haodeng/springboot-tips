@EnableScheduling and @Scheduled

    @EnableScheduling
    @SpringBootApplication
    public class SchedulingDemoApplication {
    ...
    
Config schedulers:

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