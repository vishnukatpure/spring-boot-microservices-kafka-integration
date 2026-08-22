package com.kafka.microservice_producer.controller.login;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.kafka.microservice_producer.controller.AbstractResource;
import com.kafka.microservice_producer.services.UserService;
import com.kafka.microservice_producer.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController extends AbstractResource {

	private final JwtUtils jwtUtils;

	protected LoginController(UserService userService, JwtUtils jwtUtils) {
		super(userService);
		this.jwtUtils = jwtUtils;
	}

	@GetMapping(value = { "/" })
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		return model;
	}

	@GetMapping(value = { "/home" })
	@PreAuthorize("@authorizationService.hasRole('ROLE_ADMIN')")
	public ModelAndView homePage() {
		JsonObject token = jwtUtils.generateJwtToken(getAuthentication());
		ModelAndView model = new ModelAndView();
		model.addObject("token", token);
		model.setViewName("home");
		return model;
	}

	@ResponseBody
	@GetMapping(value = { "/get-token" })
	public String getToken() {
		JsonObject obj = jwtUtils.generateJwtToken(getAuthentication());
		return obj.toString();
	}

	@GetMapping(value = "/login")
	public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
		request.getSession().invalidate();

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid Credentials provided.");
		}

		if (logout != null) {
			model.addObject("message", "Logged out successfully.");
		}

		model.setViewName("index");
		return model;
	}

	@GetMapping(value = "/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout=true";
	}

}
