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

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.impay.ccs.common.model.CntcInfo;
import com.skplanet.impay.ccs.framework.model.ValidationMarker;

/**
 * 사용자 관리 Model
 * @author Sangjun, Park
 *
 */
public class UserMng extends ValidationMarker {
	/** 사용자 Seq */
	@NotNull(groups = {Update.class})
	@NotEmpty(groups={Update.class})
	private String userSeq;
	/** 사용자ID */
	private String userId;
	/** 비밀번호 */
	private String pwd;
	/** 사용자 이름 */
	private String userNm;
	/** 연락처 정보 */
	private CntcInfo cntcInfo;
	
	/** Constructor */
	public UserMng() {
	}
	public UserMng(String userSeq, String userId) {
		this.userSeq = userSeq;
		this.userId = userId;
	}

	/* setter/getter */
	public String getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public CntcInfo getCntcInfo() {
		return cntcInfo;
	}
	public void setCntcInfo(CntcInfo cntcInfo) {
		this.cntcInfo = cntcInfo;
	}

}
