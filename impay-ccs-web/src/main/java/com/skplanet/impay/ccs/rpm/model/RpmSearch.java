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
 * 접수/처리 현황 관리 공통 Search Model
 * @author Junehee, Jang
 *
 */
public class RpmSearch {
	private String searchClf 	= "";	//조회구분 
	private String searchCode	= "";	//코드
	private String searchName	= "";	//이름
	private String searchPhone	= ""; 	//휴대폰 번호
	
	private String stDate		= "";	//조회 기간 : 시작 날짜
	private String endDate		= "";	//조회 기간 : 종료 날짜 
	
	
	/*private String statCd		= "";	//가맹점 상태 
	private String cpCd 		= "";	//가맹점 코드
	private String stDd 		= "";	//시작일자
	private String searchWord	= "";	// 검색어*/
	
	private String tjurClfFlg	= "";	// 이관 구분
	private String procStat		= "";	// 처리상태
	private String pgId			= "";	// 가맹점
	
	private PageParam pageParam;
	
	
	public String getSearchClf() {
		return searchClf;
	}
	public void setSearchClf(String searchClf) {
		this.searchClf = searchClf;
	}
	public String getSearchCode() {
		return searchCode;
	}
	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getSearchPhone() {
		return searchPhone;
	}
	public void setSearchPhone(String searchPhone) {
		this.searchPhone = searchPhone;
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
	public String getTjurClfFlg() {
		return tjurClfFlg;
	}
	public void setTjurClfFlg(String tjurClfFlg) {
		this.tjurClfFlg = tjurClfFlg;
	}
	public String getProcStat() {
		return procStat;
	}
	public void setProcStat(String procStat) {
		this.procStat = procStat;
	}
	public String getPgId() {
		return pgId;
	}
	public void setPgId(String pgId) {
		this.pgId = pgId;
	}
	public PageParam getPageParam() {
		return pageParam;
	}
	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	}
}
