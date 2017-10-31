package com.bridgelab.controller;

import javax.servlet.http.HttpSession;

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
	UserValidation userValidation;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> loginUser(@RequestBody User user, HttpSession session) throws Exception {
		
		User userLogined = userService.verifyUserData(user.getEmail(), user.getPassword());
		
		if (userLogined == null) {
			errorMessage.setResponseMessage("Enter valid data.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
		
		session.setAttribute("currentUser", userLogined);
		
		/*TokenGenerator token=new TokenGenerator();
		String compactToken=token.createJWT(userLogined);
		session.setAttribute("token", compactToken);
		System.out.println(compactToken+"<------");
		VerifyToken verifyToken=new VerifyToken();
		boolean tokenvarification=verifyToken.parseJWT(compactToken);
		System.out.println("------>"+tokenvarification);*/
		
		errorMessage.setResponseMessage("success");
		return ResponseEntity.ok(errorMessage);
	}
	
	@RequestMapping(value="/changePassword", method=RequestMethod.POST)
	public ResponseEntity<ErrorMessage> collectEmail(@RequestBody User user, HttpSession session) throws Exception {
		
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
		
		userLogined.setPassword(user.getPassword());
		userService.saveUserData(userLogined);
		session.setAttribute("currentUser", userLogined);
		errorMessage.setResponseMessage("Password updated");
		return ResponseEntity.ok(errorMessage);
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e) {
		return "Exception"+e;
	}
}
