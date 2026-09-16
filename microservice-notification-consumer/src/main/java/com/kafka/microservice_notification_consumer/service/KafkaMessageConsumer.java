package com.kafka.microservice_notification_consumer.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.kafka.microservice_notification_consumer.dto.PersonDTO;

import tools.jackson.databind.ObjectMapper;

@Service
public class KafkaMessageConsumer {

	private final EmailService emailService;
	private final ObjectMapper objectMapper;
	private final Logger log = LoggerFactory.getLogger(KafkaMessageConsumer.class);

	protected KafkaMessageConsumer(EmailService emailService, ObjectMapper objectMapper) {
		this.emailService = emailService;
		this.objectMapper = objectMapper;
	}

	@RetryableTopic(attempts = "4", backOff = @BackOff(delay = 2000, multiplier = 2.0), include = {
			RuntimeException.class }, dltTopicSuffix = ".DLT")
	@KafkaListener(topics = "notification-topic", groupId = "notification-group")
	public void consume(String message, Acknowledgment acknowledgment) {
		try {
			PersonDTO dto = objectMapper.readValue(message, PersonDTO.class);
			log.info("Received notification message: {}", dto);
			emailService.sendEmail(Arrays.asList(dto.getEmail()), null, null, "Test Email", "Test Email");
			// acknowledgment.acknowledge();
		} catch (Exception e) {
			log.error("Exception occured:", e);
			throw e;
		}
	}

	@DltHandler
	public void handleDlt(String message) {
		System.out.println("Message moved to DLT: " + message);
	}
}
