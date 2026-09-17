package com.kafka.microservice_notification_consumer.listner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
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
	public void consume(String message /* , Acknowledgment acknowledgment */) {
		notificationService.serveNotification(message);
		// acknowledgment.acknowledge();
	}

	@DltHandler
	public void handleDlt(String message) {
		log.info("Message moved to DLT: ", message);
	}
}
