package com.kafka.microservice_producer.repository;

import org.springframework.data.repository.CrudRepository;

import com.kafka.microservice_producer.modal.Employee;

public interface EmployeeRepository<P> extends CrudRepository<Employee, Long> {

}