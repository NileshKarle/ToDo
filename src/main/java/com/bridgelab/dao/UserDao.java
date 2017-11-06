package com.bridgelab.dao;

import com.bridgelab.model.User;

public interface UserDao {
	
	public void saveUserData(User user);
	
	public User loginUser(String email,String password);
	
	public User emailValidation(String email);
	
	public User userValidated (int UserId);
}
