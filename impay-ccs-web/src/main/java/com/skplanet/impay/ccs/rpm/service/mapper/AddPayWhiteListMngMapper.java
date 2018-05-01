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

import com.skplanet.impay.ccs.rpm.model.AddPayWhiteList;
import com.skplanet.impay.ccs.rpm.model.RpmSearch;

/**
 * CCS 접수/처리현황관리 
 * - 가산금 W/L 관리 Mapper
 * @author Junehee, Jang
 *
 */
public interface AddPayWhiteListMngMapper {

	// 가산금 W/L 목록 카운트
	int selectAddPayWhiteCount(RpmSearch rpmSearch);
	// 가산금 W/L 목록 조회
	void selectAddPayWhiteList(RpmSearch rpmSearch, ResultHandler<AddPayWhiteList> resultHandler);
	// 가산금 W/L 상세 조회
	AddPayWhiteList selectAddPayWhiteListInfo(String wlRegNo);
	// 가산금 W/L 등록
	void insertAddPayWhiteList(AddPayWhiteList addPayWhiteList);
	// 가산금 W/L 삭제 사유 등록
	void updateAddPayDelWhiteList(AddPayWhiteList addPayWhiteList);
	
	// 가산금 W/L 데이터 확인
	int selectAddPayCheckData(AddPayWhiteList addPayWhiteList);
	

}
