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

/**
 * 
 * @author Sangjun, Park
 *
 */
public class InvalidParameterException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3642565681738524596L;

	public InvalidParameterException(String message) {
		super(message);
	}
	
	public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParameterException(Throwable cause) {
        super(cause);
    }
}