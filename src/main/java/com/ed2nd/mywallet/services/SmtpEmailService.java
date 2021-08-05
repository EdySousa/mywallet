package com.ed2nd.mywallet.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.ed2nd.mywallet.domain.User;

public class SmtpEmailService extends AbstractEmailService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender mailSender;

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Sending email...");
		mailSender.send(msg);
		LOG.info("Email sent");
		
	}

	@Override
	public void sendUserConfirmationHtmlEmail(User obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendNewPasswordEmail(User user, String newPass) {
		// TODO Auto-generated method stub
		
	}

}
