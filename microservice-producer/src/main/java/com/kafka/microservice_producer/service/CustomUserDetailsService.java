package com.kafka.microservice_producer.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kafka.microservice_producer.modal.Authorities;
import com.kafka.microservice_producer.modal.User;
import com.kafka.microservice_producer.repository.AuthoritiesRepository;
import com.kafka.microservice_producer.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository<User> userRepository;

	private final AuthoritiesRepository<Authorities> authoritiesRepository;

	public CustomUserDetailsService(UserRepository<User> userRepository,
			AuthoritiesRepository<Authorities> authoritiesRepository) {
		this.userRepository = userRepository;
		this.authoritiesRepository = authoritiesRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
		List<Authorities> authorities = authoritiesRepository.findByUser(user);
		user.setAuthorities(authorities);

		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
				.password(user.getPassword())
				.authorities(user.getAuthorities().stream().map(Authorities::getAuthority).toArray(String[]::new))
				.disabled(!user.isEnabled()).build();
	}
}