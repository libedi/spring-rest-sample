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
 * 이관 이메일 발송 Model
 * @author Sangjun, Park
 *
 */
public class TjurEmail extends ValidationMarker {
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String title;		// 제목
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String deptCd;		// 이관부서
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	@Pattern(groups = Create.class, regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
	private String emailAddr;	// 이메일주소
	private String rcptDt;		// 접수일자
	private String regr;		// 접수자
	private String rcptTm;		// 접수시간
	private String rcptNo;		// 접수번호
	private String payCndiNm;	// 결제조건
	private String commcClfNm;	// 이통사
	private String payAmt;		// 거래금액
	private String paySvcNm;	// 가맹점명
	private String godsNm;		// 상품명
	private String trdDt;		// 거래일시
	private String cnclDt;		// 취소일시
	private String avlCncl;		// 취소가능여부
	private String trdNo;		// 결제승인번호
	private String telNo;		// 고객센터
	private String cnslClfNm;	// 상담구분
	private String cnslEvntNm;	// 이벤트
	private String cnslTypNm;	// 상담유형
	private String rcptMthdNm;	// 접수유형
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String cnslCtnt;	// 상담내용
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String reqCtnt;		// 이관 요청내용
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDeptCd() {
		return deptCd;
	}
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	public String getRcptDt() {
		return rcptDt;
	}
	public void setRcptDt(String rcptDt) {
		this.rcptDt = rcptDt;
	}
	public String getRegr() {
		return regr;
	}
	public void setRegr(String regr) {
		this.regr = regr;
	}
	public String getRcptTm() {
		return rcptTm;
	}
	public void setRcptTm(String rcptTm) {
		this.rcptTm = rcptTm;
	}
	public String getRcptNo() {
		return rcptNo;
	}
	public void setRcptNo(String rcptNo) {
		this.rcptNo = rcptNo;
	}
	public String getPayCndiNm() {
		return payCndiNm;
	}
	public void setPayCndiNm(String payCndiNm) {
		this.payCndiNm = payCndiNm;
	}
	public String getCommcClfNm() {
		return commcClfNm;
	}
	public void setCommcClfNm(String commcClfNm) {
		this.commcClfNm = commcClfNm;
	}
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
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
	public String getTrdDt() {
		return trdDt;
	}
	public void setTrdDt(String trdDt) {
		this.trdDt = trdDt;
	}
	public String getCnclDt() {
		return cnclDt;
	}
	public void setCnclDt(String cnclDt) {
		this.cnclDt = cnclDt;
	}
	public String getAvlCncl() {
		return avlCncl;
	}
	public void setAvlCncl(String avlCncl) {
		this.avlCncl = avlCncl;
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
	public String getCnslClfNm() {
		return cnslClfNm;
	}
	public void setCnslClfNm(String cnslClfNm) {
		this.cnslClfNm = cnslClfNm;
	}
	public String getCnslEvntNm() {
		return cnslEvntNm;
	}
	public void setCnslEvntNm(String cnslEvntNm) {
		this.cnslEvntNm = cnslEvntNm;
	}
	public String getCnslTypNm() {
		return cnslTypNm;
	}
	public void setCnslTypNm(String cnslTypNm) {
		this.cnslTypNm = cnslTypNm;
	}
	public String getRcptMthdNm() {
		return rcptMthdNm;
	}
	public void setRcptMthdNm(String rcptMthdNm) {
		this.rcptMthdNm = rcptMthdNm;
	}
	public String getCnslCtnt() {
		return cnslCtnt;
	}
	public void setCnslCtnt(String cnslCtnt) {
		this.cnslCtnt = cnslCtnt;
	}
	public String getReqCtnt() {
		return reqCtnt;
	}
	public void setReqCtnt(String reqCtnt) {
		this.reqCtnt = reqCtnt;
	}
	
}
