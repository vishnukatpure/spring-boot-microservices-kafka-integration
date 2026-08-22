package com.kafka.microservice_producer.model;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "user_authorities")
@Entity
@EnableJpaAuditing
public class Authorities extends EntityBase implements GrantedAuthority {

	private static final long serialVersionUID = -3685793083409982451L;

	@ManyToOne
	private User username;

	@ManyToOne
	private Roles role;

	public User getUsername() {
		return username;
	}

	public void setUsername(User username) {
		this.username = username;
	}

	@Override
	public String getAuthority() {
		return role.getRole();
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

}
