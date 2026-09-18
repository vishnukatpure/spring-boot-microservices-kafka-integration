package com.kafka.microservice_notification_consumer.util;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;

import com.kafka.microservice_notification_consumer.dto.NotificationEvent;
import com.kafka.microservice_notification_consumer.service.ProcessedEventService;

import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;

@Service
public class NotificationService {

	private final EmailService emailService;
	private final ObjectMapper objectMapper;
	private final ProcessedEventService processedEventService;

	private final Logger log = LoggerFactory.getLogger(NotificationService.class);

	NotificationService(EmailService emailService, ObjectMapper objectMapper,
			ProcessedEventService processedEventService) {
		this.emailService = emailService;
		this.objectMapper = objectMapper;
		this.processedEventService = processedEventService;
	}

	@RetryableTopic(attempts = "4", backOff = @BackOff(delay = 2000, multiplier = 2.0), include = {
			RuntimeException.class }, dltTopicSuffix = ".DLT")
	@Transactional
	public void serveNotification(String message) {
		NotificationEvent event = objectMapper.readValue(message, NotificationEvent.class);
		log.info("Received notification message: {}", event);
		String eventKey = event.eventType() + ":" + event.eventId();

		boolean newEvent = processedEventService.markIfNew(eventKey);

		if (!newEvent) {
			log.info("Duplicate event ignored: {}", eventKey);
			return;
		}

		emailService.sendEmail(Arrays.asList(event.email()), null, null, "Test Email", "Test Email");

	}
}
