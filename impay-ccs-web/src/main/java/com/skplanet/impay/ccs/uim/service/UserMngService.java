/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.uim.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.impay.ccs.common.model.CntcInfo;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.MessageByLocaleServiceImpl;
import com.skplanet.impay.ccs.common.service.mapper.UserMapper;
import com.skplanet.impay.ccs.common.util.CommonUtil;
import com.skplanet.impay.ccs.common.util.StringUtil;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.uim.model.UserMng;
import com.skplanet.impay.ccs.uim.model.UserMngPwd;
import com.skplanet.impay.ccs.uim.service.mapper.UserMngMapper;

/**
 * 사용자 정보 관리 Service
 * @author Sangjun, Park
 *
 */
@Service
public class UserMngService {
	private final static Logger logger = LoggerFactory.getLogger(UserMngService.class);
	
	@Autowired
	private UserMngMapper userMngMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MessageByLocaleServiceImpl message;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 로그인 사용자 정보 조회
	 * @param userInfo 사용자 정보
	 * @return UserMng
	 */
	public UserMng getUserMngInfo(CustomUserDetails userInfo) {
		CntcInfo cntcInfo = this.userMapper.selectCntcInfo(userInfo.getCntcSeq());
		if(cntcInfo != null){
			cntcInfo.setMphnNo(StringUtils.join(CommonUtil.telToArray(cntcInfo.getMphnNo()), "-"));
		}
		UserMng userMng = new UserMng(userInfo.getUserSeq(), userInfo.getUserId());
		userMng.setUserNm(userInfo.getUserNm());
		userMng.setCntcInfo(cntcInfo);
		return userMng;
	}
	
	/**
	 * 사용자 정보 수정
	 * @param userMng 사용자 관리 정보
	 * @return UserMng
	 */
	public UserMng updateUserMngInfo(UserMng userMng, CustomUserDetails userInfo) {
		// 세션에서 사용자 정보 조회
		userMng.getCntcInfo().setLastChgr(userInfo.getUserSeq());
		userMng.getCntcInfo().setMphnNo(StringUtils.replace(userMng.getCntcInfo().getMphnNo(), "-", ""));
		// 사용자 연락처 정보 수정
		this.userMapper.updateCntcInfo(userMng.getCntcInfo());
		return this.getUserMngInfo(userInfo);
	}
	
	/**
	 * 사용자 비밀번호 변경
	 * @param userMngPwd 비밀번호 관리 정보
	 * @return RestResult<Integer>
	 */
	@Transactional
	public RestResult<Integer> updatePwd(UserMngPwd userMngPwd){
		RestResult<Integer> result = new RestResult<Integer>();
		
		// 비밀번호 미변경
		if(StringUtils.equals(userMngPwd.getPwd(), userMngPwd.getNewPwd())){
			logger.debug(this.message.getMessage("uim.update.alert.password.nochange"));
			result.setMessage(this.message.getMessage("uim.update.alert.password.nochange"));
			result.setSuccess(false);
			return result;
		}
		// 새 비밀번호 동일여부
		if(!StringUtils.equals(userMngPwd.getNewPwd(), userMngPwd.getNewPwd2())){
			logger.debug(this.message.getMessage("uim.update.alert.password.confirm"));
			result.setMessage(this.message.getMessage("uim.update.alert.password.confirm"));
			result.setSuccess(false);
			return result;
		}
		// 비밀번호 검증
		if(StringUtil.validationPassword(userMngPwd.getNewPwd())){
			UserMng userInfo = this.userMngMapper.selectUserInfo(userMngPwd.getUserSeq());
			// 기존 비밀번화와 동일여부 체크
			if (this.passwordEncoder.matches(userMngPwd.getPwd(), userInfo.getPwd())) {
				// 신규 비밀번호 암호화
				userMngPwd.setPwd(this.passwordEncoder.encode(userMngPwd.getNewPwd()));
				userMngPwd.setLastChgr(userInfo.getUserSeq());
				result.setSuccess(true);
				result.setResult(this.userMngMapper.updatePwd(userMngPwd));
			} else {
				logger.debug(this.message.getMessage("uim.update.alert.password.wrong"));
				result.setMessage(this.message.getMessage("uim.update.alert.password.wrong"));
				result.setSuccess(false);
			}
		} else {
			logger.debug(this.message.getMessage("uim.update.alert.password.pattern"));
			result.setMessage(this.message.getMessage("uim.update.alert.password.pattern"));
			result.setSuccess(false);
			return result;
		}
		
		return result;
	}

}
