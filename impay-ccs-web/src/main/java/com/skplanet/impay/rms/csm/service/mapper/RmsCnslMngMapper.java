/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.rms.csm.service.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.skplanet.impay.ccs.csm.model.CnslSearch;
import com.skplanet.impay.rms.csm.model.RmsChrgRcptAcc;
import com.skplanet.impay.rms.csm.model.RmsChrgRcptMon;
import com.skplanet.impay.rms.csm.model.RmsCpBLInq;
import com.skplanet.impay.rms.csm.model.RmsCustChg;
import com.skplanet.impay.rms.csm.model.RmsDftNum;
import com.skplanet.impay.rms.csm.model.RmsFraudReliefReg;
import com.skplanet.impay.rms.csm.model.RmsRmChangeHis;
import com.skplanet.impay.rms.csm.model.RmsRmBlkReliefReg;

/**
 * 상담 관리 (RMS) Mapper
 * @author Sunghee Park
 *
 */
public interface RmsCnslMngMapper {
	
	/**
	 * 현재시간 조회
	 * @return
	 */
	Date selectSysdate();

	/**
	 * 청구/수납(누적) 목록 조회
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectChrgRcptAccList(CnslSearch cnslSearch, ResultHandler<RmsChrgRcptAcc> resultHandler);
	
	/**
	 * 청구/수납(월별) 목록 조회
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectChrgRcptMonList(CnslSearch cnslSearch, ResultHandler<RmsChrgRcptMon> resultHandler);
	
	/**
	 * 수납 회차별 미납횟수 목록 조회
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectNpayCntList(CnslSearch cnslSearch, ResultHandler<RmsDftNum> resultHandler);
	
	/**
	 * 미납금액 상세 조회
	 * @param params
	 * @return
	 */
	RmsDftNum selectNpayAmtDetail(RmsDftNum params);
	
	/**
	 * RM 차단/해제 목록 조회
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectRmBlkReliefList(CnslSearch cnslSearch, ResultHandler<RmsRmBlkReliefReg> resultHandler);
	
	/**
	 * 현재 진행중인 RM 해제 목록 조회
	 * @param param
	 * @return
	 */
	List<RmsRmBlkReliefReg> selectCurrentRmReliefList(RmsRmBlkReliefReg param);
	
	/**
	 * RM 해제 등록
	 * @param param
	 * @return
	 */
	int insertRmBlkReliefCust(RmsRmBlkReliefReg param);
	
	/**
	 * RM 차단
	 * @param param
	 * @return
	 */
	int updateRmBlkReliefCust(RmsRmBlkReliefReg param);
	
	/**
	 * 불량고객 차단 목록 조회
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectFraudBlkList(CnslSearch cnslSearch, ResultHandler<RmsFraudReliefReg> resultHandler);
	
	/**
	 * 불량고객 차단중인 건 조회
	 * @param param
	 * @return
	 */
	RmsFraudReliefReg selectCurrentFraudBlk(RmsFraudReliefReg param);
	
	/**
	 * 불량고객 해제
	 * @param param
	 * @return
	 */
	int updateFraudCust(RmsFraudReliefReg param);
	
	/**
	 * 가맹점B/L 목록 조회
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectCpBLInqList(CnslSearch cnslSearch, ResultHandler<RmsCpBLInq> resultHandler);
	
	/**
	 * RM 변경이력 목록 조회
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectRmChangeHisList(CnslSearch cnslSearch, ResultHandler<RmsRmChangeHis> resultHandler);
	
	/**
	 * RM 변경이력 등록
	 * @param param
	 * @return
	 */
	int insertCustChg(RmsCustChg param);
}
