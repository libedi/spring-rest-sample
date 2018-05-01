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
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.rpm.model.AddPayWhiteList;
import com.skplanet.impay.ccs.rpm.service.mapper.AddPayWhiteListMngMapper;



/**
 * CCS 접수/처리현황관리 
 * - 가산금 W/L 관리 Service
 * @author Jang
 *
 */
public class AddPayWhiteListMngServiceTest {
	
	private AddPayWhiteListMngService addPayWhiteListMngService;
	private AddPayWhiteListMngMapper addPayWhiteListMngMapper;
	private CodeNameMapper codeNameMapper;
	private UserMapper userMapper;

	@Before
	public void setUp() throws Exception{
		addPayWhiteListMngService = new AddPayWhiteListMngService();
		
		addPayWhiteListMngMapper = mock(AddPayWhiteListMngMapper.class);
		codeNameMapper = mock(CodeNameMapper.class);
		userMapper = mock(UserMapper.class);
		
		ReflectionTestUtils.setField(addPayWhiteListMngService, "addPayWhiteListMngMapper", addPayWhiteListMngMapper);
		ReflectionTestUtils.setField(addPayWhiteListMngService, "codeNameMapper", codeNameMapper);
		ReflectionTestUtils.setField(addPayWhiteListMngService, "userMapper", userMapper);
	}
	@Test
	public void testMockCreation() throws Exception{
		assertNotNull(addPayWhiteListMngService);
		assertNotNull(addPayWhiteListMngMapper);
		assertNotNull(codeNameMapper);
		assertNotNull(userMapper);
	}
	/**
	 * 가산금 W/L 상세 정보 조회
	 * @throws Exception
	 */
	@Test
	public void testGetAddPayWhiteListInfo() throws Exception{
		String param = "1";
		AddPayWhiteList expected = new AddPayWhiteList();
		expected.setWlRegNo("1");
		expected.setPayrNm("test");
		
		when(addPayWhiteListMngMapper.selectAddPayWhiteListInfo(param)).thenReturn(expected);
		AddPayWhiteList actual = this.addPayWhiteListMngService.getAddPayWhiteListInfo(param);
		assertEquals(expected, actual);
	}
	/**
	 * 가산금 W/L 등록
	 * @throws Exception
	 */
	@Test
	public void testAddPayAddWhiteList() throws Exception{
		AddPayWhiteList param1 = new AddPayWhiteList();
		CustomUserDetails param2 = new CustomUserDetails();
		RestResult<String> actual = this.addPayWhiteListMngService.addPayAddWhiteList(param1, param2);
		assertEquals(false, actual.isSuccess());
		
	}
	/**
	 * 가산금 W/L 삭제 사유 등록
	 * @throws Exception
	 */
	@Test
	public void testUpdateAddPayDelWhiteList() throws Exception{
		AddPayWhiteList param1 = new AddPayWhiteList();
		param1.setDelYn("Y");
		param1.setLastChgr("test");
		param1.setPayrNm("TEST");
		param1.setRegr("test");
		CustomUserDetails param2 = new CustomUserDetails();
		param2.setUserNm("test");
		RestResult<String> actual = this.addPayWhiteListMngService.updateAddPayDelWhiteList(param1, param2);
		assertTrue(actual.isSuccess());
				
	}
	
	
	
	
}
