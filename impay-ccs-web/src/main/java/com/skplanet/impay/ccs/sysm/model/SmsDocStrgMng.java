/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.sysm.model;

/**
 * SMS 문서 보관함 관리
 * @author Jang
 *
 */
public class SmsDocStrgMng {
	private String charStrgNo;	// 문자 보관 번호
	private String charCtnt;	// 문자 내용
	private String regDt;		// 등록 일시
	private String regr;		// 등록자
	
	public String getCharStrgNo() {
		return charStrgNo;
	}
	public void setCharStrgNo(String charStrgNo) {
		this.charStrgNo = charStrgNo;
	}
	public String getCharCtnt() {
		return charCtnt;
	}
	public void setCharCtnt(String charCtnt) {
		this.charCtnt = charCtnt;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegr() {
		return regr;
	}
	public void setRegr(String regr) {
		this.regr = regr;
	}
	
	
}
