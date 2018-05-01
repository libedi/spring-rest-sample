/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.uim.service.mapper;

import com.skplanet.impay.ccs.uim.model.UserMng;
import com.skplanet.impay.ccs.uim.model.UserMngPwd;

/**
 * 사용자 정보 관리 Mapper interface
 * @author Sangjun, Park
 *
 */
public interface UserMngMapper {
	/**
	 * 사용자 비밀번호 변경
	 * @param userMngPwd
	 * @return Integer
	 */
	Integer updatePwd(UserMngPwd userMngPwd);
	
	/**
	 * 사용자명 조회
	 * @param userSeq
	 * @return String
	 */
	String selectUsername(String userSeq);
	
	/**
	 * 사용자 정보 조회
	 * @param userSeq
	 * @return UserMng
	 */
	UserMng selectUserInfo(String userSeq);
}
