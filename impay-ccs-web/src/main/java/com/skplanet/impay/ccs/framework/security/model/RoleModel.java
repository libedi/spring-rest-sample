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

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

/**
 * RoleModel
 * @author 최재용
 *
 */
public class RoleModel implements GrantedAuthority {

	private static final long serialVersionUID = -7860105175368474115L;
	
	private String name;
    private List<Privilege> privileges;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}
	
}
