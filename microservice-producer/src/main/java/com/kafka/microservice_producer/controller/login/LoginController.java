package com.kafka.microservice_producer.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.kafka.microservice_producer.controller.AbstractResource;
import com.kafka.microservice_producer.services.UserService;
import com.kafka.microservice_producer.utils.JwtUtils;

@Controller
public class LoginController extends AbstractResource {

	private final JwtUtils jwtUtils;

	protected LoginController(UserService userService, JwtUtils jwtUtils) {
		super(userService);
		this.jwtUtils = jwtUtils;
	}

	@ResponseBody
	@GetMapping(value = { "/get-token" })
	public String getToken() {
		JsonObject obj = jwtUtils.generateJwtToken(getAuthentication());
		return obj.toString();
	}

}
