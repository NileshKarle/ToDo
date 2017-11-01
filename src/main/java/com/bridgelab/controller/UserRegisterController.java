package com.bridgelab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelab.model.ErrorMessage;
import com.bridgelab.model.User;
import com.bridgelab.service.MailService;
import com.bridgelab.service.UserService;
import com.bridgelab.validator.UserValidation;


@RestController
public class UserRegisterController {

	@Autowired
	UserService userService;
	
	@Autowired
	ErrorMessage errorMessage;
	
	@Autowired
	UserValidation userValidation;
	
	@Autowired
	MailService mailService;
	
	@RequestMapping(value= "/register", method=RequestMethod.POST)
	public ResponseEntity<ErrorMessage> registerUser(@RequestBody User user){
		
		user.setFirstLogin("false");
		String isValid=userValidation.registerValidation(user);
		
		if(isValid.equals("true")){
			
			userService.saveUserData(user);
			//mailService.sendMail(user.getEmail());
			errorMessage.setResponseMessage("success");
			return ResponseEntity.ok(errorMessage);
		}else{
			
			errorMessage.setResponseMessage(isValid);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
		
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e) {
		
		return "Exception "+e;
	}
	
}
