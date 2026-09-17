package com.kafka.microservice_notification_consumer.dto;

public record NotificationEvent(String eventType, String eventId, String email, String firstName, String lastName,
		String subject, String content) {
}
