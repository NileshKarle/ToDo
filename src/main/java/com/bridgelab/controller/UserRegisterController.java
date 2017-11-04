package com.bridgelab.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class UserRegisterController {

	@Autowired
	UserService userService;

	@Autowired
	ErrorMessage errorMessage;

	@Autowired
	UserValidation userValidation;

	@Autowired
	MailService mailService;

	@Autowired
	TokenGenerator tokenGenerator;

	@Autowired
	VerifyToken verifyToken;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> registerUser(@RequestBody User user) {

		user.setFirstLogin("false");
		String isValid = userValidation.registerValidation(user);

		if (isValid.equals("true")) {
			userService.saveUserData(user);
			String compactToken = tokenGenerator.createJWT(user.getId());
			mailService.sendMail(user.getEmail(), compactToken.replaceAll("\\.", "/"));
			errorMessage.setResponseMessage("success");
			return ResponseEntity.ok(errorMessage);
		} else {

			errorMessage.setResponseMessage(isValid);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}

	}

	@RequestMapping(value = "/UserActivation/{header}/{payload}/{footer}")
	public void RedirectToHomePage(@PathVariable("header") String header, @PathVariable("payload") String payload,
			@PathVariable("footer") String footer, HttpServletResponse response) {
		String token = header +"."+payload+"."+ footer;
		try {
			int verifiedUserId = verifyToken.parseJWT(token);
			//System.out.println(verifiedUserId+"<-----this is id");
			if (verifiedUserId!=0) {
				userService.userValidated(verifiedUserId);
				response.sendRedirect("http://localhost:8080/ToDo/#!/home");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e) {

		return "Exception " + e;
	}

}
