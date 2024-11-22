/*******************************************************************************
 * Class        :className
 * Created date :2024/08/20
 * Lasted date  :2024/08/20
 * Author       :TamTH1
 * Change log   :2024/08/20 01-00 TamTH1 create a new
******************************************************************************/
package com.example.service_fetch.config;

import org.springframework.context.annotation.Configuration;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Configuration
public class RandomLoadBalancerConfig {
    // @Bean
    // public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(Environment environment,
    //         LoadBalancerClientFactory loadBalancerClientFactory) {
    //     String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
    //     return new RandomLoadBalancer(
    //             loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    // }
    // @Bean
	// public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
	// 		ConfigurableApplicationContext context) {
	// 	return ServiceInstanceListSupplier.builder()
	// 				.withDiscoveryClient()
	// 				.withWeighted(instance -> ThreadLocalRandom.current().nextInt(1, 101))
	// 				.withCaching()
	// 				.build(context);
	// }
}
