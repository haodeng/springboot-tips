package demo.hao;

import demo.hao.service.MyDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetryDemoController {
    @Autowired
    MyDemoService myDemoService;

    @GetMapping("/retry")
    public String retryAndWorks() {
        return myDemoService.simulateFailedButBackToNormal();
    }

    @GetMapping("/retry-and-fallback")
    public String retryAndFallback() {
        return myDemoService.simulateFailedThenFallback();
    }

}
