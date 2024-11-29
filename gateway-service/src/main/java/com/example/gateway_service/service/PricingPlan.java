/*******************************************************************************
 * Class        :className
 * Created date :2024/11/26
 * Lasted date  :2024/11/26
 * Author       :TamTH1
 * Change log   :2024/11/26 01-00 TamTH1 create a new
******************************************************************************/
package com.example.gateway_service.service;

import java.time.Duration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import lombok.Getter;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Getter
public enum PricingPlan {

    FREE(Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)))),
    BASIC(Bandwidth.classic(40, Refill.greedy(40, Duration.ofMinutes(1)))),
    PROFESSIONAL(Bandwidth.classic(60, Refill.greedy(60, Duration.ofMinutes(1))));

    PricingPlan(Bandwidth bandwidth) {
        this.bandwidth = bandwidth;
    }

    Bandwidth bandwidth;
}
