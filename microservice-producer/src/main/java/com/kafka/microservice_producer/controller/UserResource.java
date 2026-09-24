package com.kafka.microservice_producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.microservice_producer.dto.ResponseDTO;
import com.kafka.microservice_producer.dto.UserDTO;
import com.kafka.microservice_producer.enums.StatusEnum;
import com.kafka.microservice_producer.model.Roles;
import com.kafka.microservice_producer.model.User;
import com.kafka.microservice_producer.services.RolesService;
import com.kafka.microservice_producer.services.UserService;
import com.kafka.microservice_producer.services.generic.GenericService;

@RestController
public class UserResource extends GenericService {

	private final UserService userService;

	final RolesService rolesService;

	UserResource(UserService userService, RolesService rolesService) {
		super(userService);
		this.userService = userService;
		this.rolesService = rolesService;
	}

	@GetMapping(value = { "/user-info/{usersName}" })
	public ResponseDTO getUser(@PathVariable("usersName") String userName) {
		ResponseDTO responseDTO = new ResponseDTO().status(StatusEnum.SUCCESS);
		User user = userService.findByUsername(userName);
		if (user != null) {
			responseDTO.setObject(getMapper().map(user, UserDTO.class));
		} else {
			responseDTO.setStatus(StatusEnum.NOT_FOUND);
			responseDTO.setMessage("User info not found");
		}
		return responseDTO;
	}

	@PostMapping(value = { "/create-user" })
	public ResponseDTO createUser(@RequestBody UserDTO userDTO) {

		ResponseDTO responseDTO = new ResponseDTO().status(StatusEnum.SUCCESS);
		Roles role = rolesService.findByRole("ROLE_" + userDTO.getRole());
		if (role == null) {
			responseDTO.setMessage("Role not found");
			responseDTO.setStatus(StatusEnum.NOT_FOUND);
			return responseDTO;
		}

		User user = userService.addUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
				userDTO.getSex(), userDTO.getPassword(), role);
		responseDTO.setObject(getMapper().map(user, UserDTO.class));
		responseDTO.setMessage("User Created");

		return responseDTO;
	}

}
