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
public class RmsDftNum {
	
	private int idx; //번호
	private String payrSeq = ""; //고객번호
	private String commcClf = ""; //이통사
	private String commcClfNm = ""; //이통사명
	private String mphnNo = ""; //휴대폰번호
	private String mnts3NpayCnt = ""; //3회차 미납횟수
	private String mnts6NpayCnt = ""; //6회차 미납횟수
	private String mnts12NpayCnt = ""; //12회차 미납횟수
	private String billAmt = ""; //청구금액
	private String rcptAmt = ""; //수납금액
	private String npayAmt = ""; //미납금액
	private String mnts = ""; //회차
	
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
	public String getMnts3NpayCnt() {
		return mnts3NpayCnt;
	}
	public void setMnts3NpayCnt(String mnts3NpayCnt) {
		this.mnts3NpayCnt = mnts3NpayCnt;
	}
	public String getMnts6NpayCnt() {
		return mnts6NpayCnt;
	}
	public void setMnts6NpayCnt(String mnts6NpayCnt) {
		this.mnts6NpayCnt = mnts6NpayCnt;
	}
	public String getMnts12NpayCnt() {
		return mnts12NpayCnt;
	}
	public void setMnts12NpayCnt(String mnts12NpayCnt) {
		this.mnts12NpayCnt = mnts12NpayCnt;
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
	public String getMnts() {
		return mnts;
	}
	public void setMnts(String mnts) {
		this.mnts = mnts;
	}
	@Override
	public String toString() {
		return "RmsDftNum [idx=" + idx + ", payrSeq=" + payrSeq + ", commcClf=" + commcClf + ", commcClfNm="
				+ commcClfNm + ", mphnNo=" + mphnNo + ", mnts3NpayCnt=" + mnts3NpayCnt + ", mnts6NpayCnt="
				+ mnts6NpayCnt + ", mnts12NpayCnt=" + mnts12NpayCnt + ", billAmt=" + billAmt + ", rcptAmt=" + rcptAmt
				+ ", npayAmt=" + npayAmt + ", mnts=" + mnts + "]";
	}
}
