package com.example.gateway_service.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;

@Data
@ConfigurationProperties("custom")
@ConfigurationPropertiesScan
public class CustomProperties {
    private final Jwt jwt;
    private final RateLimit rateLimit;
}
