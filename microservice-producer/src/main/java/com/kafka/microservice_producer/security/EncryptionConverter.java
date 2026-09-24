package com.kafka.microservice_producer.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kafka.microservice_producer.utils.SecurityUtil;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Component
@Converter
public class EncryptionConverter implements AttributeConverter<String, String> {

	private final SecurityUtil securityUtil;
	/** Default "secret" keeps existing encrypted rows decryptable. Override via app.encryption.secret-key. */
	private final String secretKey;

	public EncryptionConverter(SecurityUtil securityUtil,
			@Value("${app.encryption.secret-key:secret}") String secretKey) {
		this.securityUtil = securityUtil;
		this.secretKey = secretKey;
	}

	@Override
	public String convertToDatabaseColumn(String attribute) {
		if (attribute == null) {
			return null;
		}
		try {
			return securityUtil.encrypt(attribute, secretKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attribute;
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		try {
			return securityUtil.decrypt(dbData, secretKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbData;
	}

}
