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

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.impay.ccs.framework.model.ValidationMarker;

/**
 * 결제폰 정보
 * @author Junehee,Jang
 *
 */
public class PayMphnInfo extends ValidationMarker {
	@NotNull(groups = {Update.class, Retrieve.class})
	@NotEmpty(groups = {Update.class, Retrieve.class})
	private String payMphnId; 	// 결제폰 ID
	private String commcClf;	// 이통사 구분(SKT, KTF, LGU, CJH)
	private String commcClfNm;	// 이통사 구분(SKT, KTF, LGU, CJH)
	private String commcCustId;	// 이통사 고객 ID
	private String payrNm;		// 이용자(결제자)명
	private String mphnNo;		// 휴대폰 번호(암호화)
	private String brthYmd;		// 생년월일(암호화)
	private String brthYy;		// 출생년도 (미정의:9999)
	private String gndrFlg;		// 성별 플래그 (남성:M, 여성:F, 미정의:Z)
	private String payrSeq;		// 이용자 순번
	private String tmnalInfo;	// 단말기 정보
	private String openDd;		// 개통 일자
	private int lmt;			// 한도
	private String lmtAplyYn;	// 한도 적용 여부(Y,N)
	private String grd;			// 등급
	private String npayYn;		// 미납 여부(Y,N)
	private String pwdSetYn;	// 비밀번호 설정 여부(Y,N)
	private String usimOtpUseYn;// USIM OTP 사용 여부(Y,N)
	private String smrtBillUseYn;// SKT 스마트 청구 사용 여부(Y,N)
	private String payrNmChgCnt;// 이용자명 변경 횟수(LGU)
	private String noMvYn;		// 번호이동 여부(Y,N)
	private String atPayPrhbYn;	// 자동과금 금지 여부
	private String payItcptYn;	// 결제 차단 여부 (차단:Y, 차단안함:N)
	private String payItcptDt;	// 결제 차단 일시
	private String payItcptRegr;// 결제 차단 등록자
	private String relsYn;		// 해지 여부(Y,N)
	private String regDt;		// 등록 일시
	private String lastChgDt;	// 최종 변경 일시
	private String brthYmdFull;	// 생년월일 (yyyy.MM.dd)
	
	public String getPayMphnId() {
		return payMphnId;
	}
	public void setPayMphnId(String payMphnId) {
		this.payMphnId = payMphnId;
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
	public String getCommcCustId() {
		return commcCustId;
	}
	public void setCommcCustId(String commcCustId) {
		this.commcCustId = commcCustId;
	}
	public String getPayrNm() {
		return payrNm;
	}
	public void setPayrNm(String payrNm) {
		this.payrNm = payrNm;
	}
	public String getMphnNo() {
		return mphnNo;
	}
	public void setMphnNo(String mphnNo) {
		this.mphnNo = mphnNo;
	}
	public String getBrthYmd() {
		return brthYmd;
	}
	public void setBrthYmd(String brthYmd) {
		this.brthYmd = brthYmd;
	}
	public String getBrthYy() {
		return brthYy;
	}
	public void setBrthYy(String brthYy) {
		this.brthYy = brthYy;
	}
	public String getGndrFlg() {
		return gndrFlg;
	}
	public void setGndrFlg(String gndrFlg) {
		this.gndrFlg = gndrFlg;
	}
	public String getPayrSeq() {
		return payrSeq;
	}
	public void setPayrSeq(String payrSeq) {
		this.payrSeq = payrSeq;
	}
	public String getTmnalInfo() {
		return tmnalInfo;
	}
	public void setTmnalInfo(String tmnalInfo) {
		this.tmnalInfo = tmnalInfo;
	}
	public String getOpenDd() {
		return openDd;
	}
	public void setOpenDd(String openDd) {
		this.openDd = openDd;
	}
	public int getLmt() {
		return lmt;
	}
	public void setLmt(int lmt) {
		this.lmt = lmt;
	}
	public String getLmtAplyYn() {
		return lmtAplyYn;
	}
	public void setLmtAplyYn(String lmtAplyYn) {
		this.lmtAplyYn = lmtAplyYn;
	}
	public String getGrd() {
		return grd;
	}
	public void setGrd(String grd) {
		this.grd = grd;
	}
	public String getNpayYn() {
		return npayYn;
	}
	public void setNpayYn(String npayYn) {
		this.npayYn = npayYn;
	}
	public String getPwdSetYn() {
		return pwdSetYn;
	}
	public void setPwdSetYn(String pwdSetYn) {
		this.pwdSetYn = pwdSetYn;
	}
	public String getUsimOtpUseYn() {
		return usimOtpUseYn;
	}
	public void setUsimOtpUseYn(String usimOtpUseYn) {
		this.usimOtpUseYn = usimOtpUseYn;
	}
	public String getSmrtBillUseYn() {
		return smrtBillUseYn;
	}
	public void setSmrtBillUseYn(String smrtBillUseYn) {
		this.smrtBillUseYn = smrtBillUseYn;
	}
	public String getPayrNmChgCnt() {
		return payrNmChgCnt;
	}
	public void setPayrNmChgCnt(String payrNmChgCnt) {
		this.payrNmChgCnt = payrNmChgCnt;
	}
	public String getNoMvYn() {
		return noMvYn;
	}
	public void setNoMvYn(String noMvYn) {
		this.noMvYn = noMvYn;
	}
	public String getAtPayPrhbYn() {
		return atPayPrhbYn;
	}
	public void setAtPayPrhbYn(String atPayPrhbYn) {
		this.atPayPrhbYn = atPayPrhbYn;
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
	public String getRelsYn() {
		return relsYn;
	}
	public void setRelsYn(String relsYn) {
		this.relsYn = relsYn;
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
	public String getBrthYmdFull() {
		return brthYmdFull;
	}
	public void setBrthYmdFull(String brthYmdFull) {
		this.brthYmdFull = brthYmdFull;
	}
}
