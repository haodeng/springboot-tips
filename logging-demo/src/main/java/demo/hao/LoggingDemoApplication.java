package demo.hao;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LoggingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoggingDemoApplication.class, args);
    }
}
