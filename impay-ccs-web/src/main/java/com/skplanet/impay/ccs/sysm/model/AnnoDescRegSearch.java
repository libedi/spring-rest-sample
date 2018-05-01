/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.sysm.model;

import com.skplanet.impay.ccs.common.model.PageParam;

/**
 * 고객센터 공지사항 Search Model
 * @author Sangjun, Park
 *
 */
public class AnnoDescRegSearch {
	private Long clctBordSeq;		// 고객센터 게시판 순서
	private String stDate;			// 시작날짜
	private String endDate;			// 종료날짜
	private String srchText;		// 검색어
	private String bordClfCd;		// 게시판 유형 코드
	
	private PageParam pageParam;
	
	public Long getClctBordSeq() {
		return clctBordSeq;
	}
	public void setClctBordSeq(Long clctBordSeq) {
		this.clctBordSeq = clctBordSeq;
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
	public String getSrchText() {
		return srchText;
	}
	public void setSrchText(String srchText) {
		this.srchText = srchText;
	}
	public String getBordClfCd() {
		return bordClfCd;
	}
	public void setBordClfCd(String bordClfCd) {
		this.bordClfCd = bordClfCd;
	}
	public PageParam getPageParam() {
		return pageParam;
	}
	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	}
	
}
