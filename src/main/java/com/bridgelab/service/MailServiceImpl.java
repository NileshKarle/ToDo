package com.bridgelab.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

public class MailServiceImpl implements MailService {

	private MailSender email;

	public MailSender getEmail() {
		return email;
	}

	public void setEmail(MailSender email) {
		this.email = email;
	}
	
	@Async
	public void sendMail(String to,String text,String page)
	{
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("nileshkarle388@gmail.com");
		message.setTo(to);
		message.setSubject("Activation link");
		String urllatest=page+text;
		message.setText(/*"http://192.168.0.179:8080/ToDo/UserActivation/"*/urllatest);
		try {
			email.send(message);
		} catch (MailException e) {
			e.printStackTrace();
		}
		
	}
	
}
