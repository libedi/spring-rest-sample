package com.skplanet.impay.ccs.framework.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;

/**
 * 인증 성공시 수행할 Handler
 * @author Sangjun, Park
 *
 */
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
			throws IOException, ServletException {
		
		Object principal = authentication.getPrincipal();
        
        if(principal != null && principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails)principal;            
            userDetails.setIp(request.getRemoteAddr());
        }
        
        setDefaultTargetUrl("/");
		super.onAuthenticationSuccess(request, response, authentication);
	}
}