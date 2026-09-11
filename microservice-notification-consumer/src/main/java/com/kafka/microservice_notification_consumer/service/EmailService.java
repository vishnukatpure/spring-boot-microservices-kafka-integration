package com.kafka.microservice_notification_consumer.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public sealed interface EmailService permits GmailService {

	void sendEmail(List<String> to, List<String> cc, List<String> bcc, String subject, String body);

	void sendHtmlEmail(List<String> to, List<String> cc, List<String> bcc, String subject, String html)
			throws MessagingException;

	void sendEmailWithAttachment(List<String> to, List<String> cc, List<String> bcc, String subject, String body,
			File file) throws MessagingException;

}
