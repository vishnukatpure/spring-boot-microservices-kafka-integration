package com.kafka.microservice_producer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kafka.microservice_producer.modal.Authorities;
import com.kafka.microservice_producer.modal.User;

public interface AuthoritiesRepository<P>
		extends
			CrudRepository<Authorities, Long> {

	List<Authorities> findByUsername(User user);
}