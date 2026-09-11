package com.kafka.microservice_notification_consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

@Configuration
public class KafkaConfig<K, V> {

	@Bean
	ConcurrentKafkaListenerContainerFactory<K, V> kafkaListenerContainerFactory(ConsumerFactory<K, V> consumerFactory) {
		var factory = new ConcurrentKafkaListenerContainerFactory<K, V>();
		factory.setConsumerFactory(consumerFactory);
		factory.getContainerProperties().setAckMode(AckMode.MANUAL);
		return factory;
	}
}
