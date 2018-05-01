/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.framework.exception;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author KHJIN
 */
@Controller
@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
	
	/** DB관련 예외처리  */
	@ExceptionHandler({SQLException.class, UncategorizedDataAccessException.class, DataAccessException.class})
	public String dbErrorHandler(Exception e) {
	    logger.error("DB Error : {}", e.toString(), e);	// stack trace 노출처리
	    return "exception/error";
	}
	
	@ExceptionHandler({AccessDeniedException.class})
	public String accessHandler(Exception e) {
        logger.error("Menu Access Denied : {}", e.toString(), e);	// stack trace 노출처리
        return "exception/access_denied";
	}
	 
    @ResponseStatus(HttpStatus.CONFLICT) 
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleConflictException(Exception e){
    	logger.error("HTTP Conflict : {}", e.toString(), e);	// stack trace 노출처리
        return "exception/conflict";
    }
    
    @ExceptionHandler(Exception.class)
    public String commonError(HttpServletRequest req, HttpServletRequest res, Exception exception) {
    	logger.error("Request : {} raised {}", req.getRequestURI(), exception, exception);	// stack trace 노출처리
    	return "redirect:/handle/error";
    }
    
    @RequestMapping(value = "/handle/error")
    public String handleError(HttpServletRequest req, HttpServletRequest res) {
    	return "exception/error";
    }
    
    @RequestMapping(value = "/error/common")
    public String handleCommonError(HttpServletRequest req, HttpServletRequest res) {
    	return "exception/error_common";
    }
    
    @ExceptionHandler(UsernameNotFoundException.class)
    public void handleUsernameNotFoundException(HttpServletRequest req, HttpServletResponse res, Exception exception) throws Exception{
    	logger.error("User Not Found : {} raised {}", req.getRequestURL(), exception, exception);	// stack trace 노출처리
    	res.sendRedirect(req.getContextPath() + "/login/form?errorMsg=USER_NOT_FOUND");
    }
}