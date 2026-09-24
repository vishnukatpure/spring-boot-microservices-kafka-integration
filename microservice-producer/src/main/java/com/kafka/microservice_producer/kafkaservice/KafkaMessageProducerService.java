package com.kafka.microservice_producer.kafkaservice;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProducerService<K, V> {

	private static final Logger log = LoggerFactory.getLogger(KafkaMessageProducerService.class);
	private static final long SEND_TIMEOUT_SECONDS = 10;

	private final KafkaTemplate<K, V> kafkaTemplate;

	public KafkaMessageProducerService(KafkaTemplate<K, V> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(String topic, K key, V message) {
		try {
			kafkaTemplate.send(topic, key, message).get(SEND_TIMEOUT_SECONDS, TimeUnit.SECONDS);
			log.debug("Published message to topic={} key={}", topic, key);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Interrupted while publishing to topic " + topic, e);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to publish message to topic " + topic, e);
		}
	}
}
