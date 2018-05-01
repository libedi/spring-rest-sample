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

import org.springframework.validation.Errors;

/**
 * InvalidRequestException
 * @author jisu park
 *
 */
public class InvalidRequestException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3623234221764378590L;
	
	private Errors errors;

    public InvalidRequestException(String message, Errors errors) {
        super();
        this.errors = errors;
    }

    public Errors getErrors() { return errors; }
}
