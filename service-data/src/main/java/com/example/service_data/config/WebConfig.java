/*******************************************************************************
 * Class        :className
 * Created date :2024/10/04
 * Lasted date  :2024/10/04
 * Author       :TamTH1
 * Change log   :2024/10/04 01-00 TamTH1 create a new
******************************************************************************/
package com.example.service_data.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<RequestResponseLog> loggingFilter(RequestResponseLog requestResponseLoggingFilter) {
        FilterRegistrationBean<RequestResponseLog> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(requestResponseLoggingFilter);
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
}
