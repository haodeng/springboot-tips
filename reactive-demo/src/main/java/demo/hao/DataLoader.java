package demo.hao;

import com.sun.tools.javac.util.List;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class DataLoader {
    @Bean
    public ConnectionFactoryInitializer
    initializer(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(
                new ResourceDatabasePopulator(new ClassPathResource("schema.sql"))
        );
        return initializer;
    }

    @Bean
    public CommandLineRunner init(PostRepository repo) {
        return args -> {
            repo.saveAll(List.of(
                    new Post("test 1"),
                    new Post("test 2"),
                    new Post("test 3"),
                    new Post("test 4"),
                    new Post("test 5"),
                    new Post("test 6"),
                    new Post("test 7"),
                    new Post("test 8")))
                    .thenMany(repo.findAll())
                    .subscribe(System.out::println);
        };
    }
}

