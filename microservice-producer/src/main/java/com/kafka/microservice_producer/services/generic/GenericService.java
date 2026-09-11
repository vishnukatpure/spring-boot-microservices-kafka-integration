package com.kafka.microservice_producer.services.generic;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kafka.microservice_producer.dto.ResponseDTO;
import com.kafka.microservice_producer.enums.StatusEnum;
import com.kafka.microservice_producer.model.User;
import com.kafka.microservice_producer.services.UserService;

import tools.jackson.databind.ObjectMapper;

@Service
public abstract class GenericService {

	private final ModelMapper mapper = new ModelMapper();

	private final ObjectMapper objectMapper = new ObjectMapper();

	final UserService userService;

	protected GenericService(UserService userService) {
		this.userService = userService;
	}

	public ResponseDTO bindResponse(Object dto) {
		return new ResponseDTO().message("Success").object(dto).status(StatusEnum.SUCCESS);
	}

	public ModelMapper getMapper() {
		return mapper;
	}

	public User getLoggedInUser() {
		UserDetails userDetails = (UserDetails) getAuthentication().getPrincipal();
		User user = null;
		if (userDetails != null) {
			if (userDetails instanceof User)
				user = (User) userDetails;
			else
				user = userService.findByUsername(userDetails.getUsername());
		}
		return user;
	}

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public String objectToJsonString(Object object) {

		return getObjectMapper().writeValueAsString(object);
	}

	public UserService getUserService() {
		return userService;
	}

}