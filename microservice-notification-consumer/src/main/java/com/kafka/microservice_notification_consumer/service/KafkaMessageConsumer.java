package com.kafka.microservice_notification_consumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageConsumer {

	@KafkaListener(topics = "employee-topic", groupId = "employee-group")
	public void consume(String message, Acknowledgment acknowledgment) {

		try {
			System.out.println("Received message: " + message);
			acknowledgment.acknowledge();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
