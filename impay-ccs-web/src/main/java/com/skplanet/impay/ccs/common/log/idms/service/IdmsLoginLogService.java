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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.framework.log.idms.IdmsLoginLog;

/**
 * IDMS 로그인/로그아웃 로그 Service
 * @author Sangjun, Park
 *
 */
@Service
public class IdmsLoginLogService {
	private static final Logger logger = LoggerFactory.getLogger(IdmsLoginLogService.class);
	
	/**
	 * 로그 생성 및 출력
	 * @param loginDateTime		로그인일시 (yyyyMMddHHmmss)
	 * @param userInfo			사용자정보
	 */
	public void printIdmsLog(String loginDateTime, CustomUserDetails userInfo){
		this.printIdmsLog(loginDateTime, "", userInfo);
	}
	
	/**
	 * 로그 생성 및 출력
	 * @param loginDateTime		로그인일시 (yyyyMMddHHmmss)
	 * @param logoutDateTime	로그아웃일시 (yyyyMMddHHmmss : Null일 경우, 공백처리)
	 * @param userInfo			사용자정보
	 */
	public void printIdmsLog(String loginDateTime, String logoutDateTime, CustomUserDetails userInfo){
		IdmsLoginLog log = new IdmsLoginLog(
				userInfo.getUserId(),
				userInfo.getClntIp(),
				loginDateTime,
				logoutDateTime
				);
		logger.info(log.toString());
	}

}
