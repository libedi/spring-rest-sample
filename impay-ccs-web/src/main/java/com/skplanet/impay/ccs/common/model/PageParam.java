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
 * 페이징 파라미터 Model
 * @author Sangjun, Park
 *
 */
public class PageParam {
	
	/** 선택 페이지   */
	private int pageIndex = 0;
	
	/** 가져올 row 수 */
	private int rowCount = 10;
	
	/** 정렬할 컬럼 */
	private String sortName;
	
	/** 정렬순서    */
	private String sortOrder;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if(pageIndex == 0) {
			pageIndex=1;
		}
		this.pageIndex = pageIndex;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	public int getStartRow() {
		return (pageIndex - 1) * rowCount+1;
	}
	
	public int getEndRow() {
		return pageIndex * rowCount;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}	
}