/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.sysm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhncorp.lucy.security.xss.XssPreventer;
import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.sysm.model.SmsDocStrgMng;
import com.skplanet.impay.ccs.sysm.service.mapper.SmsDocStrgMngMapper;

/**
 * 시스템 관리
 * SMS 문서보관함 관리 Service
 * @author Junehee, Jang
 */
@Service
public class SmsDocStrgMngService {
	
	@Autowired
	private SmsDocStrgMngMapper smsDocStrgMngMapper;

	/**
	 * SMS 문서보관함 조회
	 * @return Page<SmsDocStrgMng>
	 */
	public Page<SmsDocStrgMng> selectSmsDocList() {
		List<SmsDocStrgMng> list = smsDocStrgMngMapper.getSmsDocList();
		int count = smsDocStrgMngMapper.getSmsDocCount();
		return new Page<SmsDocStrgMng>(count, list);
	}
	/**
	 * 특정 SMS 문구 조회
	 * @param charStrgNo 문자 보관 번호
	 * @return SmsDocStrgMng
	 */
	public SmsDocStrgMng selectSmsWord(String charStrgNo) {
		SmsDocStrgMng smsDocStrgMng = smsDocStrgMngMapper.getSmsWord(charStrgNo); 
		smsDocStrgMng.setCharCtnt(XssPreventer.unescape(smsDocStrgMng.getCharCtnt()));
		return smsDocStrgMng;
	}
	/**
	 * SMS 문구 등록
	 * @param smsDocStrgMng SMS 문서 보관함 관리 정보
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	public RestResult<String> addSmsWord(SmsDocStrgMng smsDocStrgMng, CustomUserDetails userInfo) {
		RestResult<String> result = new RestResult<String>();

		smsDocStrgMng.setRegr(userInfo.getUserNm());
		smsDocStrgMngMapper.insertSmsWord(smsDocStrgMng);
		result.setSuccess(true);
		result.setMessage("success");
		
		return result;
	}
	/**
	 * SMS 문구 수정
	 * @param smsDocStrgMng SMS 문서 보관함 관리 정보
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	public RestResult<String> updateSmsWord(SmsDocStrgMng smsDocStrgMng, CustomUserDetails userInfo) {
		RestResult<String> result = new RestResult<String>();
		
		smsDocStrgMng.setRegr(userInfo.getUserNm());
		smsDocStrgMngMapper.updateSmsWord(smsDocStrgMng);
		result.setSuccess(true);
		result.setMessage("success");
		return result;
	}
	/**
	 * SMS 문구 삭제
	 * @param smsDocStrgMng SMS 문서 보관함 관리 정보
	 * @return RestResult<String>
	 */
	public RestResult<String> deleteSmsWord(SmsDocStrgMng smsDocStrgMng) {
		RestResult<String> result = new RestResult<String>();
		smsDocStrgMngMapper.deleteSmsWord(smsDocStrgMng);
		result.setSuccess(true);
		result.setMessage("success");
		return result;
	}
}
