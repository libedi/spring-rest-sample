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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.impay.ccs.framework.model.ValidationMarker;

/**
 * 결제차단 등록 Model
 * @author Sangjun, Park
 *
 */
public class PayItcptModel extends ValidationMarker {
	@NotNull(groups = Create.class)
	private String payMphnId;			// 결제폰ID
	
	@NotNull(groups = Update.class)
	private Long itcptRegSeq;			// 차단등록순번
	
	@NotNull(groups = Create.class)
	private String payrSeq;				// 이용자순번
	
	@Pattern(regexp = "\\b\\d{3}?\\d{3,4}?\\d{4}\\b")
	private String mphnNo;				// 휴대폰 번호
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String itcptClfFlg;			// 차단기준 (고객번호 : U, 휴대폰번호 : P) 
	private String itcptClfFlgNm;		// 차단기준명
	
	@NotNull(groups = {Create.class,Update.class})
	@NotEmpty(groups = {Create.class,Update.class})
	private String procClfFlg;			// 결제차단여부 (차단 : R, 해지 : U)
	private String procClfFlgNm;		// 결제차단여부명
	
	@NotNull(groups = {Create.class,Update.class})
	@NotEmpty(groups = {Create.class,Update.class})
	private String procRsn;				// 차단,해제 사유
	private String regDt;				// 결제차단이력 등록일자
	private String regr;				// 결제차단이력 입력자ID
	private String regrNm;				// 결제차단이력 입력자명
	private String lastChgDt;			// 결제차단이력 수정일자
	private String lastChgr;			// 결제차단이력 수정자ID
	private String lastChgrNm;			// 결제차단이력 수정자명
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String itcptReqrClfFlg;		// 차단요청자 구분 플래그(본인 : I, 가족 : F, 기타 : E)
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String itcptReqrNm;			// 차단요청자명
	
	private String rnum;				// 번호
	private String updateYn;			// 수정여부
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String brthYmd;				// 생년월일
	private String payrNm;				// 사용자명
	private String commcClf;			// 이통사 구분코드
	private String itcptRelsRsn;		// 차단해제사유
	
	public String getPayMphnId() {
		return payMphnId;
	}
	public void setPayMphnId(String payMphnId) {
		this.payMphnId = payMphnId;
	}
	public Long getItcptRegSeq() {
		return itcptRegSeq;
	}
	public void setItcptRegSeq(Long itcptRegSeq) {
		this.itcptRegSeq = itcptRegSeq;
	}
	public String getPayrSeq() {
		return payrSeq;
	}
	public void setPayrSeq(String payrSeq) {
		this.payrSeq = payrSeq;
	}
	public String getItcptClfFlg() {
		return itcptClfFlg;
	}
	public void setItcptClfFlg(String itcptClfFlg) {
		this.itcptClfFlg = itcptClfFlg;
	}
	public String getItcptClfFlgNm() {
		return itcptClfFlgNm;
	}
	public void setItcptClfFlgNm(String itcptClfFlgNm) {
		this.itcptClfFlgNm = itcptClfFlgNm;
	}
	public String getProcClfFlg() {
		return procClfFlg;
	}
	public void setProcClfFlg(String procClfFlg) {
		this.procClfFlg = procClfFlg;
	}
	public String getProcClfFlgNm() {
		return procClfFlgNm;
	}
	public void setProcClfFlgNm(String procClfFlgNm) {
		this.procClfFlgNm = procClfFlgNm;
	}
	public String getProcRsn() {
		return procRsn;
	}
	public void setProcRsn(String procRsn) {
		this.procRsn = procRsn;
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
	public String getRegrNm() {
		return regrNm;
	}
	public void setRegrNm(String regrNm) {
		this.regrNm = regrNm;
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
	public String getLastChgrNm() {
		return lastChgrNm;
	}
	public void setLastChgrNm(String lastChgrNm) {
		this.lastChgrNm = lastChgrNm;
	}
	public String getMphnNo() {
		return mphnNo;
	}
	public void setMphnNo(String mphnNo) {
		this.mphnNo = mphnNo;
	}
	public String getItcptReqrClfFlg() {
		return itcptReqrClfFlg;
	}
	public void setItcptReqrClfFlg(String itcptReqrClfFlg) {
		this.itcptReqrClfFlg = itcptReqrClfFlg;
	}
	public String getItcptReqrNm() {
		return itcptReqrNm;
	}
	public void setItcptReqrNm(String itcptReqrNm) {
		this.itcptReqrNm = itcptReqrNm;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getUpdateYn() {
		return updateYn;
	}
	public void setUpdateYn(String updateYn) {
		this.updateYn = updateYn;
	}
	public String getBrthYmd() {
		return brthYmd;
	}
	public void setBrthYmd(String brthYmd) {
		this.brthYmd = brthYmd;
	}
	public String getPayrNm() {
		return payrNm;
	}
	public void setPayrNm(String payrNm) {
		this.payrNm = payrNm;
	}
	public String getCommcClf() {
		return commcClf;
	}
	public void setCommcClf(String commcClf) {
		this.commcClf = commcClf;
	}
	public String getItcptRelsRsn() {
		return itcptRelsRsn;
	}
	public void setItcptRelsRsn(String itcptRelsRsn) {
		this.itcptRelsRsn = itcptRelsRsn;
	}
}
