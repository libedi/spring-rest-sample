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

import java.util.List;

public class ClaimAnalysisMng {
	private List<ChartModel> chartModel;				// 차트 모델
	private List<ClmAnlsCase> clmAnlsCaseList;			// 건별 조회 모델
	private List<ClmAnlsType> clmAnlsTypeList;			// 유형별 조회 모델
	private List<ClmAnlsTjurRcpt> clmAnlsTjurRcptList;	// 이관접수별 조회
	
	private int total;
	
	public List<ClmAnlsTjurRcpt> getClmAnlsTjurRcptList() {
		return clmAnlsTjurRcptList;
	}
	public void setClmAnlsTjurRcptList(List<ClmAnlsTjurRcpt> clmAnlsTjurRcptList) {
		this.clmAnlsTjurRcptList = clmAnlsTjurRcptList;
	}
	public List<ClmAnlsType> getClmAnlsTypeList() {
		return clmAnlsTypeList;
	}
	public void setClmAnlsTypeList(List<ClmAnlsType> clmAnlsTypeList) {
		this.clmAnlsTypeList = clmAnlsTypeList;
	}
	public List<ChartModel> getChartModel() {
		return chartModel;
	}
	public void setChartModel(List<ChartModel> chartModel) {
		this.chartModel = chartModel;
	}
	public List<ClmAnlsCase> getClmAnlsCaseList() {
		return clmAnlsCaseList;
	}
	public void setClmAnlsCaseList(List<ClmAnlsCase> clmAnlsCaseSearch) {
		this.clmAnlsCaseList = clmAnlsCaseSearch;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
