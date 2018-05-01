/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.uim.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.impay.ccs.framework.model.ValidationMarker;

/**
 * Password Model
 * @author Sangjun, Park
 *
 */
public class UserMngPwd extends ValidationMarker {
	/** 기존 비밀번호 */
	@NotNull(groups = Update.class)
	@NotEmpty(groups = Update.class)
	@Size(min = 7)
	@Pattern(regexp = "^[0-9a-zA-Z~!@#$%^&*()_+|`=\\;?/-<>:\".,'\\[\\]\\{\\}]+$")
	private String pwd;

	/** 신규 비밀번호 */
	@NotNull(groups = Update.class)
	@NotEmpty(groups = Update.class)
	@Size(min = 7)
	@Pattern(regexp = "^[0-9a-zA-Z~!@#$%^&*()_+|`=\\;?/-<>:\".,'\\[\\]\\{\\}]+$")
	private String newPwd;

	/** 신규 비밀번호 확인 */
	@NotNull(groups = Update.class)
	@NotEmpty(groups = Update.class)
	@Size(min = 7)
	@Pattern(regexp = "^[0-9a-zA-Z~!@#$%^&*()_+|`=\\;?/-<>:\".,'\\[\\]\\{\\}]+$")
	private String newPwd2;

	/** 사용자 일련번호 */
	@NotNull(groups = Update.class)
	@NotEmpty(groups = Update.class)
	private String userSeq;
	
	/** 최종변경자 */
	private String lastChgr;

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getNewPwd2() {
		return newPwd2;
	}

	public void setNewPwd2(String newPwd2) {
		this.newPwd2 = newPwd2;
	}

	public String getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}

	public String getLastChgr() {
		return lastChgr;
	}

	public void setLastChgr(String lastChgr) {
		this.lastChgr = lastChgr;
	}
	
}
