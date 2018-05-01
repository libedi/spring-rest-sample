/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.sysm.service.mapper;

import java.util.List;

import com.skplanet.impay.ccs.sysm.model.SmsDocStrgMng;

/**
 * 시스템 관리
 * SMS 문서보관함 관리 Mapper
 * @author Junehee, Jang
 */
public interface SmsDocStrgMngMapper {
	// 목록 조회
	List<SmsDocStrgMng> getSmsDocList();
	// 목록 총 카운트
	int getSmsDocCount();
	// 특정 SMS 문구 조회
	SmsDocStrgMng getSmsWord(String charStrgNo);
	// SMS 문구 등록
	void insertSmsWord(SmsDocStrgMng smsDocStrgMng);
	// SMS 문구 수정
	void updateSmsWord(SmsDocStrgMng smsDocStrgMng);
	// SMS 문구 삭제
	void deleteSmsWord(SmsDocStrgMng smsDocStrgMng);
}
