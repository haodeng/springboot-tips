package demo.hao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MyDemoServiceImpl implements MyDemoService{

    private static Logger logger = LoggerFactory.getLogger(MyDemoServiceImpl.class);

    private static int retry1 = 0, retry2 = 0;
    @Override
    public String simulateFailedThenFallback() {
        retry1++;
        logger.info("Retry: " + retry1);

        throw new IllegalStateException("Simulate demo error, ignore pls.");
    }

    @Override
    public String simulateFailedButBackToNormal() {
        retry2++;
        logger.info("Retry: " + retry2);
        if (retry2 < 3) {
            throw new IllegalStateException("Simulate demo error, ignore pls.");
        }
        else {
            return "Works after retry " + retry2;
        }
    }

    @Override
    public String fallback(RuntimeException e) {
        logger.info("fallbacked due to " + e);
        return "Fallback works";
    }
}
