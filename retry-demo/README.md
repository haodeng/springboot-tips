## Spring retry annotations

* @EnableRetry – to enable spring retry in spring boot project
* @Retryable – to indicate any method to be a candidate of retry
* @Recover – to specify fallback method!

EnableRetry

    @EnableRetry
    @SpringBootApplication
    public class RetryDemoApplication {

Retryable and Recover
    
    public interface MyDemoService {
    
        @Retryable(value = {IllegalStateException.class},
                maxAttempts = 5,
                backoff = @Backoff(delay = 1000))
        public String simulateFailedThenFallback();
        
        @Recover
        public String fallback(RuntimeException e);

Retryable

if we get IllegalStateException then retry maximum 5 times and delay 1 second for each retry.

Recover

After 5 retries, still get IllegalStateException, fallback to this method

## Demo

    curl http://localhost:8080/retry
    2021-06-29 21:58:33.499  INFO 18306 --- [nio-8080-exec-1] demo.hao.service.MyDemoServiceImpl       : Retry: 1
    2021-06-29 21:58:34.504  INFO 18306 --- [nio-8080-exec-1] demo.hao.service.MyDemoServiceImpl       : Retry: 2
    2021-06-29 21:58:35.508  INFO 18306 --- [nio-8080-exec-1] demo.hao.service.MyDemoServiceImpl       : Retry: 3
    
    curl http://localhost:8080/retry-and-fallback
    2021-06-29 22:02:34.135  INFO 18338 --- [nio-8080-exec-1] demo.hao.service.MyDemoServiceImpl       : Retry: 1
    2021-06-29 22:02:35.137  INFO 18338 --- [nio-8080-exec-1] demo.hao.service.MyDemoServiceImpl       : Retry: 2
    2021-06-29 22:02:36.138  INFO 18338 --- [nio-8080-exec-1] demo.hao.service.MyDemoServiceImpl       : Retry: 3
    2021-06-29 22:02:37.141  INFO 18338 --- [nio-8080-exec-1] demo.hao.service.MyDemoServiceImpl       : Retry: 4
    2021-06-29 22:02:38.144  INFO 18338 --- [nio-8080-exec-1] demo.hao.service.MyDemoServiceImpl       : Retry: 5
    2021-06-29 22:02:38.144  INFO 18338 --- [nio-8080-exec-1] demo.hao.service.MyDemoServiceImpl       : fallbacked due to java.lang.IllegalStateException: Simulate demo error, ignore pls.


