package com.kafka.microservice_producer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafka.microservice_producer.modal.Employee;

public interface EmployeeRepository<P> extends JpaRepository<Employee, Long> {

}