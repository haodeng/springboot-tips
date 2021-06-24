package demo.hao;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting/v2")
class GreetingControllerV2 {
    private final Greeting greeting;

    public GreetingControllerV2(Greeting greeting) {
        this.greeting = greeting;
    }

    @GetMapping
    String greeting() {
        return greeting.getName();
    }

    @GetMapping("/welcome")
    String welcome() {
        return greeting.getWelcome();
    }
}

