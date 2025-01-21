/*******************************************************************************
 * Class        :className
 * Created date :2024/11/26
 * Lasted date  :2024/11/26
 * Author       :TamTH1
 * Change log   :2024/11/26 01-00 TamTH1 create a new
******************************************************************************/
package com.example.gateway_service.service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.gateway_service.prop.CustomProperties;
import com.example.gateway_service.prop.RateLimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Service
@Slf4j
public class PricingPlanService {
    private CustomProperties customProperties;

    public PricingPlanService(CustomProperties customProperties) {
        this.customProperties = customProperties;
    }

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String username, String plan) {
        return cache.computeIfAbsent(username, _ -> newBucket(plan));
    }

    public Bucket getDefaultBucket() {
        RateLimit rateLimitConfig = customProperties.getRateLimit();
        return Bucket.builder()
                .addLimit(Bandwidth.classic(rateLimitConfig.getCapacity(),
                        Refill.greedy(rateLimitConfig.getRefillAmount(),
                                Duration.ofMinutes(rateLimitConfig.getRefillMinutes()))))
                .build();
    }

    private Bucket newBucket(String plan) {
        PricingPlan pricingPlan = PricingPlan.FREE;
        try {
            pricingPlan = PricingPlan.valueOf(plan);
        } catch (Exception e) {
            log.debug("> Invalid plan: {}", plan);
        }
        return Bucket.builder()
                .addLimit(pricingPlan.getBandwidth())
                .build();
    }

}
