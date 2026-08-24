package com.kafka.microservice_producer.security;

import com.kafka.microservice_producer.utils.SecurityUtil;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EncryptionConverter implements AttributeConverter<String, String> {

	private final SecurityUtil securityUtil;
	private String sectretKey = "secret";

	public EncryptionConverter(SecurityUtil securityUtil) {
		this.securityUtil = securityUtil;
	}

	@Override
	public String convertToDatabaseColumn(String attribute) {
		if (attribute == null) {
			return null;
		}
		try {
			return securityUtil.encrypt(attribute, sectretKey);
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
			return securityUtil.decrypt(dbData, sectretKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbData;
	}

}
