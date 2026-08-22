package com.kafka.microservice_producer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kafka.microservice_producer.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	public List<User> findByUsername(String username);
}
