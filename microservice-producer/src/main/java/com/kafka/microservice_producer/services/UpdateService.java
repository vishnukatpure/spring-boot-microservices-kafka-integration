package com.kafka.microservice_producer.services;

import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kafka.microservice_producer.dto.PersonDTO;
import com.kafka.microservice_producer.model.Person;
import com.kafka.microservice_producer.repository.PersonRepository;

import jakarta.persistence.OptimisticLockException;

@Service
public class UpdateService {

	private final PersonRepository personRepository;

	UpdateService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Retryable(includes = { OptimisticLockException.class,
			ObjectOptimisticLockingFailureException.class }, maxRetries = 2, delay = 100, multiplier = 2, maxDelay = 1000)
	@Transactional
	public Person updatePersonWithRetry(PersonDTO personDTO) throws AccountNotFoundException {
		Optional<Person> p = personRepository.findById(personDTO.getId());
		if (p.isEmpty())
			throw new AccountNotFoundException();

		Person person = p.get();
		person.setAge(personDTO.getAge());
		person.setFirstName(personDTO.getFirstName());
		person.setLastName(personDTO.getLastName());

		return personRepository.save(person);
	}
}
