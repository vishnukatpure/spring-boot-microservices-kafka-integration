package com.kafka.microservice_producer.service.generic;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kafka.microservice_producer.dto.ResponseDTO;
import com.kafka.microservice_producer.enums.StatusEnum;
import com.kafka.microservice_producer.modal.User;
import com.kafka.microservice_producer.service.UserService;

@Service
public abstract class GenericService<K> {

	private static final ModelMapper mapper = new ModelMapper();

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
				user = userService.findByUserName(userDetails.getUsername()).get();
		}
		return user;
	}

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public abstract K create(K entity);

	public abstract K update(K entity);

	public abstract void delete(K entity);

	public abstract K findById(Long Id);

	public abstract List<K> getAll();

}