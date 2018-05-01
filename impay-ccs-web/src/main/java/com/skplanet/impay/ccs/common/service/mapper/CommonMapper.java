/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.service.mapper;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.skplanet.impay.ccs.common.model.CommcCost;
import com.skplanet.impay.ccs.common.model.CommonSearch;
import com.skplanet.impay.ccs.common.model.DataChg;
import com.skplanet.impay.ccs.common.model.EntpModel;

/**
 * 공통업무 Mapper
 * @author jisu park
 *
 */
public interface CommonMapper {
	/** 이통사 원가 */
	List<CommcCost> selectCommcCost(); //이통사 원가조회
	
	/** 데이터 변경이력 */
	DataChg selectDataChg(DataChg dataChg); //데이터 변경이력
	List<DataChg> selectDataChgListByTblNm(DataChg dataChg); //데이터 변경이력
	int selectDataChgPageListCount(CommonSearch commonSearch); //데이터 변경이력(페이징)건수
	void selectDataChgPageList(CommonSearch commonSearch, ResultHandler<DataChg> resultHandler); //데이터 변경이력(페이징)

	/**
	 * 법인정보 조회
	 * @param entpId
	 * @return
	 */
	EntpModel selectEntpInfo(String entpId);
	
	/**
	 * PG사/재판매사
	 * 모빌,다날,다우 
	 * 법인ID, 법인명 조회
	 * @return
	 */
	List<EntpModel> selectAdjEntp();
}
