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
		System.out.println("logined user is " + userLogined.getEmail());
		session.setAttribute("currentUser", userLogined);
		errorMessage.setResponseMessage("success");
		return ResponseEntity.ok(errorMessage);
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e) {
		return "Exception"+e;
	}
}
