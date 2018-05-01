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
 * 이용자 정보 Model
 * @author Sangjun, Park
 *
 */
public class PayrInfo {
	private String payrSeq;
	private String di;
	private String regDt;
	private String lastChgDt;
	private String brthYy;
	private String payItcptYn;
	private String payItcptDt;
	private String payItcptRegr;
	
	public String getPayrSeq() {
		return payrSeq;
	}
	public void setPayrSeq(String payrSeq) {
		this.payrSeq = payrSeq;
	}
	public String getDi() {
		return di;
	}
	public void setDi(String di) {
		this.di = di;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getLastChgDt() {
		return lastChgDt;
	}
	public void setLastChgDt(String lastChgDt) {
		this.lastChgDt = lastChgDt;
	}
	public String getBrthYy() {
		return brthYy;
	}
	public void setBrthYy(String brthYy) {
		this.brthYy = brthYy;
	}
	public String getPayItcptYn() {
		return payItcptYn;
	}
	public void setPayItcptYn(String payItcptYn) {
		this.payItcptYn = payItcptYn;
	}
	public String getPayItcptDt() {
		return payItcptDt;
	}
	public void setPayItcptDt(String payItcptDt) {
		this.payItcptDt = payItcptDt;
	}
	public String getPayItcptRegr() {
		return payItcptRegr;
	}
	public void setPayItcptRegr(String payItcptRegr) {
		this.payItcptRegr = payItcptRegr;
	}
}
