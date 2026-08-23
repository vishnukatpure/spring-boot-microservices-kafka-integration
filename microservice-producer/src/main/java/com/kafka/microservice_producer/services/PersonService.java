package com.kafka.microservice_producer.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kafka.microservice_producer.custom.exception.BadRequestException;
import com.kafka.microservice_producer.custom.exception.DuplicateRecordException;
import com.kafka.microservice_producer.custom.exception.FormValidationException;
import com.kafka.microservice_producer.dto.PersonDTO;
import com.kafka.microservice_producer.dto.ResponseDTO;
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
	public ResponseDTO getAllPersons() {
		List<PersonDTO> dto = new ArrayList<>();
		personRepository.findAll().forEach(ob -> dto.add(getMapper().map(ob, PersonDTO.class)));

		return bindResponse(dto);
	}

	@Transactional
	public ResponseDTO findByName(String name) {
		List<PersonDTO> dto = new ArrayList<>();
		personRepository.findByFirstName(name).forEach(ob -> dto.add(getMapper().map(ob, PersonDTO.class)));
		return bindResponse(dto);
	}

	@Transactional
	public ResponseDTO getById(Long id) {
		return bindResponse(getMapper().map(personRepository.findById(id).get(), PersonDTO.class));
	}

	@Transactional
	public void deletePerson(Long personId) {
		personRepository.deleteById(personId);
	}

	@Transactional
	public ResponseDTO addPerson(PersonDTO personDto) {
		Person person = personRepository.findByFirstNameAndLastName(personDto.getFirstName(), personDto.getLastName());
		if (person != null)
			throw new DuplicateRecordException("Person with given Name already exists");
		person = getMapper().map(personDto, Person.class);
		person.validate();

		return bindResponse(getMapper().map(personRepository.save(person), PersonDTO.class));
	}

	@Transactional
	public ResponseDTO updatePerson(PersonDTO personDto) {
		Person person = getMapper().map(personDto, Person.class);
		return bindResponse(getMapper().map(personRepository.save(person), PersonDTO.class));
	}

	public ResponseDTO aopTesting(String type) throws Exception {
		if (type.equals("1")) {
			throw new BadRequestException("in BadRequestException exception");
		}
		if (type.equals("2")) {
			throw new FormValidationException("Form Validation error");
		}

		throw new NullPointerException("in BadRequestException");
	}
}
