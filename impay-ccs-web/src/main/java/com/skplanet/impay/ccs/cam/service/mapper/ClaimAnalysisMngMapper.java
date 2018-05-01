/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.cam.service.mapper;


import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.skplanet.impay.ccs.cam.model.ChartModel;
import com.skplanet.impay.ccs.cam.model.ClmAnlsCase;
import com.skplanet.impay.ccs.cam.model.ClmAnlsTjurRcpt;
import com.skplanet.impay.ccs.cam.model.ClmAnlsType;


/**
 * 클레임 분석관리 Mapper
 * @author Jang
 *
 */
public interface ClaimAnalysisMngMapper {
	/**
	 *  TAB-1 
	 */
	//건별 조회 목록 	
	void selectClmAnslCaseList(ClmAnlsCase claimAnalysis, ResultHandler<ClmAnlsCase> resultHandler);
	//건별 조회 차트
	List<ChartModel> selectClmAnlsCaseChart(ClmAnlsCase claimAnalysis);
	//건별 목록 엑셀다운
	List<ClmAnlsCase> selectClmAnslCaseListExcelDown(ClmAnlsCase claimAnalysis);
	
	/**
	 *  TAB-2 
	 */
	//유형별 조회 목록 카운트
	int selectClmAnslTypeCount(ClmAnlsType clmAnlsType);
	//유형별 조회 목록
	void selectClmAnslTypeList(ClmAnlsType clmAnlsType, ResultHandler<ClmAnlsType> resultHandler);
	//유형별 조회 차트
	List<ChartModel> selectClmAnlsTypeChart(ClmAnlsType clmAnlsType);
	//유형별 목록 엑셀다운
	List<ClmAnlsType> selectClmAnslTypeListExcelDown(ClmAnlsType clmAnlsType);
	
	/**
	 * TAB-3
	 */
	//이관접수별 조회 목록 카운트
	int selectClmAnslTjurCount(ClmAnlsTjurRcpt clmAnlsTjur);
	//이관접수별 조회 목록
	void selectClmAnslTjurList(ClmAnlsTjurRcpt clmAnlsTjur, ResultHandler<ClmAnlsTjurRcpt> resultHandler);
	//이관접수별 조회 차트
	List<ChartModel> selectClmAnlsTjurChart(ClmAnlsTjurRcpt clmAnlsTjur);
	//이관접수별 엑셀 다운
	List<ClmAnlsTjurRcpt> selectClmAnslTjurListExcelDown(ClmAnlsTjurRcpt clmAnlsTjur);

}
