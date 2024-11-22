package com.example.service_data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDataApplication.class, args);
	}

}
