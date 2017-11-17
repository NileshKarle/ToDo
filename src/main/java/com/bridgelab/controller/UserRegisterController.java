package com.bridgelab.controller;

import java.io.IOException;
import java.net.URISyntaxException;
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

/**
 * @author Nilesh
 *
 * @Description This controller is called for user registration 
 */
@RestController
public class UserRegisterController {

	@Autowired
	UserService userService;

	@Autowired
	UserValidation userValidation;

	@Autowired
	MailService mailService;

	@Autowired
	TokenGenerator tokenGenerator;

	@Autowired
	VerifyToken verifyToken;

	
	/**
	 * @param user(user Object)
	 * @return ResponseEntity
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @Description This method collet's the user object as the parameter.
	 *              This user object contains the user details.
	 *              User details are stored in the database and the user receives a mail for user verification.
	 *              User can login only if the user is verified.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> registerUser(@RequestBody User user, HttpServletRequest request) throws IOException, URISyntaxException {


		ErrorMessage errorMessage = new ErrorMessage();

		User Olduser = userService.emailValidation(user.getEmail());

		// if email already exists in the database.
		if (Olduser != null) {
			errorMessage.setResponseMessage("Email already exists;");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}

		// else validate the other details.
		String isValid = userValidation.registerValidation(user);

		// if details are correct.
		if (isValid.equals("true")) {
			user.setFirstTimeLogin("false");
			user.setLoginStatus("false");

			// encrypt the password and then set it.
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
			userService.saveUserData(user);

			URL url;
			url = new URL(request.getRequestURL().toString());	
			String url1=url.getProtocol()+"://"+url.getHost()+":"+url.getPort()+"ToDo/UserActivation/";

			// Generate a token and send a mail.
			String compactToken = tokenGenerator.createJWT(user.getId());
			mailService.sendMail(user.getEmail(), compactToken.replaceAll("\\.", "/"),url1);

			errorMessage.setResponseMessage("success");
			return ResponseEntity.ok(errorMessage);
		}

		// if other details are incorrect.
		else {
		    errorMessage.setResponseMessage(isValid);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
	}

	/**
	 * @param header
	 * @param payload
	 * @param footer
	 * 
	 * @Description This method is invoked when the user click's the link received in the mail.
	 *              Through the link the user is verified and the user first time login field is set to true.
	 *              After the user verification the user is redirected to the login page.
	 *              So the user can login successfully.
	 */
	
	@RequestMapping(value = "/UserActivation/{header}/{payload}/{footer}")
	public void RedirectToHomePage(@PathVariable("header") String header, @PathVariable("payload") String payload,
			@PathVariable("footer") String footer, HttpServletResponse response) {
		
		//Regenerate the token in the original form.
		String token = header + "." + payload + "." + footer;
		
		try {
			int verifiedUserId = verifyToken.parseJWT(token);
			User user = userService.userValidated(verifiedUserId);
			// System.out.println(verifiedUserId+"<-----this is id");
			
			if (verifiedUserId != 0 || user != null) {
				user.setFirstTimeLogin("true");
				userService.saveUserData(user);
				response.sendRedirect("/ToDo/#!/login");
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
