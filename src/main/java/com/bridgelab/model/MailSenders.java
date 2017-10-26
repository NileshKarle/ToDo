package com.bridgelab.model;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailSenders {

	private MailSender email;

	public MailSender getEmail() {
		return email;
	}

	public void setEmail(MailSender email) {
		this.email = email;
	}
	
	public void sendMail(String to){
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("harshil0279@gmail.com");
		message.setTo(to);
		message.setSubject("welcome");
		message.setText("register success");
		email.send(message);
	}
}
