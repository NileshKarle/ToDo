package com.bridgelab.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bridgelab.model.User;
import com.bridgelab.service.UserService;
import com.bridgelab.token.VerifyToken;

public class NotesInterceptor implements HandlerInterceptor{
	
	@Autowired
	VerifyToken verifyToken;

	@Autowired
	UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String headers=request.getHeader("token");
		int userId = verifyToken.parseJWT(headers);
		User user = userService.userValidated(userId);
		
		if(user == null || user.getLoginStatus().equals("false")){
			return false;
		}
		
		request.setAttribute("loginedUser", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
