package demo.hao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequestMapping("/greeting/v1")
public class GreetingControllerV1 {

    @Value("${greeting.name: default name}")
    private String name;

    @Value("${greeting.welcome: ${greeting.name} welcome default}")
    private String welcomeMessage;

    @GetMapping
    String greeting() {
        return name;
    }

    @GetMapping("/welcome")
    String welcome() {
        return welcomeMessage;
    }

}
