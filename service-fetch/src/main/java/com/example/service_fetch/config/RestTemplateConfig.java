/*******************************************************************************
 * Class        :className
 * Created date :2024/08/19
 * Lasted date  :2024/08/19
 * Author       :TamTH1
 * Change log   :2024/08/19 01-00 TamTH1 create a new
******************************************************************************/
package com.example.service_fetch.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Configuration
public class RestTemplateConfig {

    // Create a bean for restTemplate to call services
    @Bean
    @LoadBalanced() // Load balance between service instances running at different ports.
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.setConnectTimeout(Duration.ofMillis(2000))
                .setReadTimeout(Duration.ofMillis(2000))
                .build();
    }

}
