package com.kafka.microservice_producer.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kafka.microservice_producer.model.Authorities;
import com.kafka.microservice_producer.model.Roles;
import com.kafka.microservice_producer.model.User;
import com.kafka.microservice_producer.repository.UserRepository;

@Service
public class UserService {

	final UserRepository userRepository;
	final PasswordEncoder passwordEncoder;

	final AuthoritiesService authoritiesService;

	UserService(UserRepository userRepository, AuthoritiesService authoritiesService) {
		this.userRepository = userRepository;
		this.authoritiesService = authoritiesService;
		passwordEncoder = new BCryptPasswordEncoder();
	}

	@Transactional
	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@Transactional
	public User getById(Long id) {
		return userRepository.findById(id).get();
	}

	@Transactional
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}

	/**
	 * @CachePut update the cache entries whenever we alter them
	 * @param user
	 * @return
	 */
	@Transactional
	@CachePut(value = "users", key = "#result.id")
	public boolean updateUser(User user) {
		return userRepository.save(user) != null;
	}

	/**
	 * @Cacheable enable caching behavior for a method
	 * @param username
	 * @return
	 */

	public User findByUsername(String username) {
		List<User> users = userRepository.findByUsername(username);
		if (users.isEmpty()) {
			throw new UsernameNotFoundException("User Not Found");
		}
		return users.get(0);
	}

	@Transactional
	public User addUser(String firstName, String lastName, String email, String sex, String password, Roles role) {

		User user = new User();
		user.setEmail(email);
		user.setUsername(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setSex(sex);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(password));

		user = userRepository.save(user);
		Authorities authorities = new Authorities();
		authorities.setRole(role);
		authorities.setUsername(user);
		authoritiesService.addAuthorities(authorities);

		return user;
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Cacheable(cacheNames = "users")
	public UserDetails loadUserByUsernameWithAuthorities(String username) {
		User user = findByUsername(username);
		user.setAuthorities(findGrantedAuthoritiesForUser(user));
		return user;
	}

	private List<GrantedAuthority> findGrantedAuthoritiesForUser(User user) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		List<Authorities> authorities = authoritiesService.findByUserName(user);
		authorities.forEach(e -> grantedAuthorities.add(e));
		return grantedAuthorities;
	}

}
