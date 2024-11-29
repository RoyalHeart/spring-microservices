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

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
@Component
@Slf4j
public class PricingPlanService {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(1000, Refill.greedy(1000, Duration.ofMinutes(1)))).build();

    @SuppressWarnings("unused")
    public Bucket resolveBucket(String username, String plan) {
        return cache.computeIfAbsent(username, (temp) -> {
            return newBucket(plan);
        });
    }

    public Bucket getDefaultBucket() {
        return bucket;
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
