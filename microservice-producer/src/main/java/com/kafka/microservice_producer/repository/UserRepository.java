package com.kafka.microservice_producer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kafka.microservice_producer.modal.User;

public interface UserRepository<P> extends CrudRepository<User, String> {
	List<User> findByFirstName(String firstName);

	Optional<User> findByUsername(String username);
}