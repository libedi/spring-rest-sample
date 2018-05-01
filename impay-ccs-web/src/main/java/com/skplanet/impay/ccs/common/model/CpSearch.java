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

/**
 * 가맹점 조회 Model
 * @author Sangjun, Park
 *
 */
public class CpSearch {
	private String searchClf;		// 조회구분 : 법인, 가맹점, 그룹
	private String cpStatus;		// 가맹점상태
	private String searchWord;		// 검색어
	
	private PageParam pageParam;
	
	public String getSearchClf() {
		return searchClf;
	}
	public void setSearchClf(String searchClf) {
		this.searchClf = searchClf;
	}
	public String getCpStatus() {
		return cpStatus;
	}
	public void setCpStatus(String cpStatus) {
		this.cpStatus = cpStatus;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public PageParam getPageParam() {
		return pageParam;
	}
	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	}
	
}
