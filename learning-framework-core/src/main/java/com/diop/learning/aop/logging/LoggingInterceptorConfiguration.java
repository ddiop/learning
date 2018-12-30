package com.diop.learning.aop.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Register the aspect to log method calls
 *
 * @author djibi
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class LoggingInterceptorConfiguration {

	@Bean
	public LoggingDomainInterceptor loggingDomainInterceptor() {
		return new LoggingDomainInterceptor();
	}

	@Bean
	public LoggingInfrastructureInterceptor loggingInfrastructureInterceptor() {
		return new LoggingInfrastructureInterceptor();
	}

	@Bean
	public LoggingExpositionInterceptor loggingExpositionInterceptor() {
		return new LoggingExpositionInterceptor();
	}

	@Bean
	public LoggingApplicationInterceptor loggingApplicationInterceptor() {
		return new LoggingApplicationInterceptor();
	}
}
