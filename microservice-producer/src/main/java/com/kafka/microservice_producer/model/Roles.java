package com.kafka.microservice_producer.model;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "roles")
@Entity
@EnableJpaAuditing
public class Roles extends EntityBase {

	private static final long serialVersionUID = 2213248833866696724L;
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
