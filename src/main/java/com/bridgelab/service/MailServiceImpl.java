package com.bridgelab.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

@Async
public class MailServiceImpl implements MailService {

	private MailSender email;

	public MailSender getEmail() {
		return email;
	}

	public void setEmail(MailSender email) {
		this.email = email;
	}
	
	public void sendMail(String to){
		
		//System.out.println("in mail");
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("ghargesiddharth@gmail.com");
		message.setTo(to);
		message.setSubject("welcome");
		message.setText("register success");
		email.send(message);
	}
	
}
