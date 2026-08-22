package com.kafka.microservice_producer.controller;

import org.springframework.stereotype.Service;

import com.kafka.microservice_producer.services.UserService;
import com.kafka.microservice_producer.services.generic.GenericService;

@Service
public abstract class AbstractResource extends GenericService {

	protected AbstractResource(UserService userService) {
		super(userService);
	}

}
