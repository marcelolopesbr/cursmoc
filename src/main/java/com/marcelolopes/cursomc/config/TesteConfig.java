package com.marcelolopes.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.marcelolopes.cursomc.services.DBService;
import com.marcelolopes.cursomc.services.EmailService;
import com.marcelolopes.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TesteConfig {
	
	@Autowired
	DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}
