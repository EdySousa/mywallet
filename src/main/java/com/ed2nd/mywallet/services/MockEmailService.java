package com.ed2nd.mywallet.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import com.ed2nd.mywallet.domain.User;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Send email simulation...");
		LOG.info(msg.toString());
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
