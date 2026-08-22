package com.kafka.microservice_producer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kafka.microservice_producer.modal.Employee;
import com.kafka.microservice_producer.repository.EmployeeRepository;
import com.kafka.microservice_producer.service.generic.GenericService;

@Service
public class EmployeeService extends GenericService<Employee> {

	EmployeeRepository<Employee> employeeRepository;

	EmployeeService(UserService userService, EmployeeRepository<Employee> employeeRepository) {
		super(userService);
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee create(Employee entity) {
		return employeeRepository.save(entity);
	}

	@Override
	public Employee update(Employee entity) {
		return employeeRepository.save(entity);
	}

	@Override
	public void delete(Employee entity) {
		employeeRepository.delete(entity);
	}

	@Override
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee findById(Long Id) {
		return employeeRepository.getReferenceById(Id);
	}

}
