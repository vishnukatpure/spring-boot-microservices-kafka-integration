package com.kafka.microservice_producer.model;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.util.StringUtils;

import com.kafka.microservice_producer.custom.exception.FormValidationException;
import com.kafka.microservice_producer.security.EncryptionConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "people")
@EnableJpaAuditing
public class Person extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1676382221207795923L;

	@Column(name = "age")
	private Integer age;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	@Convert(converter = EncryptionConverter.class)
	private String lastName;

	@Version
	private Long version;

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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Person{" + "id=" + getId() + ", age=" + age + ", firstName='" + firstName + '\'' + ", lastName='"
				+ lastName + '\'' + '}';
	}

	public void validate() {
		String msg = "";
		if (!StringUtils.hasLength(firstName))
			msg += "invalid First name";

		if (StringUtils.hasLength(msg))
			throw new FormValidationException(msg);
	}
}
