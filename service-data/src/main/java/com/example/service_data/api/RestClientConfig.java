/*******************************************************************************
 * Class        :className
 * Created date :2024/10/01
 * Lasted date  :2024/10/01
 * Author       :TamTH1
 * Change log   :2024/10/01 01-00 TamTH1 create a new
******************************************************************************/
package com.example.service_data.api;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Configuration
public class RestClientConfig {
    @Bean
    RestClient restClient(@LoadBalanced RestClient.Builder builder) {
        return builder.build();
    }

    @LoadBalanced
	@Bean
	RestClient.Builder restClientBuilder() {
		return RestClient.builder();
	}
}
