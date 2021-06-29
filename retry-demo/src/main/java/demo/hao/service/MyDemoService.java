package demo.hao.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface MyDemoService {

    @Retryable(value = {IllegalStateException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 1000))
    public String simulateFailedThenFallback();

    @Retryable(value = {IllegalStateException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 1000))
    public String simulateFailedButBackToNormal();

    @Recover
    public String fallback(RuntimeException e);
}
