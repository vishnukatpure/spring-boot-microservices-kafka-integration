package com.kafka.microservice_producer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kafka.microservice_producer.model.Roles;

public interface RolesRepository extends CrudRepository<Roles, Long> {

	List<Roles> findByRole(String role);
}
