package com.kafka.microservice_notification_consumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageConsumer {

	@KafkaListener(topics = "employee-topic", groupId = "employee-group")
	public void consume(String message) {

		System.out.println("Received message: " + message);
	}
}
