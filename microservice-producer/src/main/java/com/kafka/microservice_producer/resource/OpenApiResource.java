package com.kafka.microservice_producer.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.microservice_producer.service.KafkaMessageProducerService;

@RestController
@RequestMapping("/open/")
public class OpenApiResource {

	KafkaMessageProducerService<String, Object> kafkaMessageProducerService;

	public OpenApiResource(KafkaMessageProducerService<String, Object> kafkaMessageProducerService) {
		this.kafkaMessageProducerService = kafkaMessageProducerService;
	}

	@GetMapping("invoke-kafka")
	public String getMethodName() {

		kafkaMessageProducerService.sendMessage("employee-topic", "1", "Test-message");
		return new String("ABCD");
	}

}
