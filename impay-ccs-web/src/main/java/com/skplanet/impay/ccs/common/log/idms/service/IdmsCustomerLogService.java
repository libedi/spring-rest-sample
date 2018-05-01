/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.log.idms.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.skplanet.impay.ccs.common.util.CommonUtil;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.framework.log.idms.IdmsCustomerDataAccessLog;

/**
 * IDMS 고객정보 로그 Service
 * @author Sangjun, Park
 *
 */
@Service
public class IdmsCustomerLogService {
	private static final Logger logger = LoggerFactory.getLogger(IdmsCustomerLogService.class);
	
	/** 최대 고객리스트 건수 */
	public static final int MAX_CUSTOMER_COUNT = 999;
	
	/**
	 * 로그 생성 및 출력
	 * @param customerName	고객명
	 * @param systemViewId	App 화면 ID
	 * @param functionCode	기능코드
	 * @param accessCount	고객리스트건수 (1~999)
	 * @param userInfo		사용자정보
	 */
	public void printIdmsLog(String customerName, String systemViewId, String functionCode, int accessCount, CustomUserDetails userInfo){
		this.printIdmsLog("", customerName, systemViewId, functionCode, accessCount, userInfo);
	}
	
	/**
	 * 로그 생성 및 출력
	 * @param customerId 	고객ID (Null일 경우, 공백처리)
	 * @param customerName	고객명
	 * @param systemViewId	App 화면 ID
	 * @param functionCode	기능코드
	 * @param accessCount	고객리스트건수 (1~999)
	 * @param userInfo		사용자정보
	 */
	public void printIdmsLog(String customerId, String customerName, String systemViewId, String functionCode, int accessCount, CustomUserDetails userInfo){
		IdmsCustomerDataAccessLog log = new IdmsCustomerDataAccessLog(
				new Date(),
				CommonUtil.getLocalServerIp(),
				userInfo.getUserId(),
				userInfo.getClntIp(),
				customerId,
				customerName,
				systemViewId,
				functionCode,
				accessCount
				);
		
		logger.info(log.toString());
	}

}
