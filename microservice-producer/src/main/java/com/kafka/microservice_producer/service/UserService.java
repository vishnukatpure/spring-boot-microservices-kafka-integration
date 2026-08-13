package com.kafka.microservice_producer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kafka.microservice_producer.modal.User;
import com.kafka.microservice_producer.repository.UserRepository;

@Service
public class UserService {

	final UserRepository<User> userRepository;

	UserService(UserRepository<User> userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@Transactional
	public List<User> findByName(String name) {
		return userRepository.findByFirstName(name);
	}

	@Transactional
	public Optional<User> getById(String id) {
		return userRepository.findById(id);
	}

	@Transactional
	public void deleteUser(String UserId) {
		userRepository.deleteById(UserId);
	}

	@Transactional
	public boolean addUser(User User) {
		return userRepository.save(User) != null;
	}

	@Transactional
	public boolean updateUser(User User) {
		return userRepository.save(User) != null;
	}

}