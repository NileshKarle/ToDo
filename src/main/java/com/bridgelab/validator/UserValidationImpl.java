package com.bridgelab.validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelab.model.User;
import com.bridgelab.service.UserService;

public class UserValidationImpl implements UserValidation {

	@Autowired
	UserService userService;
	
	@Override
	public String registerValidation(User user) {
		String result="false";
		
		String nameFormat="^[a-zA-Z]+$";
		
		String contactValidation="[a-zA-Z]";
		
		String emailFormat="[a-zA-Z0-9\\.]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,5}$";
		
		String passwordFormat="[a-zA-Z]+[0-9]+";
		
		String contactFormat="[0-9]{10}";
		
		if(user.getFirstName()==null||user.getFirstName()==""){
			result="First Name cannot be empty.";
			return result;
		}
		
		else if(user.getLastName()==null||user.getLastName()==""){
			result="Last Name cannot be empty.";
			return result;
		}
		
		else if(user.getEmail()==null||user.getEmail()==""){
			result="Email cannot be empty.";
			return result;
		}
		
		else if(user.getPassword()==null||user.getPassword()==""){
			result="Password cannot be empty.";
			return result;
		}
		
		else if(user.getContact()==null||user.getContact()==""){
			result="Contact cannot be empty.";
			return result;
		}
		
		else if(!user.getFirstName().matches(nameFormat)){
			result="First Name must contain only characters.";
			return result;
		}
		
		else if(!user.getLastName().matches(nameFormat)){
			result="Last Name must contain only characters.";
			return result;
		}
		
		else if(!user.getEmail().matches(emailFormat)){
			result="Email is not in correct format.";
			return result;
		}
		
		else if(!user.getPassword().matches(passwordFormat)){
			result="Password must contain words followed numbers.";
			return result;
		}
		
		else if(user.getContact().matches(contactValidation)){
			result="contact must contain numbers only.";
			return result;
		}
		
		else if(!user.getContact().matches(contactFormat)){
			result="contact must contain exectly 10 digits";
			return result;
		}

		else{
			User newUser=userService.emailValidation(user.getEmail());
			if(newUser==null){
				result="true";
				return result;
			}
			else{
				result="email already exists";
				return result;
			}		
		}
	}

	@Override
	public String validatePassword(String password) {
		
		String passwordFormat="[a-zA-Z]+[0-9]+";
		
		 if(!password.matches(passwordFormat)){
			 return "Password must contain words followed numbers.";
			}
		 else{
			 return "true";
		 }
	}
}
