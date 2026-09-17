package com.kafka.microservice_notification_consumer.service;

import org.springframework.stereotype.Service;

import com.kafka.microservice_notification_consumer.model.ProcessedEvent;
import com.kafka.microservice_notification_consumer.repository.ProcessedEventRepository;

@Service
public class ProcessedEventService {

	private ProcessedEventRepository processedEventRepository;

	public ProcessedEventService(ProcessedEventRepository processedEventRepository) {
		this.processedEventRepository = processedEventRepository;
	}

	public ProcessedEvent save(ProcessedEvent processedEvent) {
		return processedEventRepository.save(processedEvent);
	}

	public boolean markIfNew(String eventKey) {
		int rows = processedEventRepository.markIfNew(eventKey);
		return rows == 1;
	}

}
