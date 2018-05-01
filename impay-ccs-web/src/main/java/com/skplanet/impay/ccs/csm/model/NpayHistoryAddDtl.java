/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.csm.model;

/**
 * 미납이력 및 가산금 부과내역
 * @author Junehee, Jang
 *
 */
public class NpayHistoryAddDtl {
	private String billYm; 		// 청구 년월
	private String trdDd; 		//거래 일시
	private String payMphnId; 	// 결제폰 ID
	private String mphnNo;		// 핸드폰 번호
	private String acPayAmt; 	// 거래금액
	private String rcptAmt; 	// 수납금액
	private String npayAmt; 	// 미납금액
	private String billAmt; 	// 청구금액
	private String trdStat; 	// 상태 (취소 컬럼의 Y/N에 따른 Y = 취소 , N = 정상)

	private String cpNm; 		// 가맹점 명
	private String godsNm; 		//상품명
	
	public String getMphnNo() {
		return mphnNo;
	}
	public void setMphnNo(String mphnNo) {
		this.mphnNo = mphnNo;
	}
	public String getBillYm() {
		return billYm;
	}
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}
	public String getTrdDd() {
		return trdDd;
	}
	public void setTrdDd(String trdDd) {
		this.trdDd = trdDd;
	}
	public String getPayMphnId() {
		return payMphnId;
	}
	public void setPayMphnId(String payMphnId) {
		this.payMphnId = payMphnId;
	}
	public String getAcPayAmt() {
		return acPayAmt;
	}
	public void setAcPayAmt(String acPayAmt) {
		this.acPayAmt = acPayAmt;
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
	public String getBillAmt() {
		return billAmt;
	}
	public void setBillAmt(String billAmt) {
		this.billAmt = billAmt;
	}
	public String getTrdStat() {
		return trdStat;
	}
	public void setTrdStat(String trdStat) {
		this.trdStat = trdStat;
	}
	public String getCpNm() {
		return cpNm;
	}
	public void setCpNm(String cpNm) {
		this.cpNm = cpNm;
	}
	public String getGodsNm() {
		return godsNm;
	}
	public void setGodsNm(String godsNm) {
		this.godsNm = godsNm;
	}
}
