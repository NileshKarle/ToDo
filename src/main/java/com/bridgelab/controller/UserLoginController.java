package com.bridgelab.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelab.model.ErrorMessage;
import com.bridgelab.model.User;
import com.bridgelab.service.MailService;
import com.bridgelab.service.UserService;
import com.bridgelab.token.TokenGenerator;
import com.bridgelab.token.VerifyToken;
import com.bridgelab.validator.UserValidation;

@RestController
public class UserLoginController {

	@Autowired
	UserService userService;

	@Autowired
	ErrorMessage errorMessage;

	@Autowired
	MailService mailService;
	
	@Autowired
	TokenGenerator tokenGenerator;
	
	@Autowired
	UserValidation userValidation;
	
	@Autowired
	VerifyToken verifyToken;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> loginUser(@RequestBody User user, HttpSession session) throws Exception {
		
		User userLogined = userService.emailValidation(user.getEmail());
		
		if (userLogined == null) {
			errorMessage.setResponseMessage("Such Email dose not exists try again later.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
		
		if(userLogined.getFirstLogin().equals("false")){
			errorMessage.setResponseMessage("User must login from mail for the first time.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
		Boolean passwordStatus=BCrypt.checkpw(user.getPassword(), userLogined.getPassword());
		if(!passwordStatus){
			errorMessage.setResponseMessage("check your password and try again.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
		
		String compactToken = tokenGenerator.createJWT(userLogined.getId(),userLogined.getFirstName());
		session.setAttribute("AccessToken", compactToken);
		System.out.println(compactToken+"<----- !!!!");
		errorMessage.setResponseMessage(compactToken);
		return ResponseEntity.ok(errorMessage);
	}
	
	@RequestMapping(value="/forgotPassword", method=RequestMethod.POST)
	public ResponseEntity<ErrorMessage> collectNewPassword(@RequestBody User user, HttpSession session) throws Exception {
		
		User userLogined = userService.emailValidation(user.getEmail());
		
		if (userLogined == null) {
			errorMessage.setResponseMessage("such email dose not exists.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
		
		String validatePassword = userValidation.validatePassword(user.getPassword());
		
		if(validatePassword.equals("Password must contain words followed numbers.")){
			errorMessage.setResponseMessage(validatePassword);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
		
		String compactToken = tokenGenerator.createJWT(userLogined.getId(),user.getPassword());
		mailService.sendMail(userLogined.getEmail(), compactToken.replaceAll("\\.", "/"),"http://192.168.0.179:8080/ToDo/UpdatedPassword/");
		errorMessage.setResponseMessage("success");
		return ResponseEntity.ok(errorMessage);
	}
	
	@RequestMapping(value = "/UpdatedPassword/{header}/{payload}/{footer}")
	public void RedirectToHomePage(@PathVariable("header") String header, @PathVariable("payload") String payload,
			@PathVariable("footer") String footer, HttpServletResponse response) {
		
		String token = header +"."+payload+"."+ footer;
		try {
			int verifiedUserId = verifyToken.parseJWT(token);
			User user=userService.userValidated(verifiedUserId);
			
			if (verifiedUserId!=0 || user!=null) {
				String encrypt=BCrypt.hashpw(verifyToken.parseString(token), BCrypt.gensalt(10));
				user.setPassword(encrypt);
				userService.saveUserData(user);
				response.sendRedirect("http://localhost:8080/ToDo/#!/login");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e) {
		return "Exception"+e;
	}
}
