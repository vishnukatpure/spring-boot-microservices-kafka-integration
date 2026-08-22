package com.kafka.microservice_producer.resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.microservice_producer.modal.Employee;
import com.kafka.microservice_producer.service.EmployeeService;
import com.kafka.microservice_producer.service.KafkaMessageProducerService;

@RestController
@RequestMapping("/employee/")
public class EmployeeResource {

	final KafkaMessageProducerService<String, Object> kafkaMessageProducerService;

	final EmployeeService employeeService;

	EmployeeResource(KafkaMessageProducerService<String, Object> kafkaMessageProducerService,
			EmployeeService employeeService) {
		this.kafkaMessageProducerService = kafkaMessageProducerService;
		this.employeeService = employeeService;
	}

	@PostMapping
	public String createEmployee(@RequestBody Employee employee) {

		employee = employeeService.create(employee);

		kafkaMessageProducerService.sendMessage("employee-topic", employee.getId().toString(), employee);
		return "Employee added Succesfully";
	}

	@PutMapping
	public String updateEmployee(@RequestBody Employee employee) {

		employee = employeeService.create(employee);
		Employee emp = employeeService.findById(employee.getId());
		if (emp != null) {
			emp.setEmail(employee.getEmail());
			emp.setFirstName(employee.getFirstName());
			emp.setLastName(employee.getLastName());
			employeeService.update(emp);
			kafkaMessageProducerService.sendMessage("employee-topic", employee.getId().toString(), employee);
		}

		return "Employee Updated Succesfully";
	}

	@DeleteMapping
	public String deleteEmployee(@RequestBody Employee employee) {

		employee = employeeService.create(employee);
		Employee emp = employeeService.findById(employee.getId());
		if (emp != null) {
			employeeService.delete(emp);
			kafkaMessageProducerService.sendMessage("employee-topic", employee.getId().toString(), employee);
		}

		return "Employee deleted Succesfully";
	}

}
