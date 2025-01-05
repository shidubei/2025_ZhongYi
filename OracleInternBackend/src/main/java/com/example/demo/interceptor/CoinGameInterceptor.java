package com.example.demo.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * CoinGameInterceptor:
 *  This is an interceptor for the web application,avoid user */
public class CoinGameInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object Handler) throws Exception {
		String url = request.getRequestURI();
		if(!url.equals("/Coin-game")) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return false;
		}
		return true;
		
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
	
	}

}
