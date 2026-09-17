package com.kafka.microservice_notification_consumer.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity(name = "processed_event")
public class ProcessedEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9204616091287106514L;

	@Id
	String eventId;

	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime eventDate;

	public ProcessedEvent(String eventId) {
		this.eventId = eventId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public LocalDateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDateTime eventDate) {
		this.eventDate = eventDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
