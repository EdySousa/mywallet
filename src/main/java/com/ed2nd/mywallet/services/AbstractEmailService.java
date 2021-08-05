package com.ed2nd.mywallet.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.ed2nd.mywallet.domain.User;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;


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

}
