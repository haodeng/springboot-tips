package demo.hao;

import org.springframework.stereotype.Service;

@Service
public class BlockingService {

    public String getGreeting() {
        try {
            // Simulate blocking I/O call with a blocking sleep call
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        return "Hi";
    }
}
