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
 * 클레임 분석 - 이관접수건 별
 * @author Junehee, Jang
 *
 */
public class ClmAnlsTjurRcpt {
	
	private int idx;				// 순번
	
	private String stDate;			// 시작 일자
	private String endDate;			// 종료 일자
	
	private String regDt;			// 접수일자
	
	private String tjurClfFlg;		// 이관접수 구분
	
	private String deptCd;			// 부서코드
	private String deptNm;			// 부서코드명

	private String cpCd;			// 가맹점코드
	private String paySvcNm;		// 가맹점 명
	
	private String cnslTypCd;		// 상담유형코드
	private String cnslTypNm;		// 상담유형코드명
	
	private String cnslClfUprCd;	// 상담 구분 ( 상담 구분(상위) 코드)
	private String cnslClfUprCdNm;	// 상담 구분 명
	
	private String cnslEvntCd;		// 이벤트 (미인지과금, 미납가산금, 시스템오류, 기타)
	private String cnslEvntNm;		// 이벤트 
	
	private String commcClf;		// 이통사구분
	private String commcClfNm;		// 이통사명
	
	private int rcptCnt;			// 접수건수
	private int procYCnt;			// 처리건수
	private int procNCnt;			// 미처리건수
	
	private PageParam pageParam;

	
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getTjurClfFlg() {
		return tjurClfFlg;
	}

	public void setTjurClfFlg(String tjurClfFlg) {
		this.tjurClfFlg = tjurClfFlg;
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

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getDeptCd() {
		return deptCd;
	}

	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public String getCpCd() {
		return cpCd;
	}

	public void setCpCd(String cpCd) {
		this.cpCd = cpCd;
	}

	public String getPaySvcNm() {
		return paySvcNm;
	}

	public void setPaySvcNm(String paySvcNm) {
		this.paySvcNm = paySvcNm;
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

	public PageParam getPageParam() {
		return pageParam;
	}

	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	} 
}
