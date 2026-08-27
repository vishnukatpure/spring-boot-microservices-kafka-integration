package com.kafka.microservice_producer.controller;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.microservice_producer.custom.exception.BadRequestException;
import com.kafka.microservice_producer.custom.exception.FormValidationException;
import com.kafka.microservice_producer.dto.PersonDTO;
import com.kafka.microservice_producer.dto.ResponseDTO;
import com.kafka.microservice_producer.kafkaservice.KafkaMessageProducerService;
import com.kafka.microservice_producer.model.Person;
import com.kafka.microservice_producer.services.PersonService;
import com.kafka.microservice_producer.services.UserService;

@RestController
@RequestMapping(value = "/person")
public class PersonResource extends AbstractResource {

	final PersonService personService;

	final KafkaMessageProducerService<Long, Object> kafkaMessageProducerService;

	PersonResource(PersonService personService, UserService userService,
			KafkaMessageProducerService<Long, Object> kafkaMessageProducerService) {
		super(userService);
		this.personService = personService;
		this.kafkaMessageProducerService = kafkaMessageProducerService;
	}

	@GetMapping(value = "/{id}")
	public ResponseDTO getAllUsers(@PathVariable Long id) {
		return bindResponse(getMapper().map(personService.getById(id).get(), PersonDTO.class));
	}

	@GetMapping(value = "/aop/testing/{type}")
	public ResponseDTO aopTesting(@PathVariable String type) throws Exception {
		if (type.equals("1")) {
			throw new BadRequestException("in BadRequestException exception");
		}
		if (type.equals("2")) {
			throw new FormValidationException("Form Validation error");
		}

		throw new NullPointerException("in BadRequestException");
	}

	@GetMapping(value = "/personByName/{name}")
	public ResponseDTO getPersoneByName(@PathVariable String name) {
		List<PersonDTO> dto = new ArrayList<>();
		personService.findByName(name).forEach(ob -> dto.add(getMapper().map(ob, PersonDTO.class)));
		return bindResponse(dto);
	}

	@GetMapping(value = "/all")
	public ResponseDTO getAll() {
		List<PersonDTO> dto = new ArrayList<>();
		personService.getAllPersons().forEach(ob -> dto.add(getMapper().map(ob, PersonDTO.class)));

		return bindResponse(dto);
	}

	@DeleteMapping(value = "/{id}")
	public HttpStatus deletePerson(@PathVariable Long id) {
		personService.deletePerson(id);
		kafkaMessageProducerService.sendMessage("person-topic", id, null);
		return HttpStatus.NO_CONTENT;
	}

	@PostMapping
	public ResponseDTO insertPerson(@RequestBody PersonDTO personDTO) {

		Person person = getMapper().map(personDTO, Person.class);
		person.validate();

		person = personService.addPerson(person);
		ResponseDTO responseDTO = bindResponse(getMapper().map(person, PersonDTO.class));
		kafkaMessageProducerService.sendMessage("person-topic", person.getId(), responseDTO.getObject());
		return responseDTO;
	}

	@PutMapping
	public ResponseDTO updatePerson(@RequestBody PersonDTO personDto) throws AccountNotFoundException {
		
		Person person = personService.updatePerson(personDto);
		ResponseDTO responseDTO = bindResponse(getMapper().map(person, PersonDTO.class));
		kafkaMessageProducerService.sendMessage("person-topic", personDto.getId(), responseDTO.getObject());
		return responseDTO;
	}
}
