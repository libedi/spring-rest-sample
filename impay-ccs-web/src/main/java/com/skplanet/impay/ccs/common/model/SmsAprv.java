/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.model;

/**
 * SMS 인증요청
 * @author jisu park
 *
 */
public class SmsAprv {

	/** SMS인증요청 key */
	private String authtiSeq;
	/** 인증번호 */
	private String authtiNo;
	
	/** sms메세지 */
	private String msg;
	
	/** 받는 사람 전화번호 */
	private String mphnNo;
	
	/** 등록자 */
	private String regr;
	
	public String getAuthtiSeq() {
		return authtiSeq;
	}
	public void setAuthtiSeq(String authtiSeq) {
		this.authtiSeq = authtiSeq;
	}
	public String getAuthtiNo() {
		return authtiNo;
	}
	public void setAuthtiNo(String authtiNo) {
		this.authtiNo = authtiNo;
	}
	public String getMphnNo() {
		return mphnNo;
	}
	public void setMphnNo(String mphnNo) {
		this.mphnNo = mphnNo;
	}
	public String getRegr() {
		return regr;
	}
	public void setRegr(String regr) {
		this.regr = regr;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}