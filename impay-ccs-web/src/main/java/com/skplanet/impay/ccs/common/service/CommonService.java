/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.impay.ccs.common.model.CommcCost;
import com.skplanet.impay.ccs.common.model.CommonSearch;
import com.skplanet.impay.ccs.common.model.DataChg;
import com.skplanet.impay.ccs.common.model.EntpModel;
import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.service.mapper.CommonMapper;

/**
 * 공통업무조회
 * @author jisu park
 *
 */
@Service
public class CommonService {
		
	private static Logger logger = LoggerFactory.getLogger(CommonService.class);
	
	@Autowired 
	private CommonMapper commonMapper;
	
	/**
	 * 이통사 원가 조회
	 * @return List<CommcCost>
	 */
	public List<CommcCost> getCommcCost() {		
		return commonMapper.selectCommcCost();
	}	
	
	/**
	 * 데이터 변경이력
	 * @param dataChg 데이터 변경이력 정보
	 * @return DataChg
	 */
	public DataChg getDataChg(DataChg dataChg){
		return commonMapper.selectDataChg(dataChg);
	}
	
	/**
	 * 데이터 변경이력리스트
	 * @param dataChg 데이터 변경이력 정보
	 * @return List<DataChg>
	 */
	public List<DataChg> getDataChgListByTblNm(DataChg dataChg){
		return commonMapper.selectDataChgListByTblNm(dataChg);
	}
	
	/**
	 * 데이터 변경이력(페이징)
	 * @param commonSearch  검색 파라미터
	 * @return Page<DataChg>
	 */
	public Page<DataChg> getDataChgPageList(CommonSearch model){		
		final List<DataChg> list = new ArrayList<DataChg>();
		
		commonMapper.selectDataChgPageList(model, new ResultHandler<DataChg>(){
			@Override
			public void handleResult(ResultContext<? extends DataChg> context) {
				DataChg obj = context.getResultObject();
				list.add(obj);
			}
		});
		
		return new Page<DataChg>(commonMapper.selectDataChgPageListCount(model), list);
	}
	
	/**
	 * 법인정보 조회
	 * @param entpId 법인ID
	 * @return EntpModel
	 */
	public EntpModel getEntpInfo(String entpId){
		return this.commonMapper.selectEntpInfo(entpId);
	}

	/**
	 * PG사/재판매사
	 * 모빌,다날,다우 
	 * 법인ID, 법인명 조회
	 * @return List<EntpModel>
	 */
	public List<EntpModel> getAdjEntp() {
		return this.commonMapper.selectAdjEntp();
	}
}