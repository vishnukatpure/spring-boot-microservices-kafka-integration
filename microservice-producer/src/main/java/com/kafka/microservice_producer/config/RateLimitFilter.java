package com.kafka.microservice_producer.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.microservice_producer.dto.ResponseDTO;
import com.kafka.microservice_producer.enums.StatusEnum;
import com.kafka.microservice_producer.utils.RateLimitService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

	private RateLimitService rateLimitService;

	RateLimitFilter(RateLimitService rateLimitService) {
		this.rateLimitService = rateLimitService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String client = request.getRemoteAddr();
		String path = request.getRequestURI();
		String method = request.getMethod();
		if (!rateLimitService.isAllowed(client, path, method)) {
			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);

			ResponseDTO responseDTO = new ResponseDTO().status(StatusEnum.TOO_MANY_REQUESTS)
					.message("Too many requests");

			ObjectMapper objectMapper = new ObjectMapper();

			response.getWriter().write(objectMapper.writeValueAsString(responseDTO));

			return;
		}

		filterChain.doFilter(request, response);
	}

}
