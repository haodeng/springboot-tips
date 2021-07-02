package demo.hao;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Ehcache3CachingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ehcache3CachingDemoApplication.class, args);
    }
}
