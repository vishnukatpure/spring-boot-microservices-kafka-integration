package com.kafka.microservice_producer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.microservice_producer.dto.PersonDTO;
import com.kafka.microservice_producer.dto.ResponseDTO;
import com.kafka.microservice_producer.kafkaservice.KafkaMessageProducerService;
import com.kafka.microservice_producer.model.Person;
import com.kafka.microservice_producer.services.PersonService;
import com.kafka.microservice_producer.services.UserService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/person")
@Validated
public class PersonResource extends AbstractResource {

	final PersonService personService;
	private final Validator validator;

	PersonResource(PersonService personService, UserService userService,
			KafkaMessageProducerService<Long, Object> kafkaMessageProducerService, Validator validator) {
		super(userService);
		this.personService = personService;
		this.validator = validator;
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseDTO insertPerson(@RequestPart("person") String personJson,
			@RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture) throws IOException {

		PersonDTO personDTO = new ObjectMapper().readValue(personJson, PersonDTO.class);

		validatePerson(personDTO);

		Person person = getMapper().map(personDTO, Person.class);
		person = personService.addPerson(person, profilePicture);
		ResponseDTO responseDTO = bindResponse(getMapper().map(person, PersonDTO.class));
		return responseDTO;
	}

	private void validatePerson(PersonDTO personDTO) {
		Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}

	@PutMapping(name = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseDTO updatePerson(@PathVariable @Min(value = 1, message = "ID must be greater than 0") Long id,
			@RequestPart("person") String personJson,
			@RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture)
			throws AccountNotFoundException, JsonMappingException, JsonProcessingException {

		PersonDTO personDTO = new ObjectMapper().readValue(personJson, PersonDTO.class);
		personDTO.setId(id);

		validatePerson(personDTO);

		Person person = personService.updatePerson(personDTO);
		ResponseDTO responseDTO = bindResponse(getMapper().map(person, PersonDTO.class));
		return responseDTO;
	}

	@DeleteMapping(value = "/{id}")
	public HttpStatus deletePerson(@PathVariable Long id) {
		personService.deletePerson(id);
		return HttpStatus.NO_CONTENT;
	}

	@GetMapping(value = "/{id}")
	public ResponseDTO getPersonById(@PathVariable @Min(value = 1, message = "ID must be greater than 0") Long id) {
		return bindResponse(getMapper().map(personService.getById(id), PersonDTO.class));
	}

	@GetMapping(value = "/personByName/{name}")
	public ResponseDTO getPersoneByName(@PathVariable String name) {
		List<PersonDTO> dto = new ArrayList<>();
		personService.findByName(name).forEach(ob -> dto.add(getMapper().map(ob, PersonDTO.class)));
		return bindResponse(dto);
	}

	@GetMapping(value = "/all")
	public ResponseDTO getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("firstName").ascending());
		Page<Person> pageData = personService.getAllPersons(pageable);

		Page<PersonDTO> dtoData = pageData.map(person -> getMapper().map(person, PersonDTO.class));

		return bindResponse(dtoData);
	}
}
