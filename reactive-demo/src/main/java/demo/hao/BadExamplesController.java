package demo.hao;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bad")
public class BadExamplesController {
    private final BlockingService blockingService;

    /**
     * Incorrect implementation.
     *   - Imposter Method (doing work before subscribing)
     *   - Blocking in event loop
     */
    @GetMapping("example1")
    public Mono<String> getGreeting_imposter() {
        try {
            // blocking work performed during assembly, before subscribing
            String greeting = blockingService.getGreeting();
            return Mono.just(greeting);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    /**
     * Incorrect implementation.
     *   - Blocking in event loop
     */
    @GetMapping("example2")
    public Mono<String> getGreeting_blocking() {
        // blocking call on event loop
        return Mono.fromCallable(blockingService::getGreeting);
    }

    /*
     * Correct implementation
     */
    @GetMapping("example3")
    public Mono<String> getGreeting() {
        return Mono.fromCallable(blockingService::getGreeting)
                // properly schedule above blocking call on
                // scheduler meant for blocking tasks
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("example4")
    public Mono<String> getGreeting_stillBlocking() {
        // Danger Zone!!!
        try {
            // something blocking
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Mono.fromCallable(blockingService::getGreeting)
                // properly schedule above blocking call on
                // scheduler meant for blocking tasks
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("example5")
    public Mono<String> getGreeting_badWrapping() {
        //wrapped blocking code to callable
        return Mono.fromCallable(this.blockingGreeting_wrong())
                // properly schedule above blocking call on
                // scheduler meant for blocking tasks
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Callable<String> blockingGreeting_wrong() {
        try {
            // something blocking
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return blockingService::getGreeting;
    }

    @GetMapping("example6")
    public Mono<String> getGreeting_correctWrapping() {
        //wrapped blocking code to callable
        return Mono.fromCallable(this.blockingGreeting_correct())
                // properly schedule above blocking call on
                // scheduler meant for blocking tasks
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Callable<String> blockingGreeting_correct() {
        return () -> {
            try {
                // something blocking
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return blockingService.getGreeting();
        };
    }
}
