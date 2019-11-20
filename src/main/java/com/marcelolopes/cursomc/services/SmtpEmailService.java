package com.marcelolopes.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	MailSender mailSender;
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		mailSender.send(msg);
	}

}
