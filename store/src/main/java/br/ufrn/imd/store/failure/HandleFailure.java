package br.ufrn.imd.store.failure;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class HandleFailure {

    private Random random;
    private final Map<String, Boolean> cache;
    private final ScheduledExecutorService scheduler;


    public HandleFailure() {
        this.random = new Random();
        this.cache = new ConcurrentHashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public boolean isFailureOccurring(float probability) {
        return random.nextDouble() < probability;
    }

    public void activeFailure(String key, int duration) {
        if(!cache.containsKey(key)) {
            cache.put(key, Boolean.TRUE);
            scheduler.schedule(() -> cache.remove(key), duration, TimeUnit.SECONDS);
        }
    }

    public boolean checkFailureActivation(String key) {
        return Objects.nonNull(cache.get(key)) && cache.get(key);
    }
}
