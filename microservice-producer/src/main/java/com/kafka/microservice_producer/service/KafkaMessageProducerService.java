package com.kafka.microservice_producer.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProducerService {

	private final KafkaTemplate<String, String> kafkaTemplate;

	public KafkaMessageProducerService(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(String topic, String message) {
		kafkaTemplate.send(topic, message);
	}
}
