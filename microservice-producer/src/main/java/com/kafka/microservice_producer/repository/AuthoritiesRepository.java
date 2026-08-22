package com.kafka.microservice_producer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kafka.microservice_producer.model.Authorities;
import com.kafka.microservice_producer.model.User;

public interface AuthoritiesRepository extends CrudRepository<Authorities, Long> {

	List<Authorities> findByUsername(User user);

}
