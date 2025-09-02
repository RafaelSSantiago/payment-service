package com.example.paymentservice.interfaces.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IdempotencyCache {
    private static final Map<String, PaymentResponse> CACHE = new ConcurrentHashMap<>();

    private IdempotencyCache() {}

    public static PaymentResponse tryGet(String key){
        if(key == null || key.isBlank()) return null;
        return CACHE.get(key);
    }

    public static void remember(String key, PaymentResponse response){
        if(key == null || key.isBlank()) return;
        CACHE.putIfAbsent(key, response);
    }
}
