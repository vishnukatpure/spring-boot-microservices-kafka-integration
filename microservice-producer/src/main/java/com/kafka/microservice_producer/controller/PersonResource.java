package com.kafka.microservice_producer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.microservice_producer.dto.PersonDTO;
import com.kafka.microservice_producer.dto.ResponseDTO;
import com.kafka.microservice_producer.kafkaservice.KafkaMessageProducerService;
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
		return personService.getById(id);
	}

	@GetMapping(value = "/aop/testing/{type}")
	public ResponseDTO aopTesting(@PathVariable String type) throws Exception {
		ResponseDTO dto = personService.aopTesting(type);
		return dto;
	}

	@GetMapping(value = "/personByName/{name}")
	public ResponseDTO getPersoneByName(@PathVariable String name) {
		return personService.findByName(name);
	}

	@GetMapping(value = "/all")
	public ResponseDTO getAll() {
		return personService.getAllPersons();
	}

	@DeleteMapping(value = "/{id}")
	public HttpStatus deletePerson(@PathVariable Long id) {
		personService.deletePerson(id);
		kafkaMessageProducerService.sendMessage("person-topic", id, null);
		return HttpStatus.NO_CONTENT;
	}

	@PostMapping
	public ResponseDTO insertPersone(@RequestBody PersonDTO person) {
		ResponseDTO responseDTO = personService.addPerson(person);
		kafkaMessageProducerService.sendMessage("person-topic", person.getId(), responseDTO.getObject());
		return responseDTO;
	}

	@PutMapping
	public ResponseDTO updatePerson(@RequestBody PersonDTO personDto) {
		ResponseDTO responseDTO = personService.updatePerson(personDto);
		kafkaMessageProducerService.sendMessage("person-topic", personDto.getId(), responseDTO.getObject());
		return responseDTO;
	}
}
