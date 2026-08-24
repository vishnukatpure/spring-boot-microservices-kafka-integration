package com.kafka.microservice_producer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kafka.microservice_producer.custom.exception.DuplicateRecordException;
import com.kafka.microservice_producer.model.Person;
import com.kafka.microservice_producer.repository.PersonRepository;
import com.kafka.microservice_producer.services.generic.GenericService;

@Service
public class PersonService extends GenericService {

	PersonRepository personRepository;

	UserService userService;

	PersonService(PersonRepository personRepository, UserService userService) {
		super(userService);
		this.personRepository = personRepository;
	}

	@Transactional
	public Iterable<Person> getAllPersons() {

		return personRepository.findAll();
	}

	@Transactional
	public List<Person> findByName(String name) {
		return personRepository.findByFirstName(name);
	}

	@Transactional
	@Cacheable(value = "person", key = "#id")
	public Optional<Person> getById(Long id) {
		return personRepository.findById(id);
	}

	@Transactional
	@CacheEvict(value = "person", key = "#id")
	public void deletePerson(Long id) {
		personRepository.deleteById(id);
	}

	@Transactional

	public Person addPerson(Person person) {
		Person personOld = personRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
		if (personOld != null)
			throw new DuplicateRecordException("Person with given Name already exists");

		return personRepository.save(person);
	}

	@Transactional
	@CachePut(value = "person", key = "#person.id")
	public Person updatePerson(Person person) {
		return personRepository.save(person);
	}

}
