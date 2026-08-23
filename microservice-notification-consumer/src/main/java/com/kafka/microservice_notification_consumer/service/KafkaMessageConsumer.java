package com.kafka.microservice_notification_consumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.kafka.microservice_producer.dto.PersonDTO;

@Service
public class KafkaMessageConsumer {

	@KafkaListener(topics = "person-topic", groupId = "person-group")
	public void consume(PersonDTO message, Acknowledgment acknowledgment) {
		try {
			System.out.println("Received message: " + message);
			acknowledgment.acknowledge();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
