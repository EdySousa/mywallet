package com.ed2nd.mywallet.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.ed2nd.mywallet.domain.User;

public interface EmailService {

	void sendUserConfirmationEmail(User obj);

	void sendEmail(SimpleMailMessage msg);

	void sendUserConfirmationHtmlEmail(User obj);

	void sendHtmlEmail(MimeMessage msg);
		
	void sendNewPasswordEmail(User user, String newPass);
}
