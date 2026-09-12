package com.kafka.microservice_notification_consumer.service;

import java.util.Arrays;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.kafka.microservice_notification_consumer.dto.PersonDTO;

import tools.jackson.databind.ObjectMapper;

@Service
public class KafkaMessageConsumer {

	private final EmailService emailService;
	private final ObjectMapper objectMapper;

	protected KafkaMessageConsumer(EmailService emailService, ObjectMapper objectMapper) {
		this.emailService = emailService;
		this.objectMapper = objectMapper;
	}

	@KafkaListener(topics = "notification-topic", groupId = "notification-group")
	public void consume(String message, Acknowledgment acknowledgment) {
		try {
			PersonDTO dto = objectMapper.readValue(message, PersonDTO.class);
			System.out.println("Received message: " + dto);
			emailService.sendEmail(Arrays.asList(dto.getEmail()), null, null, "Test Email", "Test Email");
			acknowledgment.acknowledge();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
