package com.kafka.microservice_producer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kafka.microservice_producer.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

	List<Person> findByFirstName(String firstName);

	Person findByFirstNameAndLastName(String firstName, String lastName);
}
