package com.kafka.microservice_producer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kafka.microservice_producer.modal.Authorities;
import com.kafka.microservice_producer.repository.AuthoritiesRepository;

@Service
public class AuthoritiesService {

	final AuthoritiesRepository<Authorities> authoritiesRepository;

	AuthoritiesService(
			AuthoritiesRepository<Authorities> authoritiesRepository) {
		this.authoritiesRepository = authoritiesRepository;
	}

	@Transactional
	public List<Authorities> getAllAuthoritiess() {
		return (List<Authorities>) authoritiesRepository.findAll();
	}

	@Transactional
	public Optional<Authorities> getById(Long id) {
		return authoritiesRepository.findById(id);
	}

	@Transactional
	public void deleteAuthorities(Long AuthoritiesId) {
		authoritiesRepository.deleteById(AuthoritiesId);
	}

	@Transactional
	public boolean addAuthorities(Authorities Authorities) {
		return authoritiesRepository.save(Authorities) != null;
	}

	@Transactional
	public boolean updateAuthorities(Authorities Authorities) {
		return authoritiesRepository.save(Authorities) != null;
	}
}