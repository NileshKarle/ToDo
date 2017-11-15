package com.bridgelab.controller;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
	MailService mailService;

	@Autowired
	TokenGenerator tokenGenerator;

	@Autowired
	UserValidation userValidation;

	@Autowired
	VerifyToken verifyToken;

	/**
	 * @param user
	 * @return ResponseEntity
	 * 
	 * @Description This method check's if the email and password exist's in
	 *              database. If it exist's then further it check's if the user
	 *              is verified or not. If not then corresponding customize
	 *              message is send to the user.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> loginUser(@RequestBody User user,HttpServletRequest request) throws Exception {
		
		ErrorMessage errorMessage = new ErrorMessage();

		// Gets the object of user from the database if the email exist's in database.
		User userLogined = userService.emailValidation(user.getEmail());

		// if no such email exist's in database.
		if (userLogined == null) {
			errorMessage.setResponseMessage("Such Email does not exist try again later.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}

		// if such user exist's in database but the user is not verified.
		if (userLogined.getFirstTimeLogin().equals("false")) {
			errorMessage.setResponseMessage("User must login from mail for the first time.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}

		// decrypt the password and check if it is vallid or not.
		Boolean passwordStatus = BCrypt.checkpw(user.getPassword(), userLogined.getPassword());

		// if the password is not valid.
		if (!passwordStatus) {
			errorMessage.setResponseMessage("check your password and try again.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}

		// if the password is valid.
		String compactToken = tokenGenerator.createJWT(userLogined.getId());
		userLogined.setLoginStatus("true");
		userService.saveUserData(userLogined);
		errorMessage.setResponseMessage(compactToken);
		return ResponseEntity.ok(errorMessage);

	}

	@RequestMapping(value="/logout",method=RequestMethod.POST)
	public void logout(@RequestHeader(value = "token") String headers){
		int userId = verifyToken.parseJWT(headers);
		User user = userService.userValidated(userId);
		user.setLoginStatus("false");
		userService.saveUserData(user);
	}
	
	
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> emailValidation(@RequestBody User user, HttpServletRequest request)
			throws Exception {
		
		User userLogined = userService.emailValidation(user.getEmail());
		ErrorMessage errorMessage = new ErrorMessage();

		if (userLogined == null) {
			errorMessage.setResponseMessage("such email dose not exists.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}

		URL url;
		url = new URL(request.getRequestURL().toString());	
		String url1=url.getProtocol()+"://"+url.getHost()+":"+url.getPort()+"/ToDo/redirect/";

		String compactToken = tokenGenerator.createJWT(userLogined.getId());
		mailService.sendMail(userLogined.getEmail(),compactToken.replaceAll("\\.", "/"),url1);
		errorMessage.setResponseMessage("success");
		return ResponseEntity.ok(errorMessage);

	}
		
	@RequestMapping(value = "/redirect/{header}/{payload}/{footer}")
	public void redirect(@PathVariable("header") String header, @PathVariable("payload") String payload,
			@PathVariable("footer") String footer, HttpServletResponse response) {
		
		//Regenerate the token in the original form.
		String token = header + "." + payload + "." + footer;
		
		try {
			int verifiedUserId = verifyToken.parseJWT(token);
			// System.out.println(verifiedUserId+"<-----this is id");
			
			if (verifiedUserId != 0) {
				System.out.println("it is in the /new page");
				response.sendRedirect("http://localhost:8080/ToDo/#!/forgotPassword"); 
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	@RequestMapping(value = "/UpdatedPassword")
	public ResponseEntity<ErrorMessage> updatePassword(@RequestBody User user) throws IOException {
		
		System.out.println("its in the updatepassword.....");
		ErrorMessage errorMessage=new ErrorMessage();
		User OldUser=userService.emailValidation(user.getEmail());
		
		if(OldUser!=null){
		String encrypt = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
		OldUser.setPassword(encrypt);
		userService.saveUserData(OldUser);
		errorMessage.setResponseMessage("success");
		return ResponseEntity.ok(errorMessage);
		}
		
		errorMessage.setResponseMessage("Such email does not exist");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}
	

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e) {
		return "Exception" + e;
	}
}
