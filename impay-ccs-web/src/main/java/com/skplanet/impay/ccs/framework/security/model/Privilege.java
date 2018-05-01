/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.framework.security.model;

import java.io.Serializable;

/**
 * Privilege Model
 * @author Sangjun, Park
 *
 */
public class Privilege implements Serializable {
	
	private static final long serialVersionUID = -3006328516944074685L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
