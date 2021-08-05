package com.ed2nd.mywallet.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ed2nd.mywallet.services.DBService;
import com.ed2nd.mywallet.services.EmailService;
import com.ed2nd.mywallet.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService; 
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		this.dbService.instantiateTestDatabase();		
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}
