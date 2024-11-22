package com.example.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties("jwt")
@Configuration
public class JWT {
    private int accessValidTime = 0;
    private int refreshValidTime = 0;
    private String base64Secret;
}
