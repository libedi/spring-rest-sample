/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.framework.security.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.skplanet.impay.ccs.common.service.MenuService;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.framework.security.service.mapper.CustomUserDetailsMapper;

/**
 * CustomUserDetailsService
 * @author 
 *
 */
public class CustomUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired CustomUserDetailsMapper userDetailsMapper;	
	
	@Autowired MenuService menuService;
	
	@Autowired HttpServletRequest request;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		CustomUserDetails userDetails = userDetailsMapper.selectUserByUsername(username);
		
        if(userDetails == null) {
            throw new UsernameNotFoundException("");
        }
        menuService.getMemu(userDetails);
        userDetails.setSysId("test");
        userDetails.setLoginId(userDetails.getUserId());
        userDetails.setClntIp(request.getRemoteAddr());
        
        logger.info("############################ IDMS 요청 정보 ############################");
        logger.info("sysId : " + userDetails.getSysId() );
        logger.info("loginId : " + userDetails.getLoginId() );
        logger.info("clntIp : " + userDetails.getClntIp() );

        return userDetails;
	}
	
	/**
	 * 사용자 비밀번호 오류횟수 수정
	 * 
	 * @param username
	 */
	public void updateUserPwdErrCnt(String username) {
		// 사용자의 조회
		CustomUserDetails userDetails = userDetailsMapper.selectUserByUsername(username);
		if(userDetails == null) {
            throw new UsernameNotFoundException("user not found");
        }
		
		int pwdErrCnt = userDetails.getPwdErrCnt();
		
		if (pwdErrCnt < 4) {
			// 비밀번호 오류횠수 증가 +1
			userDetailsMapper.updateUserPwdErrCnt(username);
		} else {
			// 비밀번호 오류횟수 증가 + 1 & Id 정지
			userDetailsMapper.updateUserPwdErrCntAndIdStop(username);
		}
	}
	
	/**
	 * 사용자 비밀번호 오류횟수 초기화
	 * 
	 * @param username
	 */
	public void updateUserPwdErrCntInit(String username) {
		userDetailsMapper.updateUserPwdErrCntInit(username);
	}
}