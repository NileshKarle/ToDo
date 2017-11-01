package com.bridgelab.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelab.dao.UserDao;
import com.bridgelab.model.User;

public class UserServiceImpl implements UserService {


	@Autowired 
	UserDao userDao;
	
	@Override
	public void saveUserData(User user) {		
		userDao.saveUser(user);
	}
	
	@Override
	public User verifyUserData(String email,String password){
		return userDao.loginUser(email,password);
	}

	@Override
	public User emailValidation(String email) {
		User user = userDao.emailValidation(email);
		if(user==null){
			return null;
		}
		return user;
	}

}