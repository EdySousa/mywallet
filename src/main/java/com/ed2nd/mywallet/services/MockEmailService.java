package com.ed2nd.mywallet.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Send email simulation...");
		LOG.info(msg.toString());
		LOG.info("Email sent");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Send HTML email simulation...");
		LOG.info(msg.toString());
		LOG.info("Email sent");
	}
}
