package com.kafka.microservice_producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.resilience.annotation.EnableResilientMethods;

@SpringBootApplication
@EnableCaching
@EnableResilientMethods
public class MicroserviceProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceProducerApplication.class, args);
	}

}
