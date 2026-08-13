package com.kafka.microservice_producer.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.microservice_producer.service.KafkaMessageProducerService;

@RestController
public class EmployeeResource {

	final KafkaMessageProducerService kafkaMessageProducerService;

	EmployeeResource(KafkaMessageProducerService kafkaMessageProducerService) {
		this.kafkaMessageProducerService = kafkaMessageProducerService;
	}

	@GetMapping("/open/invoke-kafka")
	public String getMethodName() {

		kafkaMessageProducerService.sendMessage("employee-topic", "Test-message");
		return new String("ABCD");
	}

}
