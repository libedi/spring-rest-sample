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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.mapper.CodeNameMapper;
import com.skplanet.impay.ccs.common.service.mapper.UserMapper;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.sysm.model.AnnoDescReg;
import com.skplanet.impay.ccs.sysm.service.mapper.AnnoDescRegMapper;


/**
 * 시스템관리
 * 고객센터 공지사항 Service Test
 * @author Jang
 *
 */
public class AnnoDescRegServiceTest {

	private AnnoDescRegService annoDescRegService; 
	private AnnoDescRegMapper annoDescRegMapper;
	private UserMapper userMapper;
	private CodeNameMapper codeNameMapper;
	
	@Before
	public void setUp() throws Exception {
		annoDescRegService = new AnnoDescRegService();
		
		annoDescRegMapper = mock(AnnoDescRegMapper.class);
		userMapper = mock(UserMapper.class);
		codeNameMapper = mock(CodeNameMapper.class);
		
		ReflectionTestUtils.setField(annoDescRegService, "annoDescRegMapper", annoDescRegMapper);
		ReflectionTestUtils.setField(annoDescRegService, "userMapper", userMapper);
		ReflectionTestUtils.setField(annoDescRegService, "codeNameMapper", codeNameMapper);
	}
	@Test
	public void testMockCreation() throws Exception{
		assertNotNull(annoDescRegService);
		assertNotNull(annoDescRegMapper);
		assertNotNull(userMapper);
		assertNotNull(codeNameMapper);
	}
	/**
	 * 공지사항 상세 내역 조회
	 * @throws Exception
	 */
	@Test
	public void testGetAnnoDescInfo() throws Exception{
		long param = 1;
		AnnoDescReg expected = new AnnoDescReg();
		
		when(annoDescRegMapper.selectAnnoDescInfo(param)).thenReturn(expected);
		AnnoDescReg actual = this.annoDescRegService.getAnnoDescInfo(param);
		assertEquals(expected, actual);
	}
	/**
	 * 공지사항 등록
	 * @throws Exception
	 */
	@Test
	public void testAddAnnoDescReg() throws Exception{
		AnnoDescReg param1 = new AnnoDescReg();
		param1.setBordClfCd("test");
		param1.setBordClfCdNm("test");
		param1.setCtnt("test");
		CustomUserDetails param2 = new CustomUserDetails();
		param2.setUserSeq("1");
		RestResult<String> actual = this.annoDescRegService.addAnnoDescReg(param1, param2);
		assertTrue(actual.isSuccess());
	}
	
	/**
	 * 공지사항 수정
	 * @throws Exception
	 */
	@Test
	public void testUpdateAnnoDescReg() throws Exception{
		AnnoDescReg param1 = new AnnoDescReg();
		param1.setBordClfCd("test");
		param1.setBordClfCdNm("test");
		param1.setCtnt("test");
		CustomUserDetails param2 = new CustomUserDetails();
		param2.setUserSeq("1");
		RestResult<String> actual = this.annoDescRegService.updateAnnoDescReg(param1, param2);
		assertTrue(actual.isSuccess());
	}
}
