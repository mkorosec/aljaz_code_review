package com.astraser.code.challenge.gateway.analytics;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RequestCounterService {

    private final static Map<String, AtomicLong> requestCounters = new HashMap<>();

    public void incrementCounter(String path) {
        requestCounters.computeIfAbsent(path, k -> new AtomicLong(0L)).getAndIncrement();
    }

    public Map<String, AtomicLong> getRequestCount() {
        return requestCounters;
    }
}
