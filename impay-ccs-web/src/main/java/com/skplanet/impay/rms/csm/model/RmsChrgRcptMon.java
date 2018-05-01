/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.rms.csm.model;

/**
 * 상담 관리 (RMS) > 청구/수납(누적) Model
 * @author Sunghee Park
 *
 */
public class RmsChrgRcptMon {
	
	private int idx; //번호
	private String payrSeq = ""; //고객번호
	private String payYm = ""; //미납년월
	private String commcClf = ""; //이통사
	private String commcClfNm = ""; //이통사명
	private String mphnNo = ""; //휴대폰번호
	private String billAmt = ""; //청구금액
	private String rcptAmt = ""; //수납금액
	private String npayAmt = ""; //미납금액
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getPayrSeq() {
		return payrSeq;
	}
	public void setPayrSeq(String payrSeq) {
		this.payrSeq = payrSeq;
	}
	public String getPayYm() {
		return payYm;
	}
	public void setPayYm(String payYm) {
		this.payYm = payYm;
	}
	public String getCommcClf() {
		return commcClf;
	}
	public void setCommcClf(String commcClf) {
		this.commcClf = commcClf;
	}
	public String getCommcClfNm() {
		return commcClfNm;
	}
	public void setCommcClfNm(String commcClfNm) {
		this.commcClfNm = commcClfNm;
	}
	public String getMphnNo() {
		return mphnNo;
	}
	public void setMphnNo(String mphnNo) {
		this.mphnNo = mphnNo;
	}
	public String getBillAmt() {
		return billAmt;
	}
	public void setBillAmt(String billAmt) {
		this.billAmt = billAmt;
	}
	public String getRcptAmt() {
		return rcptAmt;
	}
	public void setRcptAmt(String rcptAmt) {
		this.rcptAmt = rcptAmt;
	}
	public String getNpayAmt() {
		return npayAmt;
	}
	public void setNpayAmt(String npayAmt) {
		this.npayAmt = npayAmt;
	}
	@Override
	public String toString() {
		return "RmsChrgRcptMon [idx=" + idx + ", payrSeq=" + payrSeq + ", payYm=" + payYm + ", commcClf=" + commcClf
				+ ", commcClfNm=" + commcClfNm + ", mphnNo=" + mphnNo + ", billAmt=" + billAmt + ", rcptAmt=" + rcptAmt
				+ ", npayAmt=" + npayAmt + "]";
	}
}
