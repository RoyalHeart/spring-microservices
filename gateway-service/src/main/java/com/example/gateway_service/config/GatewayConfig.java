/*******************************************************************************
 * Class        :className
 * Created date :2024/08/22
 * Lasted date  :2024/08/22
 * Author       :TamTH1
 * Change log   :2024/08/22 01-00 TamTH1 create a new
******************************************************************************/
package com.example.gateway_service.config;

import java.util.ArrayList;
import java.util.List;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {

    @Autowired
    AuthenticationFilter authFilter;

    @Autowired
    RateLimitFilter rateLimitFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        List<GatewayFilter> filters = new ArrayList<>();
        filters.add(authFilter);
        filters.add(rateLimitFilter);
        return builder.routes()
                .route("service-auth", r -> r.path("/api/auth/**")
                        .filters(f -> f.filters(filters))
                        .uri("lb://SERVICE-AUTH"))
                .route("service-data", r -> r.path("/api/data/**")
                        .filters(f -> f.filters(filters))
                        .uri("lb://SERVICE-DATA"))
                .route("service-fetch", r -> r.path("/api/fetch/**")
                        .filters(f -> f.filters(filters))
                        .uri("lb://service-fetch"))
                .build();
    }

}
