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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.sysm.model.SmsDocStrgMng;
import com.skplanet.impay.ccs.sysm.service.mapper.SmsDocStrgMngMapper;

/**
 * 시스템 관리
 * SMS 문서 보관함 Service Test
 * @author Jang
 *
 */
public class SmsDocStrgMngServiceTest {
	private SmsDocStrgMngService smsDocStrgMngService;
	private SmsDocStrgMngMapper smsDocStrgMngMapper;
	
	@Before
	public void setUp() throws Exception{
		smsDocStrgMngService = new SmsDocStrgMngService();
		
		smsDocStrgMngMapper = mock(SmsDocStrgMngMapper.class);
		
		ReflectionTestUtils.setField(smsDocStrgMngService, "smsDocStrgMngMapper", smsDocStrgMngMapper);
	}

	@Test
	public void testMockCreation() throws Exception{
		assertNotNull(smsDocStrgMngService);
		assertNotNull(smsDocStrgMngMapper);
	}
	
	/**
	 * SMS 문서보관함 조회
	 * @throws Exception
	 */
	@Test
	public void testSelectSmsDocList() throws Exception{
		int expectedSize = 10;
		SmsDocStrgMng obj1 = new SmsDocStrgMng();
		SmsDocStrgMng obj2 = new SmsDocStrgMng();
		List<SmsDocStrgMng> expectedList = new ArrayList<>();
		expectedList.add(obj1);
		expectedList.add(obj2);
		
		when(smsDocStrgMngMapper.getSmsDocList()).thenReturn(expectedList);
		when(smsDocStrgMngMapper.getSmsDocCount()).thenReturn(expectedSize);
		Page<SmsDocStrgMng> expected = new Page<>(expectedSize, expectedList);
		
		Page<SmsDocStrgMng> actual = this.smsDocStrgMngService.selectSmsDocList();

		assertEquals(expected.getContent(), actual.getContent());
	}
	/**
	 * 특정 SMS 문구 조회
	 * @throws Exception
	 */
	@Test
	public void testSelectSmsWord() throws Exception{
		String param = "12345";
		SmsDocStrgMng expected = new SmsDocStrgMng();
		expected.setCharStrgNo("12345");

		when(smsDocStrgMngMapper.getSmsWord(param)).thenReturn(expected);
		
		SmsDocStrgMng actual = this.smsDocStrgMngService.selectSmsWord(param);
		assertEquals(expected.getCharStrgNo(), actual.getCharStrgNo());
	}
	/**
	 * SMS 문구 등록
	 * @throws Exception
	 */
	@Test
	public void testAddSmsWord() throws Exception{
		
		SmsDocStrgMng param1 = new SmsDocStrgMng();
		param1.setCharCtnt("test");
		param1.setCharStrgNo("1");
		param1.setRegr("1");

		CustomUserDetails param2 = new CustomUserDetails();
		param2.setUserSeq("1");
		RestResult<String> actual = this.smsDocStrgMngService.addSmsWord(param1, param2);
		assertTrue(actual.isSuccess());
		
	}
	/**
	 * SMS 문구 수정
	 * @throws Exception
	 */
	@Test
	public void testUpdateSmsWord() throws Exception{
		
		SmsDocStrgMng param1 = new SmsDocStrgMng();
		param1.setCharCtnt("test");
		param1.setCharStrgNo("1");
		param1.setRegr("1");

		CustomUserDetails param2 = new CustomUserDetails();
		param2.setUserSeq("1");
		RestResult<String> actual = this.smsDocStrgMngService.updateSmsWord(param1, param2);
		assertTrue(actual.isSuccess());
		
	}
	/**
	 * SMS 문구 삭제
	 * @throws Exception
	 */
	@Test
	public void testDeleteSmsWord() throws Exception{
		
		SmsDocStrgMng param1 = new SmsDocStrgMng();
		param1.setCharCtnt("");
		param1.setCharStrgNo("");
		param1.setRegDt("");
		param1.setRegr("");

		RestResult<String> actual = this.smsDocStrgMngService.deleteSmsWord(param1);
		assertTrue(actual.isSuccess());
		
	}
}
