/*******************************************************************************
 * Class        :className
 * Created date :2024/08/19
 * Lasted date  :2024/08/19
 * Author       :TamTH1
 * Change log   :2024/08/19 01-00 TamTH1 create a new
******************************************************************************/
package com.example.gateway_service.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.gateway_service.config.JwtUtil;
import com.example.gateway_service.service.PricingPlanService;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
@Slf4j
public class RateLimitFilter implements GatewayFilter {

    private final JwtUtil jwtUtil;
    @Autowired
    private final PricingPlanService pricingPlanService;
    private final Bucket publicBucket;

    public RateLimitFilter(JwtUtil jwtUtil, PricingPlanService pricingPlanService) {
        this.jwtUtil = jwtUtil;
        this.pricingPlanService = pricingPlanService;
        this.publicBucket = pricingPlanService.getDefaultBucket();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = "";
        try {
            authHeader = this.getAuthHeader(request);
        } catch (Exception e) {
            log.debug("> No Authorization");
        }
        boolean hasAuth = StringUtils.isNotBlank(authHeader);
        if (hasAuth) {
            final String[] authHeaders = authHeader.split("\s");
            final String jwt = authHeaders[1];
            String username = jwtUtil.extractUserName(jwt);
            String plan = jwtUtil.extractAllClaims(jwt).get("plan").toString();
            log.debug("> username {} plan {}", username, plan);
            Bucket bucket = pricingPlanService.resolveBucket(username, plan);
            ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
            if (probe.isConsumed()) {
                exchange.getResponse().getHeaders().add("X-Rate-Limit-Remaining",
                        Long.toString(probe.getRemainingTokens()));
            } else {
                long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
                exchange.getResponse().getHeaders()
                        .add("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
                return onError(exchange, HttpStatus.TOO_MANY_REQUESTS);
            }
        } else {
            // Default bucket for all user on public api
            ConsumptionProbe probe = publicBucket.tryConsumeAndReturnRemaining(1);
            if (probe.isConsumed()) {
                exchange.getResponse().getHeaders().add("X-Rate-Limit-Remaining",
                        Long.toString(probe.getRemainingTokens()));
            } else {
                long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
                exchange.getResponse().getHeaders()
                        .add("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
                return onError(exchange, HttpStatus.TOO_MANY_REQUESTS);
            }
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }
}
