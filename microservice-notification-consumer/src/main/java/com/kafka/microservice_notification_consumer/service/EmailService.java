package com.kafka.microservice_notification_consumer.service;

import java.io.File;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	private final JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendEmail(String to, String subject, String body) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);

		mailSender.send(message);
	}

	public void sendHtmlEmail(String to, String subject, String html) throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(html, true);

		mailSender.send(message);
	}

	public void sendEmailWithAttachment(String to, String subject, String body, File file) throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body);

		helper.addAttachment(file.getName(), file);

		mailSender.send(message);
	}
}
