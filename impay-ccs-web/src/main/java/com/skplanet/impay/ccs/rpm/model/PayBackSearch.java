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

import com.skplanet.impay.ccs.common.model.PageParam;

/**
 * 환불 접수 Search Model
 * @author Junehee, Jang
 *
 */
public class PayBackSearch {
	private String rcptStDt;	// 접수일 시작일
	private String rcptEndDt;	// 접수일 종료일
	
	private String reqStDt;		// 요청일 시작일
	private String reqEndDt;		// 요청일 시작일
	
	private String phoneNo; 	// 휴대폰 번호
	private String commcClf;	// 이통사 구분
	private String pybkTypCd;	// 환불 구분
	private String name;		// 이름
	
	private PageParam pageParam;
	
	public String getRcptStDt() {
		return rcptStDt;
	}
	public void setRcptStDt(String rcptStDt) {
		this.rcptStDt = rcptStDt;
	}
	public String getRcptEndDt() {
		return rcptEndDt;
	}
	public void setRcptEndDt(String rcptEndDt) {
		this.rcptEndDt = rcptEndDt;
	}
	public String getReqStDt() {
		return reqStDt;
	}
	public void setReqStDt(String reqStDt) {
		this.reqStDt = reqStDt;
	}
	public String getReqEndDt() {
		return reqEndDt;
	}
	public void setReqEndDt(String reqEndDt) {
		this.reqEndDt = reqEndDt;
	}
	public PageParam getPageParam() {
		return pageParam;
	}
	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getCommcClf() {
		return commcClf;
	}
	public void setCommcClf(String commcClf) {
		this.commcClf = commcClf;
	}
	public String getPybkTypCd() {
		return pybkTypCd;
	}
	public void setPybkTypCd(String pybkTypCd) {
		this.pybkTypCd = pybkTypCd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}




