package com.ed2nd.mywallet.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ed2nd.mywallet.domain.User;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendUserConfirmationEmail(User obj) {
		SimpleMailMessage sm = prepareSimpleMessageFromUser(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMessageFromUser(User obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Confirmation User Account");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}

	protected String htmlFromTemplateUser(User obj) {
		Context context = new Context();
		context.setVariable("user", obj);
		return templateEngine.process("email/newUser", context);
	}

	@Override
	public void sendUserConfirmationHtmlEmail(User obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromUser(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendUserConfirmationEmail(obj);
		}

	}

	protected MimeMessage prepareMimeMessageFromUser(User obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("New user: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplateUser(obj), true);
		return mimeMessage;
	}

	@Override
	public void sendNewPasswordEmail(User user, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(user, newPass);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(User user, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(user.getEmail());
		sm.setFrom(sender);
		sm.setSubject("New Paword Request");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("New password: " + newPass);
		return sm;
	}

}
