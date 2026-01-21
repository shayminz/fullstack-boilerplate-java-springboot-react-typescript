package co.devskills.springbootboilerplate.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private static final int LIMIT = 10;
    private static final long WINDOW_MS = 60_000;

    private final ConcurrentHashMap<String, ClientWindow> store = new ConcurrentHashMap<>();

    public boolean allowRequest(String clientId) {
        long now = System.currentTimeMillis();

        AtomicInteger countHolder = new AtomicInteger();

        store.compute(clientId, (key, existing) -> {
            if (existing == null || now - existing.windowStart > WINDOW_MS) {
                // New window
                countHolder.set(1);
                return new ClientWindow(now, new AtomicInteger(1));
            } else {
                // Existing window - increment
                countHolder.set(existing.counter.incrementAndGet());
                return existing;
            }
        });

        return countHolder.get() <= LIMIT;
    }

    static class ClientWindow {
        long windowStart;
        AtomicInteger counter;

        ClientWindow(long windowStart, AtomicInteger counter) {
            this.windowStart = windowStart;
            this.counter = counter;
        }
    }
}