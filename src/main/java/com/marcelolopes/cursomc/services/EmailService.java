package com.marcelolopes.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.marcelolopes.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
	
}
