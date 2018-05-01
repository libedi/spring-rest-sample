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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.skplanet.impay.ccs.common.model.CntcInfo;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.MessageByLocaleServiceImpl;
import com.skplanet.impay.ccs.common.service.mapper.UserMapper;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.uim.model.UserMng;
import com.skplanet.impay.ccs.uim.model.UserMngPwd;
import com.skplanet.impay.ccs.uim.service.mapper.UserMngMapper;

/**
 * 사용자 정보관리 Service Test
 * @author Sangjun, Park
 *
 */
public class UserMngServiceTest {
	private UserMngService userMngService;
	private UserMngMapper userMngMapper;
	private UserMapper userMapper;
	private MessageByLocaleServiceImpl message;
	private PasswordEncoder passwordEncoder;
	
	@Before
	public void setUp() throws Exception{
		userMngService = new UserMngService();
		
		userMngMapper = mock(UserMngMapper.class);
		userMapper = mock(UserMapper.class);
		message = mock(MessageByLocaleServiceImpl.class);
		passwordEncoder = new BCryptPasswordEncoder();
		
		ReflectionTestUtils.setField(userMngService, "userMngMapper", userMngMapper);
		ReflectionTestUtils.setField(userMngService, "userMapper", userMapper);
		ReflectionTestUtils.setField(userMngService, "message", message);
		ReflectionTestUtils.setField(userMngService, "passwordEncoder", passwordEncoder);
	}
	
	@Test
	public void testMockCreation() throws Exception{
		assertNotNull(userMngService);
		assertNotNull(userMngMapper);
		assertNotNull(userMapper);
		assertNotNull(message);
		assertNotNull(passwordEncoder);
	}
	
	/**
	 * 사용자 정보 조회 test
	 * @throws Exception
	 */
	@Test
	public void testGetUserMngInfo() throws Exception{
		CustomUserDetails param = new CustomUserDetails();
		param.setUserId("test");
		param.setUserSeq("1");
		param.setUserNm("홍길동");
		
		CntcInfo retObj = new CntcInfo();
		retObj.setMphnNo("01012345678");
		when(userMapper.selectCntcInfo(param.getCntcSeq())).thenReturn(retObj);
		
		UserMng expected = new UserMng();
		expected.setUserId("test");
		expected.setUserSeq("1");
		expected.setUserNm("홍길동");
		expected.setCntcInfo(retObj);
		
		UserMng actual = userMngService.getUserMngInfo(param);
		
		assertEquals(expected.getCntcInfo().getMphnNo(), actual.getCntcInfo().getMphnNo());
	}
	
	/**
	 * 사용자 정보 수정 test
	 * @throws Exception
	 */
	@Test
	public void testUpdateUserMngInfo() throws Exception{
		CustomUserDetails userInfo = new CustomUserDetails();
		userInfo.setUserId("test");
		userInfo.setUserSeq("1");
		userInfo.setUserNm("홍길동");
		
		CntcInfo cntcInfo = new CntcInfo();
		cntcInfo.setMphnNo("01012345678");
		cntcInfo.setEmail("test@impay.com");
		cntcInfo.setDeptNm("impay 개발팀");
		
		UserMng userMng = new UserMng();
		userMng.setUserId("test");
		userMng.setUserSeq("1");
		userMng.setUserNm("홍길동");
		userMng.setCntcInfo(cntcInfo);
		
		userMngService.updateUserMngInfo(userMng, userInfo);
	}
	
	/**
	 * 비밀번호 변경 test
	 * @throws Exception
	 */
	@Test
	public void testUpdatePwd() throws Exception{
		String userSeq = "1";
		
		UserMng retObj = new UserMng();
		retObj.setPwd("$2a$10$u1kF2PDxQ39Kh1.QF2G2t.bdOknINCrkvNVErM/D1rO1s7f9vy6oC");
		
		when(userMngMapper.selectUserInfo(userSeq)).thenReturn(retObj);
		
		UserMngPwd param = new UserMngPwd();
		param.setUserSeq(userSeq);
		param.setPwd("1234");
		param.setNewPwd("q1w2e3r4!");
		param.setNewPwd2("q1w2e3r4!");
		RestResult<Integer> actual = userMngService.updatePwd(param);
		
		assertTrue(actual.isSuccess());
	}

}
