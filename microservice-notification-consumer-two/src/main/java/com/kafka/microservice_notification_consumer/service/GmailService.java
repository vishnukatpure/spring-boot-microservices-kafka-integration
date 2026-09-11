package com.kafka.microservice_notification_consumer.service;

import java.io.File;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public final class GmailService implements EmailService {

	private final JavaMailSender mailSender;

	public GmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendEmail(List<String> to, List<String> cc, List<String> bcc, String subject, String body) {

		SimpleMailMessage message = new SimpleMailMessage();

		if (to != null && !to.isEmpty())
			message.setTo(to.toArray(new String[0]));
		if (cc != null && !cc.isEmpty())
			message.setCc(cc.toArray(new String[0]));
		if (bcc != null && !bcc.isEmpty())
			message.setBcc(bcc.toArray(new String[0]));
		message.setSubject(subject);
		message.setText(body);

		mailSender.send(message);
	}

	@Override
	public void sendHtmlEmail(List<String> to, List<String> cc, List<String> bcc, String subject, String html)
			throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		if (to != null && !to.isEmpty())
			helper.setTo(to.toArray(new String[0]));
		if (cc != null && !cc.isEmpty())
			helper.setCc(cc.toArray(new String[0]));
		if (bcc != null && !bcc.isEmpty())
			helper.setBcc(bcc.toArray(new String[0]));

		helper.setSubject(subject);
		helper.setText(html, true);

		mailSender.send(message);
	}

	@Override
	public void sendEmailWithAttachment(List<String> to, List<String> cc, List<String> bcc, String subject, String body,
			File file) throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		if (to != null && !to.isEmpty())
			helper.setTo(to.toArray(new String[0]));
		if (cc != null && !cc.isEmpty())
			helper.setCc(cc.toArray(new String[0]));
		if (bcc != null && !bcc.isEmpty())
			helper.setBcc(bcc.toArray(new String[0]));

		helper.setSubject(subject);
		helper.setText(body);

		helper.addAttachment(file.getName(), file);

		mailSender.send(message);
	}
}
