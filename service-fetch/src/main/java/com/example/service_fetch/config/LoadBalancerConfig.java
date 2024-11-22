/*******************************************************************************
 * Class        :className
 * Created date :2024/08/20
 * Lasted date  :2024/08/20
 * Author       :TamTH1
 * Change log   :2024/08/20 01-00 TamTH1 create a new
******************************************************************************/
package com.example.service_fetch.config;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Configuration
@LoadBalancerClient(name = "randomLoadBalancer", configuration = RandomLoadBalancerConfig.class)
public class LoadBalancerConfig {
    
}
