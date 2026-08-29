package com.kafka.microservice_producer.dto;

public class PersonDTO extends AbstractEntityBaseDTO {

	private Integer age;
	private String firstName;
	private String lastName;

	public PersonDTO() {
	}

	public PersonDTO(long id, String firstName, String lastName) {
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
