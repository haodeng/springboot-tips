package demo.hao;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class CacheEventLogger implements CacheEventListener {

    @Override
    public void onEvent(CacheEvent cacheEvent) {
        log.info("Cache event = {}, Key = {},  Old value = {}, New value = {}",
                cacheEvent.getType(),
                cacheEvent.getKey(),
                cacheEvent.getOldValue(),
                cacheEvent.getNewValue());
    }
}