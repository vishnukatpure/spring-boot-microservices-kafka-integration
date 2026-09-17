package com.kafka.microservice_producer.dto;

public record NotificationEvent(String eventType, String eventId, String email, String firstName, String lastName,
		String subject, String content) {
}
