package com.example.gateway_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties("custom")
@Configuration
public class CustomProperties {
    private final JWT jwt = new JWT();

    @Data
    public static class JWT {
        private int validTime = 0;
        private String base64Secret;
    }
}
