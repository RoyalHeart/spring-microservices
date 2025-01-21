package com.example.security.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.security.filter.RequestResponseLogFilter;

@Configuration
public class WebConfig {

    @Bean
    FilterRegistrationBean<RequestResponseLogFilter> loggingFilter(
            RequestResponseLogFilter requestResponseLoggingFilter) {
        FilterRegistrationBean<RequestResponseLogFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(requestResponseLoggingFilter);
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
}
