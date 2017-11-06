package com.bridgelab.service;

import com.bridgelab.model.User;

public interface UserService {
	
	public void saveUserData(User user);
	
	public User verifyUserData(String email,String password);
	
	public User emailValidation(String email);
	
	public User userValidated(int UserId);
		
}
