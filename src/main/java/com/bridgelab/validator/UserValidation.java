package com.bridgelab.validator;

import com.bridgelab.model.User;

public interface UserValidation {
	
	public String registerValidation(User user);
	
	public String validatePassword(String password);

}
