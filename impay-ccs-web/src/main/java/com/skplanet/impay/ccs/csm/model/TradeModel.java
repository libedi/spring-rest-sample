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
 * 거래내역 Model
 * @author Sangjun, Park
 *
 */
public class TradeModel {
	private String rnum;		// 순서
	private String trdTypCd;	// 결제조건코드
	private String trdTypNm;	// 결제조건
	private String trdDt;		// 거래일시
	private String trdDd;		// 거래일자
	private String reqDt;		// 요청일시
	private String reqDd;		// 요청일자
	private String cnclDt;		// 취소일시
	private String cnclDd;		// 취소일자
	private String avlCncl;		// 취소가능여부
	private String brthYmd;		// 생년월일
	private String commcClf;	// 이통사 코드
	private String commcClfNm;	// 이통사명
	private String payrSeq;		// 고객번호
	private String mphnNo;		// 휴대폰번호
	private long payAmt;		// 거래금액
	private long saleAmt;		// 할인금액
	private long acPayAmt;		// 청구금액
	private String paySvcNm;	// 가맹점명
	private String godsNm;		// 상품명
	private String smplPayYn;	// 고객상태
	private String smplPayNm;	// 고객상태명
	private String payStat;		// 결제상태
	private String trdNo;		// 결제승인(구매)번호
	private String telNo;		// 고객센터 전화번호
	private String paySiteNm;	// 결제사이트명
	private String entpId;		// 법인ID
	private String cpCd;		// 가맹점코드
	private String inRsltCd;	// 내부오류코드
	private String inRsltCdNm;	// 내부오류코드명
	private String extrRsltCd;	// 외부오류코드
	private String extrRsltCdNm;// 외부오류코드명
	private String transCd;		// 전문코드
	private String transCdNm;	// 전문코드명
	private String cnclYn;		// 취소여부
	private String inRsltMsg;	// 내부오류메시지
	private String extrRsltMsg;	// 외부오류메시지
	private String payMphnId;	// 결제폰정보ID
	private String authtiClfFlg;// 인증구분 (S:SMS, A:ARS, O:U-OTP, N:인증없음/자동2회차)
	private String authtiClfFlgNm;// 인증구분명
	
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getTrdTypCd() {
		return trdTypCd;
	}
	public void setTrdTypCd(String trdTypCd) {
		this.trdTypCd = trdTypCd;
	}
	public String getTrdTypNm() {
		return trdTypNm;
	}
	public void setTrdTypNm(String trdTypNm) {
		this.trdTypNm = trdTypNm;
	}
	public String getTrdDt() {
		return trdDt;
	}
	public void setTrdDt(String trdDt) {
		this.trdDt = trdDt;
	}
	public String getTrdDd() {
		return trdDd;
	}
	public void setTrdDd(String trdDd) {
		this.trdDd = trdDd;
	}
	public String getReqDt() {
		return reqDt;
	}
	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}
	public String getReqDd() {
		return reqDd;
	}
	public void setReqDd(String reqDd) {
		this.reqDd = reqDd;
	}
	public String getCnclDt() {
		return cnclDt;
	}
	public void setCnclDt(String cnclDt) {
		this.cnclDt = cnclDt;
	}
	public String getCnclDd() {
		return cnclDd;
	}
	public void setCnclDd(String cnclDd) {
		this.cnclDd = cnclDd;
	}
	public String getAvlCncl() {
		return avlCncl;
	}
	public void setAvlCncl(String avlCncl) {
		this.avlCncl = avlCncl;
	}
	public String getBrthYmd() {
		return brthYmd;
	}
	public void setBrthYmd(String brthYmd) {
		this.brthYmd = brthYmd;
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
	public String getPayrSeq() {
		return payrSeq;
	}
	public void setPayrSeq(String payrSeq) {
		this.payrSeq = payrSeq;
	}
	public String getMphnNo() {
		return mphnNo;
	}
	public void setMphnNo(String mphnNo) {
		this.mphnNo = mphnNo;
	}
	public long getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(long payAmt) {
		this.payAmt = payAmt;
	}
	public long getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(long saleAmt) {
		this.saleAmt = saleAmt;
	}
	public long getAcPayAmt() {
		return acPayAmt;
	}
	public void setAcPayAmt(long acPayAmt) {
		this.acPayAmt = acPayAmt;
	}
	public String getPaySvcNm() {
		return paySvcNm;
	}
	public void setPaySvcNm(String paySvcNm) {
		this.paySvcNm = paySvcNm;
	}
	public String getGodsNm() {
		return godsNm;
	}
	public void setGodsNm(String godsNm) {
		this.godsNm = godsNm;
	}
	public String getSmplPayYn() {
		return smplPayYn;
	}
	public void setSmplPayYn(String smplPayYn) {
		this.smplPayYn = smplPayYn;
	}
	public String getSmplPayNm() {
		return smplPayNm;
	}
	public void setSmplPayNm(String smplPayNm) {
		this.smplPayNm = smplPayNm;
	}
	public String getPayStat() {
		return payStat;
	}
	public void setPayStat(String payStat) {
		this.payStat = payStat;
	}
	public String getTrdNo() {
		return trdNo;
	}
	public void setTrdNo(String trdNo) {
		this.trdNo = trdNo;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getPaySiteNm() {
		return paySiteNm;
	}
	public void setPaySiteNm(String paySiteNm) {
		this.paySiteNm = paySiteNm;
	}
	public String getEntpId() {
		return entpId;
	}
	public void setEntpId(String entpId) {
		this.entpId = entpId;
	}
	public String getCpCd() {
		return cpCd;
	}
	public void setCpCd(String cpCd) {
		this.cpCd = cpCd;
	}
	public String getInRsltCd() {
		return inRsltCd;
	}
	public void setInRsltCd(String inRsltCd) {
		this.inRsltCd = inRsltCd;
	}
	public String getInRsltCdNm() {
		return inRsltCdNm;
	}
	public void setInRsltCdNm(String inRsltCdNm) {
		this.inRsltCdNm = inRsltCdNm;
	}
	public String getExtrRsltCd() {
		return extrRsltCd;
	}
	public void setExtrRsltCd(String extrRsltCd) {
		this.extrRsltCd = extrRsltCd;
	}
	public String getExtrRsltCdNm() {
		return extrRsltCdNm;
	}
	public void setExtrRsltCdNm(String extrRsltCdNm) {
		this.extrRsltCdNm = extrRsltCdNm;
	}
	public String getTransCd() {
		return transCd;
	}
	public void setTransCd(String transCd) {
		this.transCd = transCd;
	}
	public String getTransCdNm() {
		return transCdNm;
	}
	public void setTransCdNm(String transCdNm) {
		this.transCdNm = transCdNm;
	}
	public String getCnclYn() {
		return cnclYn;
	}
	public void setCnclYn(String cnclYn) {
		this.cnclYn = cnclYn;
	}
	public String getInRsltMsg() {
		return inRsltMsg;
	}
	public void setInRsltMsg(String inRsltMsg) {
		this.inRsltMsg = inRsltMsg;
	}
	public String getExtrRsltMsg() {
		return extrRsltMsg;
	}
	public void setExtrRsltMsg(String extrRsltMsg) {
		this.extrRsltMsg = extrRsltMsg;
	}
	public String getPayMphnId() {
		return payMphnId;
	}
	public void setPayMphnId(String payMphnId) {
		this.payMphnId = payMphnId;
	}
	public String getAuthtiClfFlg() {
		return authtiClfFlg;
	}
	public void setAuthtiClfFlg(String authtiClfFlg) {
		this.authtiClfFlg = authtiClfFlg;
	}
	public String getAuthtiClfFlgNm() {
		return authtiClfFlgNm;
	}
	public void setAuthtiClfFlgNm(String authtiClfFlgNm) {
		this.authtiClfFlgNm = authtiClfFlgNm;
	}
	
}
