package demo.hao;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SimpleCachingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleCachingDemoApplication.class, args);
    }
}
