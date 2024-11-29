/*******************************************************************************
 * Class        :className
 * Created date :2024/10/01
 * Lasted date  :2024/10/01
 * Author       :TamTH1
 * Change log   :2024/10/01 01-00 TamTH1 create a new
******************************************************************************/
package com.example.service_fetch.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
    public RestClient restClient(@LoadBalanced RestClient.Builder builder) {
        return builder.build();
    }

    @LoadBalanced
	@Bean
	RestClient.Builder restClientBuilder() {
        return RestClient.builder().requestFactory(getClientHttpRequestFactory());
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(100);
        clientHttpRequestFactory.setConnectionRequestTimeout(100);
        return clientHttpRequestFactory;
    }
}
