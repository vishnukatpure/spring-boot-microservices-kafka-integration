package com.kafka.microservice_producer.model;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.util.StringUtils;

import com.kafka.microservice_producer.custom.exception.FormValidationException;
import com.kafka.microservice_producer.security.EncryptionConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "people", indexes = { @Index(name = "idx_person_email", columnList = "email"),
		@Index(name = "idx_person_mobile", columnList = "mobile") })
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

	private String mobile;

	private String email;

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Person [age=" + age + ", firstName=" + firstName + ", lastName=" + lastName + ", mobile=" + mobile
				+ ", email=" + email + ", version=" + version + "]";
	}

	public void validate() {
		String msg = "";
		if (!StringUtils.hasLength(firstName))
			msg += "invalid First name";

		if (StringUtils.hasLength(msg))
			throw new FormValidationException(msg);
	}
}
