package com.bridgelab.service;

import com.bridgelab.model.User;

public interface UserService {
	
	public void saveUserData(User user);
	
	public User verifyUserData(String email,String password);
	
	public String emailValidation(String email);
		
}
