package com.kafka.microservice_producer.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RequestInterceptorConfig implements WebMvcConfigurer {

	private final RequestInterceptor requestInterceptor;

	RequestInterceptorConfig(RequestInterceptor requestInterceptor) {
		this.requestInterceptor = requestInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor);
	}
}
