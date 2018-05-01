/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.rpm.service.mapper;
import org.apache.ibatis.session.ResultHandler;

import com.skplanet.impay.ccs.csm.model.CnslSearch;
import com.skplanet.impay.ccs.rpm.model.PayBackRcpt;
import com.skplanet.impay.ccs.rpm.model.PayBackSearch;

/**
 * CCS 접수/처리현황관리 
 * - 환불접수 Mapper
 * @author Junehee, Jang
 *
 */
public interface PayBackRcptMapper {
	/**
	 * 환불 접수 신규 등록
	 * @author Junehee, Jang
	 * @param payBackRcpt
	 */
	void insertPayBackRcpt(PayBackRcpt payBackRcpt);
	/**
	 * 환불 접수 목록 카운트
	 * @param payBackSearch
	 * @return
	 */
	int selectPayBackRcptCount(PayBackSearch payBackSearch);
	/**
	 * 환불 접수 목록 조회
	 * @param payBackSearch
	 * @param resultHandler 
	 * @return
	 */
	void selectPayBackRcptList(PayBackSearch payBackSearch, ResultHandler<PayBackRcpt> resultHandler);
	/**
	 * 환불 접수 상세 조회
	 * @param pybkRcptNo
	 * @return
	 */
	PayBackRcpt selectPayBackRcptInfo(String pybkRcptNo);
	/**
	 * 환불 접수 
	 * CHECK 된 ROW 삭제여부 Y 수정  
	 * @param cnslSearch
	 */
	void deletePayBackRcpt(CnslSearch cnslSearch);
}
