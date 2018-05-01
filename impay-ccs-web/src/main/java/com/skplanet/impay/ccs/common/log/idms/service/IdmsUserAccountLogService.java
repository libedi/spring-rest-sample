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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.framework.log.idms.IdmsGrantedAdminUserLog;


/**
 * IDMS 사용자 계정 로그 Service
 * @author Sangjun, Park
 *
 */
@Service
public class IdmsUserAccountLogService {
	private static final Logger logger = LoggerFactory.getLogger(IdmsUserAccountLogService.class);
	
	/** 계정구분 : 관리자 */
	public static final String ACCOUNT_TYPE_ADMIN = "1";
	/** 계정구분 : 제휴사 관리자 */
	public static final String ACCOUNT_TYPE_CP_ADMIN = "2";
	/** 계정구분 : 임의발급계정 */
	public static final String ACCOUNT_TYPE_RANDOM = "3";
	
	/**
	 * 로그 생성 및 출력
	 * @param accountType	App사용자ID 계정구분
	 * @param regDateTime	App 로그인ID 생성일시
	 * @param fromDateTime	App 로그인ID 사용시작일시 (yyyyMMddHHmmss)
	 * @param toDateTime	App 로그인ID 사용종료일시 (yyyyMMddHHmmss)
	 * @param userInfo		사용자정보
	 */
	public void printIdmsLog(String accountType, String regDateTime, String fromDateTime, String toDateTime, CustomUserDetails userInfo){
		this.printIdmsLog("", accountType, regDateTime, fromDateTime, toDateTime, userInfo);
	}
	
	/**
	 * 로그 생성 및 출력
	 * @param companyName	App사용자 소속사 (Null일 경우, 공백처리)
	 * @param accountType	App사용자ID 계정구분
	 * @param regDateTime	App 로그인ID 생성일시
	 * @param fromDateTime	App 로그인ID 사용시작일시 (yyyyMMddHHmmss)
	 * @param toDateTime	App 로그인ID 사용종료일시 (yyyyMMddHHmmss)
	 * @param userInfo		사용자정보
	 */
	public void printIdmsLog(String companyName, String accountType, String regDateTime, String fromDateTime, String toDateTime, CustomUserDetails userInfo){
		IdmsGrantedAdminUserLog log = new IdmsGrantedAdminUserLog(
				userInfo.getUserId(),
				userInfo.getUserNm(),
				companyName,
				(StringUtils.equals("Y", userInfo.getUseYn()) ? "1" : (userInfo.getPwdErrCnt() < 5 ? "3" : "2")),
				accountType,
				regDateTime,
				fromDateTime,
				toDateTime
				);
		logger.info(log.toString());
	}

}
