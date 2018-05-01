/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.csm.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.model.SendReqModel;
import com.skplanet.impay.ccs.common.service.EmailService;
import com.skplanet.impay.ccs.common.service.FileService;
import com.skplanet.impay.ccs.common.service.MessageByLocaleServiceImpl;
import com.skplanet.impay.ccs.common.service.SmsSendService;
import com.skplanet.impay.ccs.common.service.mapper.CodeNameMapper;
import com.skplanet.impay.ccs.common.service.mapper.CommonMapper;
import com.skplanet.impay.ccs.common.service.mapper.SendReqMapper;
import com.skplanet.impay.ccs.common.service.mapper.UserMapper;
import com.skplanet.impay.ccs.csm.model.CnslDetail;
import com.skplanet.impay.ccs.csm.model.CnslScrpt;
import com.skplanet.impay.ccs.csm.model.CnslSearch;
import com.skplanet.impay.ccs.csm.model.NotiResult;
import com.skplanet.impay.ccs.csm.model.PayItcptModel;
import com.skplanet.impay.ccs.csm.service.mapper.CnslMngMapper;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.uim.service.mapper.UserMngMapper;

/**
 * 상담관리 Service Test
 * @author Sangjun, Park
 *
 */
public class CnslMngServiceTest {
	
	private CnslMngService cnslMngService;
	private CnslMngMapper cnslMngMapper;
	private MessageByLocaleServiceImpl message;
	private CodeNameMapper codeNameMapper;
	private UserMapper userMapper;
	private FileService fileService;
	private EmailService emailService;
	private SmsSendService smsSendService;
	private SendReqMapper sendReqMapper;
	private UserMngMapper userMngMapper;
	private CommonMapper commonMapper;
	private ObjectMapper objectMapper;
	private FreeMarkerConfigurer freeMarkerConfigurer;
	private String hostAddress;
	
	@Before
	public void setUp() throws Exception {
		cnslMngService = new CnslMngService();
		
		cnslMngMapper = mock(CnslMngMapper.class);
		message = mock(MessageByLocaleServiceImpl.class);
		codeNameMapper = mock(CodeNameMapper.class);
		userMapper = mock(UserMapper.class);
		fileService = mock(FileService.class);
		emailService = mock(EmailService.class);
		smsSendService = mock(SmsSendService.class);
		sendReqMapper = mock(SendReqMapper.class);
		userMngMapper = mock(UserMngMapper.class);
		commonMapper = mock(CommonMapper.class);
		objectMapper = mock(ObjectMapper.class);
		freeMarkerConfigurer = mock(FreeMarkerConfigurer.class);
		hostAddress = "http://localhost:8882";
		
		ReflectionTestUtils.setField(cnslMngService, "cnslMngMapper", cnslMngMapper);
		ReflectionTestUtils.setField(cnslMngService, "message", message);
		ReflectionTestUtils.setField(cnslMngService, "codeNameMapper", codeNameMapper);
		ReflectionTestUtils.setField(cnslMngService, "userMapper", userMapper);
		ReflectionTestUtils.setField(cnslMngService, "fileService", fileService);
		ReflectionTestUtils.setField(cnslMngService, "emailService", emailService);
		ReflectionTestUtils.setField(cnslMngService, "smsSendService", smsSendService);
		ReflectionTestUtils.setField(cnslMngService, "sendReqMapper", sendReqMapper);
		ReflectionTestUtils.setField(cnslMngService, "userMngMapper", userMngMapper);
		ReflectionTestUtils.setField(cnslMngService, "commonMapper", commonMapper);
		ReflectionTestUtils.setField(cnslMngService, "objectMapper", objectMapper);
		ReflectionTestUtils.setField(cnslMngService, "freeMarkerConfigurer", freeMarkerConfigurer);
		ReflectionTestUtils.setField(cnslMngService, "hostAddress", hostAddress);
	}
	
	@Test
	public void testMockCreation() throws Exception{
		assertNotNull(cnslMngService);
		assertNotNull(cnslMngMapper);
		assertNotNull(message);
		assertNotNull(codeNameMapper);
		assertNotNull(userMapper);
		assertNotNull(fileService);
		assertNotNull(emailService);
		assertNotNull(smsSendService);
		assertNotNull(sendReqMapper);
		assertNotNull(userMngMapper);
		assertNotNull(commonMapper);
		assertNotNull(objectMapper);
		assertNotNull(freeMarkerConfigurer);
		assertNotNull(hostAddress);
	}
	
	/**
	 * 거래완료 카운트 조회 test
	 * @throws Exception
	 */
	@Test
	public void testGetTradeCompleteListCount() throws Exception {
		int expected = 2;
		CnslSearch param = new CnslSearch();
		when(cnslMngMapper.selectTradeCompleteListCount(param)).thenReturn(expected);
		assertEquals(expected, cnslMngService.getTradeCompleteListCount(param));
	}
	
	/**
	 * 상담관리 조회 test
	 * @throws Exception
	 */
	@Test
	public void testGetCnslDetail() throws Exception{
		CnslSearch param = new CnslSearch();
		param.setRcptNo("12345678");
		
		CnslDetail expected = new CnslDetail();
		expected.setRcptNo(param.getRcptNo());
		expected.setCnslCtnt("test");
		
		when(cnslMngMapper.selectCnslDetail(param.getRcptNo())).thenReturn(expected);
		
		CnslDetail actual = cnslMngService.getCnslDetail(param);
		
		assertEquals(expected.getRcptNo(), actual.getRcptNo());
		assertEquals(expected.getCnslCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"), actual.getCnslCtnt());
	}
	
	/**
	 * 상담내용 저장 test
	 * @throws Exception
	 */
	@Test
	public void testInsertCnslCtnt() throws Exception {
		int expected = 1;
		
		CnslDetail param1 = new CnslDetail();
		param1.setCnslCtnt("test");
		
		CustomUserDetails param2 = new CustomUserDetails();
		param2.setUserSeq("1");
		
		when(cnslMngMapper.insertCnslDtl(param1)).thenReturn(expected);
		when(cnslMngMapper.insertCnslChg(param1)).thenReturn(expected);
		when(cnslMngMapper.insertCnslPayRel(param1)).thenReturn(expected);
		
		int actual = this.cnslMngService.insertCnslCtnt(param1, param2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 상담내용 수정 test
	 * @throws Exception
	 */
	@Test
	public void testUpdateCnslCtnt() throws Exception {
		int expected = 1;
		
		CnslDetail param1 = new CnslDetail();
		param1.setRcptNo("12345678");
		param1.setCnslCtnt("test");
		
		CustomUserDetails param2 = new CustomUserDetails();
		param2.setUserSeq("1");
		
		CnslDetail retObj = new CnslDetail();
		retObj.setSeq(1);
		
		when(cnslMngMapper.selectCnslChgLast(param1.getRcptNo())).thenReturn(retObj);
		when(cnslMngMapper.updateCnslDtl(param1)).thenReturn(expected);
		when(cnslMngMapper.selectCnslDetail(param1.getRcptNo())).thenReturn(new CnslDetail());
		when(cnslMngMapper.insertCnslChg(param1)).thenReturn(expected);
		
		int actual = this.cnslMngService.updateCnslCtnt(param1, param2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 거래시도 카운트 조회 test
	 * @throws Exception
	 */
	@Test
	public void testGetTraderTryListCount() throws Exception{
		int expected = 2;
		
		CnslSearch param = new CnslSearch();
		when(cnslMngMapper.selectTradeTryListCount(param)).thenReturn(expected);
		
		int actual = this.cnslMngService.getTradeTryListCount(param);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 결제차단내역 단건 조회 test
	 * @throws Exception
	 */
	@Test
	public void testGetPayItcpt() throws Exception{
		String param = "123456789";
		PayItcptModel expected = new PayItcptModel();
		expected.setProcRsn("block process");
		expected.setRegr("1");
		expected.setLastChgr("1");
		
		when(cnslMngMapper.selectPayItcpt(param)).thenReturn(expected);
		when(userMngMapper.selectUsername(expected.getRegr())).thenReturn("테스트");
		when(userMngMapper.selectUsername(expected.getLastChgr())).thenReturn("테스트");
		
		PayItcptModel actual = this.cnslMngService.getPayItcpt(param);
		
		assertEquals(expected.getProcRsn(), actual.getProcRsn());
	}
	
	/**
	 * 결제차단 등록 test
	 * @throws Exception
	 */
	@Test
	public void testInsertPayItcpt() throws Exception{
		int expected = 1;
		
		PayItcptModel param1 = new PayItcptModel();
		param1.setPayMphnId("123456789");
		param1.setPayrSeq("111222333");
		param1.setItcptClfFlg("P");
		param1.setProcRsn("block process");
		param1.setRegr("1");
		param1.setLastChgr("1");
		
		CustomUserDetails param2 = new CustomUserDetails();
		param2.setUserSeq("1");
		
		when(cnslMngMapper.insertPayItcpt(param1)).thenReturn(expected);
		
		RestResult<PayItcptModel> actual = this.cnslMngService.insertPayItcpt(param1, param2);
		
		assertTrue(actual.isSuccess());
		assertEquals(expected, actual.getInsertCnt());
	}
	
	/**
	 * 결제차단 해제 test
	 * @throws Exception
	 */
	@Test
	public void testUpdatePayItcpt() throws Exception{
		int expected = 1;
		
		PayItcptModel param1 = new PayItcptModel();
		param1.setPayMphnId("123456789");
		param1.setPayrSeq("111222333");
		param1.setItcptClfFlg("P");
		param1.setProcRsn("block process");
		param1.setRegr("1");
		param1.setLastChgr("1");
		
		CustomUserDetails param2 = new CustomUserDetails();
		param2.setUserSeq("1");
		
		when(cnslMngMapper.updatePayItcpt(param1)).thenReturn(expected);
		
		int actual = this.cnslMngService.updatePayItcpt(param1, param2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 결과통보 조회 test
	 * @throws Exception
	 */
	@Test
	public void testGetNotiResult() throws Exception{
		String param = "123456789";
		
		SendReqModel retObj = new SendReqModel();
		retObj.setIdvdMphnNo("01012345678");
		retObj.setSmsSndWord("TEST SMS WORD");
		retObj.setIdvdSndYn("Y");
		when(sendReqMapper.selectSendReqDtl(param)).thenReturn(retObj);
		
		NotiResult expected = new NotiResult();
		expected.setRsltNotiMthd("S");
		expected.setMphnNo(retObj.getIdvdMphnNo());
		expected.setProcCtnt(retObj.getSmsSndWord().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		
		NotiResult actual = this.cnslMngService.getNotiResult(param);
		
		assertEquals(expected.getMphnNo(), actual.getMphnNo());
	}
	
	/**
	 * 문서보관함 리스트 조회 test
	 * @throws Exception
	 */
	@Test
	public void testGetSmsDocumentList() throws Exception{
		String sms1 = "[impay에서 보냅니다]\r\n이것은 테스트1 문자입니다.";
		String sms2 = "[impay에서 보냅니다]\r\n이것은 테스트2 문자입니다.";
		String sms3 = "[impay에서 보냅니다]\r\n이것은 테스트3 문자입니다.";
		String sms4 = "[impay에서 보냅니다]\r\n이것은 테스트4 문자입니다.";
		
		List<String> expected = new ArrayList<>();
		expected.add(sms1.replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		expected.add(sms2.replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		expected.add(sms3.replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		expected.add(sms4.replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		
		when(this.cnslMngMapper.selectSmsDocumentList()).thenReturn(expected);
		
		List<String> actual = this.cnslMngService.getSmsDocumentList();
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 상담 시나리오 리스트 조회 test
	 * @throws Exception
	 */
	@Test
	public void testgetCnslScriptContent() throws Exception{
		String param = "결제";
		
		CnslScrpt scrpt1 = new CnslScrpt();
		scrpt1.setCnslScrptList(new ArrayList<CnslScrpt>());
		CnslScrpt scrpt2 = new CnslScrpt();
		scrpt2.setCnslScrptList(new ArrayList<CnslScrpt>());
		CnslScrpt scrpt3 = new CnslScrpt();
		scrpt3.setCnslScrptList(new ArrayList<CnslScrpt>());
		
		List<CnslScrpt> expected = new ArrayList<>();
		expected.add(scrpt1);
		expected.add(scrpt2);
		expected.add(scrpt3);
		when(this.cnslMngMapper.selectCnslCrspScrpt(param)).thenReturn(expected);
		
		List<CnslScrpt> actual = this.cnslMngService.getCnslScriptContent(param);
		
		assertEquals(expected.size(), actual.size());
	}
	
}
