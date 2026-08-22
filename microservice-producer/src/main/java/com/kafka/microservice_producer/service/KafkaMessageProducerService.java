package com.kafka.microservice_producer.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProducerService<K, V> {

	private final KafkaTemplate<K, V> kafkaTemplate;

	public KafkaMessageProducerService(KafkaTemplate<K, V> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(String topic, K key, V message) {
		kafkaTemplate.send(topic, key, message);
	}
}
