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

import java.util.List;

import javax.validation.constraints.Pattern;

import com.skplanet.impay.ccs.common.model.PageParam;

/**
 * 상담관리 조회 Model
 * @author Sangjun, Park
 *
 */
public class CnslSearch {
	private String selectDate;	// 조회기간 선택(월별 : M, 기간별 : T)
	private String strtMon;		// 월별 달
	private String strtDt;		// 기간별 시작날짜
	private String endDt;		// 기간별 종료날짜
	private String cpCd;		// 가맹점 코드
	private String custMphnNo;	// 상담자 휴대번호
	private String rcptNo;		// 접수번호
	private String[] trdNos = {};	// 거래번호 리스트
	private String payMphnId;	// 상담자 결제폰 ID
	private String chgListYn;	// 상담내용 변경내역 조회여부
	private String payrSeq;		// 이용자 순번
	@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
	private String email;		// 이용자 이메일주소
	private List<String> payMphnIdList;	// 결제폰ID 리스트
	private String ccsEmployee;	// 콜센터 직원
	private String hash;		// 화면이동할 hash 지점
	
	private PageParam pageParam;
	
	public CnslSearch() {
		chgListYn = "N";
	}
	
	public String getCcsEmployee() {
		return ccsEmployee;
	}

	public void setCcsEmployee(String ccsEmployee) {
		this.ccsEmployee = ccsEmployee;
	}

	public String getSelectDate() {
		return selectDate;
	}
	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}
	public String getStrtMon() {
		return strtMon;
	}
	public void setStrtMon(String strtMon) {
		this.strtMon = strtMon;
	}
	public String getStrtDt() {
		return strtDt;
	}
	public void setStrtDt(String strtDt) {
		this.strtDt = strtDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getCpCd() {
		return cpCd;
	}
	public void setCpCd(String cpCd) {
		this.cpCd = cpCd;
	}
	public String getCustMphnNo() {
		return custMphnNo;
	}
	public void setCustMphnNo(String custMphnNo) {
		this.custMphnNo = custMphnNo;
	}
	public String getRcptNo() {
		return rcptNo;
	}
	public void setRcptNo(String rcptNo) {
		this.rcptNo = rcptNo;
	}
	public String[] getTrdNos() {
		return (String[]) trdNos.clone();
	}
	public void setTrdNos(String[] trdNos) {
		this.trdNos = (String[]) trdNos.clone();
	}
	public PageParam getPageParam() {
		return pageParam;
	}
	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	}
	public String getPayMphnId() {
		return payMphnId;
	}
	public void setPayMphnId(String payMphnId) {
		this.payMphnId = payMphnId;
	}
	public String getChgListYn() {
		return chgListYn;
	}
	public void setChgListYn(String chgListYn) {
		this.chgListYn = chgListYn;
	}
	public String getPayrSeq() {
		return payrSeq;
	}
	public void setPayrSeq(String payrSeq) {
		this.payrSeq = payrSeq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getPayMphnIdList() {
		return payMphnIdList;
	}
	public void setPayMphnIdList(List<String> payMphnIdList) {
		this.payMphnIdList = payMphnIdList;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
}
