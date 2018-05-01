
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
 * 클레임 분석 - 건별 조회
 * @author Junehee, Jang
 *
 */
public class ClmAnlsCase {

	private int totalCnt;			// 총계
	
	private String stDate;			// 시작 일자
	private String endDate;			// 종료 일자
	
	private String rcptNo;			// 접수번호
	
	private String rcptMthdCd;		// 접수방법코드
	private String rcptMthdNm;		// 접수방법명
	
	private String payCndiCd;		// 결제조건코드(일반, 회수, 자동, 직접, 배치)
	private String payCndiNm;		// 결제조건명
	
	private String cnslTypCd;		// 상담유형코드
	private String cnslTypNm;		// 상담유형코드명
	
	private String cnslClfUprCd;	// 상담 구분 ( 상담 구분(상위) 코드)
	private String cnslClfUprCdNm;	// 상담 구분 명
	
	private String cnslEvntCd;		// 이벤트 (미인지과금, 미납가산금, 시스템오류, 기타)
	private String cnslEvntNm;		// 이벤트 
	
	private String procYn;			// 처리여부
	private String procNm;			// 처리 명
	private String procCtnt;		// 처리 내용
	private String procCtntYn;		// 처리 내용 여부 Y/N
	
	private String cnslYn;			// 상담 여부
	private String cnslCtnt;		// 상담 내용
	private String cnslCtntYn;		// 상담 내용 여부 Y/N
	
	private String custTypFlg;		// 고객유형 flg
	private String custTypFlgNm;		// 고객유형 flg명
	
	private String payMphnId;		// 결제폰ID
	private String mphnNo;			// 휴대폰 번호
	
	private String cpCd;			// 가맹점코드
	private String cpNm;			// 가맹점 명
	
	private String godsNm;			// 상품명
	
	private String commcClf;		// 이통사 구분
	private String commcClfNm;		// 이통사명
	
	private String regDt;			// 접수일자
	private String regr;			// 상담자
	
	private String tjurClfFlg;		// 이관 구분 
	private String tjurClfFlgNm;	// 이관 구분 명
	
	private PageParam pageParam;	
	
	
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
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
	public String getCustTypFlgNm() {
		return custTypFlgNm;
	}
	public void setCustTypFlgNm(String custTypFlgNm) {
		this.custTypFlgNm = custTypFlgNm;
	}
	public String getCustTypFlg() {
		return custTypFlg;
	}
	public void setCustTypFlg(String custTypFlg) {
		this.custTypFlg = custTypFlg;
	}
	public PageParam getPageParam() {
		return pageParam;
	}
	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	}
	public String getProcCtntYn() {
		return procCtntYn;
	}
	public void setProcCtntYn(String procCtntYn) {
		this.procCtntYn = procCtntYn;
	}
	public String getCnslCtntYn() {
		return cnslCtntYn;
	}
	public void setCnslCtntYn(String cnslCtntYn) {
		this.cnslCtntYn = cnslCtntYn;
	}
	public String getProcNm() {
		return procNm;
	}
	public void setProcNm(String procNm) {
		this.procNm = procNm;
	}
	public String getGodsNm() {
		return godsNm;
	}
	public void setGodsNm(String godsNm) {
		this.godsNm = godsNm;
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
	public String getProcCtnt() {
		return procCtnt;
	}
	public void setProcCtnt(String procCtnt) {
		this.procCtnt = procCtnt;
	}
	public String getCnslYn() {
		return cnslYn;
	}
	public void setCnslYn(String cnslYn) {
		this.cnslYn = cnslYn;
	}
	public String getCnslCtnt() {
		return cnslCtnt;
	}
	public void setCnslCtnt(String cnslCtnt) {
		this.cnslCtnt = cnslCtnt;
	}
	public String getRcptNo() {
		return rcptNo;
	}
	public void setRcptNo(String rcptNo) {
		this.rcptNo = rcptNo;
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
	public String getProcYn() {
		return procYn;
	}
	public void setProcYn(String procYn) {
		this.procYn = procYn;
	}
	public String getPayMphnId() {
		return payMphnId;
	}
	public void setPayMphnId(String payMphnId) {
		this.payMphnId = payMphnId;
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
	public String getTjurClfFlg() {
		return tjurClfFlg;
	}
	public void setTjurClfFlg(String tjurClfFlg) {
		this.tjurClfFlg = tjurClfFlg;
	}
	public String getTjurClfFlgNm() {
		return tjurClfFlgNm;
	}
	public void setTjurClfFlgNm(String tjurClfFlgNm) {
		this.tjurClfFlgNm = tjurClfFlgNm;
	}
}
