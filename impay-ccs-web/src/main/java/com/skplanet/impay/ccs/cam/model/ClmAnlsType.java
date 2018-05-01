/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.cam.model;

import com.skplanet.impay.ccs.common.model.PageParam;

/**
 * 클레임 분석 - 유형별
 * @author Junehee, Jang
 *
 */
public class ClmAnlsType {
	
	private String stDate;			// 시작 일자
	private String endDate;			// 종료 일자
	
	private String rcptMthdCd;		// 접수방법코드
	private String rcptMthdNm;		// 접수방법명
	
	private String payCndiCd;		// 결제조건코드(일반, 회수, 자동, 직접, 배치)
	private String payCndiNm;		// 결제조건명
	
	private String cnslTypCd;		// 상담유형코드
	private String cnslTypNm;		// 상담유형코드명
	
	private String cnslClfUprCd;	// 상담 구분 ( 상담 구분(상위) 코드)
	private String cnslClfUprCdNm;	// 상담 구분 명
	
	private String commcClf;		// 이통사 구분
	private String commcClfNm;		// 이통사명
	
	private String mphnNo;			// 휴대폰 번호
	private String cpCd;			// 가맹점코드
	private String cpNm;			// 가맹점 명
	
	private String cnslEvntCd;		// 이벤트 (미인지과금, 미납가산금, 시스템오류, 기타)
	private String cnslEvntNm;		// 이벤트 

	private String regDt;			// 접수일자
	
	private int rcptCnt;			// 접수건수
	private int procYCnt;			// 처리건수
	private int procNCnt;			// 미처리건수
	
	private PageParam pageParam; 
	
	public PageParam getPageParam() {
		return pageParam;
	}
	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	}
	public String getStDate() {
		return stDate;
	}
	public void setStDate(String stDate) {
		this.stDate = stDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRcptMthdCd() {
		return rcptMthdCd;
	}
	public void setRcptMthdCd(String rcptMthdCd) {
		this.rcptMthdCd = rcptMthdCd;
	}
	public String getRcptMthdNm() {
		return rcptMthdNm;
	}
	public void setRcptMthdNm(String rcptMthdNm) {
		this.rcptMthdNm = rcptMthdNm;
	}
	public String getPayCndiCd() {
		return payCndiCd;
	}
	public void setPayCndiCd(String payCndiCd) {
		this.payCndiCd = payCndiCd;
	}
	public String getPayCndiNm() {
		return payCndiNm;
	}
	public void setPayCndiNm(String payCndiNm) {
		this.payCndiNm = payCndiNm;
	}
	public String getCnslTypCd() {
		return cnslTypCd;
	}
	public void setCnslTypCd(String cnslTypCd) {
		this.cnslTypCd = cnslTypCd;
	}
	public String getCnslTypNm() {
		return cnslTypNm;
	}
	public void setCnslTypNm(String cnslTypNm) {
		this.cnslTypNm = cnslTypNm;
	}
	public String getCnslClfUprCd() {
		return cnslClfUprCd;
	}
	public void setCnslClfUprCd(String cnslClfUprCd) {
		this.cnslClfUprCd = cnslClfUprCd;
	}
	public String getCnslClfUprCdNm() {
		return cnslClfUprCdNm;
	}
	public void setCnslClfUprCdNm(String cnslClfUprCdNm) {
		this.cnslClfUprCdNm = cnslClfUprCdNm;
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
	public String getCpCd() {
		return cpCd;
	}
	public void setCpCd(String cpCd) {
		this.cpCd = cpCd;
	}
	public String getCpNm() {
		return cpNm;
	}
	public void setCpNm(String cpNm) {
		this.cpNm = cpNm;
	}
	public String getCnslEvntCd() {
		return cnslEvntCd;
	}
	public void setCnslEvntCd(String cnslEvntCd) {
		this.cnslEvntCd = cnslEvntCd;
	}
	public String getCnslEvntNm() {
		return cnslEvntNm;
	}
	public void setCnslEvntNm(String cnslEvntNm) {
		this.cnslEvntNm = cnslEvntNm;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public int getRcptCnt() {
		return rcptCnt;
	}
	public void setRcptCnt(int rcptCnt) {
		this.rcptCnt = rcptCnt;
	}
	public int getProcYCnt() {
		return procYCnt;
	}
	public void setProcYCnt(int procYCnt) {
		this.procYCnt = procYCnt;
	}
	public int getProcNCnt() {
		return procNCnt;
	}
	public void setProcNCnt(int procNCnt) {
		this.procNCnt = procNCnt;
	}
}
