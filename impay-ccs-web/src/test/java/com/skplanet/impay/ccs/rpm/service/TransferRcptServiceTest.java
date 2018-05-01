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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.skplanet.impay.ccs.common.service.MessageByLocaleServiceImpl;
import com.skplanet.impay.ccs.common.service.mapper.CodeNameMapper;
import com.skplanet.impay.ccs.rpm.model.RpmSearch;
import com.skplanet.impay.ccs.rpm.service.mapper.TransferRcptMapper;

/**
 * 이관 접수 Service Test
 * @author Sangjun, Park
 *
 */
public class TransferRcptServiceTest {
	private TransferRcptService transferRcptService;
	private TransferRcptMapper transferRcptMapper;
	private MessageByLocaleServiceImpl message;
	private CodeNameMapper codeNameMapper;
	
	@Before
	public void setup() throws Exception{
		transferRcptService = new TransferRcptService();
		transferRcptMapper = mock(TransferRcptMapper.class);
		message = mock(MessageByLocaleServiceImpl.class);
		codeNameMapper = mock(CodeNameMapper.class);
		
		ReflectionTestUtils.setField(transferRcptService, "transferRcptMapper", transferRcptMapper);
		ReflectionTestUtils.setField(transferRcptService, "message", message);
		ReflectionTestUtils.setField(transferRcptService, "codeNameMapper", codeNameMapper);
	}
	
	@Test
	public void testMockCreation() throws Exception{
		assertNotNull(transferRcptService);
		assertNotNull(transferRcptMapper);
		assertNotNull(message);
		assertNotNull(codeNameMapper);
	}
	
	/**
	 * 이관 접수 리스트 총건수 조회 test
	 * @throws Exception
	 */
	@Test
	public void testGetTransferRcptListCount() throws Exception{
		int expected = 2;
		RpmSearch param = new RpmSearch();
		when(transferRcptMapper.selectCnslCpTjurListCount(param)).thenReturn(expected);
		int actual = this.transferRcptService.getTransferRcptListCount(param);
		assertEquals(expected, actual);
	}
	
}
