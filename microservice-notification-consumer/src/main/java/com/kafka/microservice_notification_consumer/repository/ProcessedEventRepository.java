package com.kafka.microservice_notification_consumer.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kafka.microservice_notification_consumer.model.ProcessedEvent;

public interface ProcessedEventRepository extends CrudRepository<ProcessedEvent, String> {

	@Modifying
	@Query(value = """
			INSERT INTO processed_event (event_id)
			VALUES (:eventKey)
			ON DUPLICATE KEY UPDATE event_id = event_id
			""", nativeQuery = true)
	int markIfNew(String eventKey);

}
