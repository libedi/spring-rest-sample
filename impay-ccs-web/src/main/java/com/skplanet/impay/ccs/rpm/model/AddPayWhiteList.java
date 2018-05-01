/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.rpm.model;

/**
 * 미납가산금 W/L 관리
 * @author Junehee, Jang
 *
 */
public class AddPayWhiteList {
	private String rnum;
	private String wlRegNo;  // WL 등록 번호
	private String payrNm;	 // 이용자(고객)명
	private String payMphnId;// 결제폰 ID
	private String regClfCd; // 등록 구분 코드
	private String regRsn;	 // 등록 사유
	private String delYn;	 // 삭제 여부
	private String delRsn;	 // 삭제 사유
	private String delDt;    // 삭제 일시
	private String delProc;	 // 삭제 처리자
	private String regDt;	 // 등록 일시
	private String regr;	 // 등록자
	private String lastChgDt;// 최종 변경 일시
	private String lastChgr; // 최종 변경자
	
	private String mphnNo;	 // 휴대폰 번호
	
	public String getMphnNo() {
		return mphnNo;
	}
	public void setMphnNo(String mphnNo) {
		this.mphnNo = mphnNo;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getWlRegNo() {
		return wlRegNo;
	}
	public void setWlRegNo(String wlRegNo) {
		this.wlRegNo = wlRegNo;
	}
	public String getPayrNm() {
		return payrNm;
	}
	public void setPayrNm(String payrNm) {
		this.payrNm = payrNm;
	}
	public String getPayMphnId() {
		return payMphnId;
	}
	public void setPayMphnId(String payMphnId) {
		this.payMphnId = payMphnId;
	}
	public String getRegClfCd() {
		return regClfCd;
	}
	public void setRegClfCd(String regClfCd) {
		this.regClfCd = regClfCd;
	}
	public String getRegRsn() {
		return regRsn;
	}
	public void setRegRsn(String regRsn) {
		this.regRsn = regRsn;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getDelRsn() {
		return delRsn;
	}
	public void setDelRsn(String delRsn) {
		this.delRsn = delRsn;
	}
	public String getDelDt() {
		return delDt;
	}
	public void setDelDt(String delDt) {
		this.delDt = delDt;
	}
	public String getDelProc() {
		return delProc;
	}
	public void setDelProc(String delProc) {
		this.delProc = delProc;
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
	public String getLastChgDt() {
		return lastChgDt;
	}
	public void setLastChgDt(String lastChgDt) {
		this.lastChgDt = lastChgDt;
	}
	public String getLastChgr() {
		return lastChgr;
	}
	public void setLastChgr(String lastChgr) {
		this.lastChgr = lastChgr;
	}
}
