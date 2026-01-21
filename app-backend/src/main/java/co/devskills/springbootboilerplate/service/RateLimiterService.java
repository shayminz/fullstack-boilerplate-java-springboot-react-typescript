package co.devskills.springbootboilerplate.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    //Write your code here

    public boolean allowRequest(String clientId) {
        return true
    }
}