package com.example.security.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.security.service.impl.RequestResponseLog;

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
