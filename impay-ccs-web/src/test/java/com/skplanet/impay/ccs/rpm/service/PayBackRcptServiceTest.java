/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.rpm.service;

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
import com.skplanet.impay.ccs.csm.model.CnslSearch;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.rpm.model.PayBackRcpt;
import com.skplanet.impay.ccs.rpm.service.mapper.PayBackRcptMapper;


/**
 * CCS 접수/처리현황관리 
 * - 환불접수 Service
 * @author Jang
 *
 */
public class PayBackRcptServiceTest {

	private PayBackRcptService payBackRcptService;
	private PayBackRcptMapper payBackRcptMapper;
	private UserMapper userMapper;
	private CodeNameMapper codeNameMapper;
	
	@Before
	public void setUp() throws Exception{
		payBackRcptService = new PayBackRcptService();
		
		payBackRcptMapper = mock(PayBackRcptMapper.class);
		userMapper = mock(UserMapper.class);
		codeNameMapper = mock(CodeNameMapper.class);
		
		ReflectionTestUtils.setField(payBackRcptService, "payBackRcptMapper", payBackRcptMapper);
		ReflectionTestUtils.setField(payBackRcptService, "userMapper", userMapper);
		ReflectionTestUtils.setField(payBackRcptService, "codeNameMapper", codeNameMapper);
	}
	
	@Test
	public void testMockCreation() throws Exception{
		assertNotNull(payBackRcptService);
		assertNotNull(payBackRcptMapper);
		assertNotNull(userMapper);
		assertNotNull(codeNameMapper);
	}
	/**
	 * 환불 접수 상세 조회
	 * @throws Exception
	 */
	@Test
	public void testGetPayBackRcptInfo() throws Exception{
		String param = "1";
		PayBackRcpt expected = new PayBackRcpt();
		when(payBackRcptMapper.selectPayBackRcptInfo(param)).thenReturn(expected);
		PayBackRcpt actual = this.payBackRcptService.getPayBackRcptInfo(param);
		assertEquals(expected, actual);
	}
	/**
	 * 환불 접수 등록 
	 * @throws Exception
	 */
	@Test
	public void testAddPayBackRcpt() throws Exception{
		PayBackRcpt param1 = new PayBackRcpt();
		CustomUserDetails param2 = new CustomUserDetails();
		RestResult<String> actual = this.payBackRcptService.addPayBackRcpt(param1, param2);
		assertEquals(false, actual.isSuccess());
	}
	/**
	 * 환불 접수 
	 * CHECK 된 ROW 삭제여부 Y 수정
	 * @throws Exception
	 */
	@Test
	public void testDeletePayBackRcpt() throws Exception{
		CnslSearch param = new CnslSearch();
		param.setChgListYn("test");
		CustomUserDetails param2 = new CustomUserDetails();
		RestResult<String> actual = this.payBackRcptService.deletePayBackRcpt(param,param2);
		assertTrue(actual.isSuccess());
	}
}
