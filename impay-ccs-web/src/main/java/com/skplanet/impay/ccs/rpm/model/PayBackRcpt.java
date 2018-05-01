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
 * 미납가산금 환불 내역
 * @author Junehee, Jang
 *
 */
public class PayBackRcpt {
	private String rnum; // 
	private String pybkRcptNo;	// 환불 접수 번호
	private String pybkTypCd;	// 환불 유형 코드
	private String pybkProc;	// 환불 처리자 ID
	private String procDt;		// 처리 일시
	private String trdDd;		// 거래 일시	
	private String pybkReqDd;	// 환불 요청 일자
	private String payrNm;		// 고객명
	private String payMphnId;	// 결제폰 ID
	private String mphnNo;		// 휴대폰 번호
	private String payAmt;		// 결제 금액
	private String CpCd;		// 가맹점 코드
	private String CpNm;		// 가맹점 명
	private String bankCd;		// 은행코드
	private String bankNm;		// 은행명
	private String dpstrNm;		// 예금주명
	private String acctNo;		// 계좌번호
	private String commcClf;	// 이통사 구분
	private String procClfCd;	// 처리구분 코드
	private String delYn;		// 삭제 여부
	private String delDt;		// 삭제 일시
	private String regDt;		// 등록일시
	private String regr;		// 등록자
	private String lastChgDt;	// 최종변경 일시
	private String lastChgr;	// 최종변경자
	
	
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getMphnNo() {
		return mphnNo;
	}
	public void setMphnNo(String mphnNo) {
		this.mphnNo = mphnNo;
	}
	public String getPybkRcptNo() {
		return pybkRcptNo;
	}
	public void setPybkRcptNo(String pybkRcptNo) {
		this.pybkRcptNo = pybkRcptNo;
	}
	public String getPybkTypCd() {
		return pybkTypCd;
	}
	public void setPybkTypCd(String pybkTypCd) {
		this.pybkTypCd = pybkTypCd;
	}
	public String getPybkProc() {
		return pybkProc;
	}
	public void setPybkProc(String pybkProc) {
		this.pybkProc = pybkProc;
	}
	public String getProcDt() {
		return procDt;
	}
	public void setProcDt(String procDt) {
		this.procDt = procDt;
	}
	public String getTrdDd() {
		return trdDd;
	}
	public void setTrdDd(String trdDd) {
		this.trdDd = trdDd;
	}
	public String getPybkReqDd() {
		return pybkReqDd;
	}
	public void setPybkReqDd(String pybkReqDd) {
		this.pybkReqDd = pybkReqDd;
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
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getCpCd() {
		return CpCd;
	}
	public void setCpCd(String cpCd) {
		CpCd = cpCd;
	}
	public String getCpNm() {
		return CpNm;
	}
	public void setCpNm(String cpNm) {
		CpNm = cpNm;
	}
	public String getBankCd() {
		return bankCd;
	}
	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}
	public String getBankNm() {
		return bankNm;
	}
	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}
	public String getDpstrNm() {
		return dpstrNm;
	}
	public void setDpstrNm(String dpstrNm) {
		this.dpstrNm = dpstrNm;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getCommcClf() {
		return commcClf;
	}
	public void setCommcClf(String commcClf) {
		this.commcClf = commcClf;
	}
	public String getProcClfCd() {
		return procClfCd;
	}
	public void setProcClfCd(String procClfCd) {
		this.procClfCd = procClfCd;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getDelDt() {
		return delDt;
	}
	public void setDelDt(String delDt) {
		this.delDt = delDt;
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
