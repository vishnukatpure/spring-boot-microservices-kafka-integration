package com.kafka.microservice_notification_consumer.listner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.kafka.microservice_notification_consumer.util.NotificationService;

@Service
public class KafkaMessageConsumerListener {

	NotificationService notificationService;
	private final Logger log = LoggerFactory.getLogger(KafkaMessageConsumerListener.class);

	protected KafkaMessageConsumerListener(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@KafkaListener(topics = "notification-topic", groupId = "notification-group")
	@RetryableTopic(attempts = "4", backOff = @BackOff(delay = 2000, multiplier = 2.0), include = {
			RuntimeException.class }, dltTopicSuffix = ".DLT")
	public void consume(String message,
			@Header("correlationId") String correlationId/* , Acknowledgment acknowledgment */) {
		try {
			MDC.put("correlationId", correlationId);
			notificationService.serveNotification(message);
			// acknowledgment.acknowledge();
		} finally {
			MDC.remove("correlationId");
		}
	}

	@DltHandler
	public void handleDlt(String message) {
		log.error("Message moved to DLT: {}", message);
	}
}
