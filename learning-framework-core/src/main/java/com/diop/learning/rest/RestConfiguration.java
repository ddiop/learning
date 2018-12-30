package com.diop.learning.rest;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {

	@Bean(name="restTemplate")
	@Primary
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	@Bean(name="ribbonRestTemplate")
	@LoadBalanced
	public RestTemplate ribbonRestTemplate() {

		return new RestTemplate();
	}
	
}
