package com.bridgelab.service;

import com.bridgelab.model.User;

public interface UserService {
	
	public void saveUserData(User user);
	
	public User emailValidation(String email);
	
	public User userValidated(int UserId);
		
}
