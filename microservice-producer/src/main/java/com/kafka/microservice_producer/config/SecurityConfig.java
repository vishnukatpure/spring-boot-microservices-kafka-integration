package com.kafka.microservice_producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth

				.requestMatchers("/non-secured/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**",
						"/actuator/**")
				.permitAll()

				.anyRequest().hasAnyRole("USER", "ADMIN")).csrf(csrf -> csrf.disable())
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}
}
