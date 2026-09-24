package com.kafka.microservice_producer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccessLogFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, java.io.IOException {

		final Logger log = LoggerFactory.getLogger("ACCESS_LOG");

		long startTime = System.currentTimeMillis();

		try {
			System.out.println("ACCESS FILTER CALLED");
			filterChain.doFilter(request, response);
		} finally {

			long duration = System.currentTimeMillis() - startTime;

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			log.info("METHOD={} URI={} STATUS={} USER={} AUTHORITIES={} TIME={}ms", request.getMethod(),
					request.getRequestURI(), response.getStatus(), auth != null ? auth.getName() : "anonymous",
					auth != null ? auth.getAuthorities() : "[]", duration);
		}
	}
}
