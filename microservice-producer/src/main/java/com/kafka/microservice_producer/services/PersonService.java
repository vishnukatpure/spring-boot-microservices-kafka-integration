package com.kafka.microservice_producer.services;

import java.io.IOException;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kafka.microservice_producer.custom.exception.DuplicateRecordException;
import com.kafka.microservice_producer.dto.PersonDTO;
import com.kafka.microservice_producer.kafkaservice.KafkaMessageProducerService;
import com.kafka.microservice_producer.model.Person;
import com.kafka.microservice_producer.repository.PersonRepository;
import com.kafka.microservice_producer.services.generic.GenericService;

@Service
public class PersonService extends GenericService {

	PersonRepository personRepository;

	UpdateService updateService;

	UserService userService;

	FileOperationService fileOperationService;

	final KafkaMessageProducerService<Long, Object> kafkaMessageProducerService;

	PersonService(KafkaMessageProducerService<Long, Object> kafkaMessageProducerService,
			PersonRepository personRepository, UserService userService, UpdateService updateService,
			FileOperationService fileOperationService) {
		super(userService);
		this.kafkaMessageProducerService = kafkaMessageProducerService;
		this.personRepository = personRepository;
		this.updateService = updateService;
		this.fileOperationService = fileOperationService;
	}

	@Transactional(rollbackFor = Exception.class)
	public Page<Person> getAllPersons(Pageable pageable) {

		return personRepository.findAll(pageable);
	}

	@Transactional
	public List<Person> findByName(String name) {
		return personRepository.findByFirstName(name);
	}

	@Transactional
	@Cacheable(value = "person", key = "#id")
	public Person getById(Long id) {
		return personRepository.findById(id).get();
	}

	@Transactional
	@CacheEvict(value = "person", key = "#id")
	public void deletePerson(Long id) {
		personRepository.deleteById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Person addPerson(Person person, MultipartFile profilePicture) throws IOException {
		Person personOld = personRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
		if (personOld != null)
			throw new DuplicateRecordException("Person with given Name already exists");

		String key = fileOperationService.uploadFile(profilePicture);
		person.setProfilePicture(key);
		person = personRepository.save(person);
		kafkaMessageProducerService.sendMessage("notification-topic", person.getId(), objectToJsonString(person));
		return person;
	}

	@CachePut(value = "person", key = "#personDTO.id")
	public Person updatePerson(PersonDTO personDTO) throws AccountNotFoundException {
		Person person = updateService.updatePersonWithRetry(personDTO);
		kafkaMessageProducerService.sendMessage("notification-topic", person.getId(), objectToJsonString(person));
		return person;
	}

}
