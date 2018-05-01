package com.skplanet.impay.ccs.framework.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * 인증 실패시 수행할 Handler
 * @author Sangjun, Park
 *
 */
@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomFailureHandler.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, 
										HttpServletResponse response,
										AuthenticationException e) throws IOException, ServletException {
		
		String queryParam = request.getContextPath()+"/login/form?";
		
		if(e instanceof UsernameNotFoundException) {
			queryParam = queryParam+"&errorMsg=UserNotFound";
		}
		
		if(e instanceof BadCredentialsException) {
			queryParam = queryParam+"&errorMsg=BadCredentials";
		}
		
		if(e instanceof LockedException) {
			queryParam = queryParam+"&errorMsg=AccountLocked";
		}
		
		if(e instanceof DisabledException) {
			queryParam = queryParam+"&errorMsg=AccountDisabled";
		}
		
		response.sendRedirect(queryParam);
	}
}