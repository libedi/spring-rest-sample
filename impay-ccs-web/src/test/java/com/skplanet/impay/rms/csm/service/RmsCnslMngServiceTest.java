/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.rms.csm.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.skplanet.impay.rms.csm.model.RmsCustChg;
import com.skplanet.impay.rms.csm.model.RmsDftNum;
import com.skplanet.impay.rms.csm.model.RmsFraudReliefReg;
import com.skplanet.impay.rms.csm.model.RmsRmBlkReliefReg;
import com.skplanet.impay.rms.csm.service.mapper.RmsCnslMngMapper;
import com.skplanet.impay.rms.util.RmsMessageUtil;

/**
 * 상담 관리 (RMS) Service 테스트
 * @author Sunghee Park
 */
public class RmsCnslMngServiceTest {
	
	private RmsCnslMngService rmsCnslMngService;
	private RmsCnslMngMapper rmsCnslMngMapper;
	private RmsMessageUtil rmsMessageUtil;
	
	@Before
	public void setUp() throws Exception {
		rmsCnslMngService = new RmsCnslMngService();
		rmsCnslMngMapper = mock(RmsCnslMngMapper.class);
		rmsMessageUtil = mock(RmsMessageUtil.class);
		ReflectionTestUtils.setField(rmsCnslMngService, "rmsCnslMngMapper", rmsCnslMngMapper);
		ReflectionTestUtils.setField(rmsCnslMngService, "rmsMessageUtil", rmsMessageUtil);
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testMockCreation() throws Exception {
		assertNotNull(rmsCnslMngService);
		assertNotNull(rmsCnslMngMapper);
		assertNotNull(rmsMessageUtil);
	}
	
	@Test
	public void testGetNpayAmtDetail() throws Exception {
		RmsDftNum param = new RmsDftNum();
		
		RmsDftNum expected = new RmsDftNum();
		
		when(rmsCnslMngMapper.selectNpayAmtDetail(param)).thenReturn(expected);
		RmsDftNum actual = rmsCnslMngService.getNpayAmtDetail(param);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testRelieveRm() throws Exception {
		RmsRmBlkReliefReg param = new RmsRmBlkReliefReg();
        param.setPayrSeq("2505194");
		param.setMphnNo("01062968092");
		param.setApplyStatus("R");
		param.setReliefClfCd("D");
		param.setBlkReliefCd("RMBRFR");
		param.setEdDt("2016.04.11");
		param.setRelsRsnCd("RMRR99");
		param.setRmk("비고2");
		
		RmsRmBlkReliefReg currentRmRelief = new RmsRmBlkReliefReg();
		currentRmRelief.setRowId("AAAI/zAAPAABOXtAAN");
		currentRmRelief.setPayrSeq("2505194");
		currentRmRelief.setMphnNo("01012345678");
		currentRmRelief.setBlkReliefCd("RMBRFR");
		currentRmRelief.setStDt("2016.03.04 16:16:41");
		currentRmRelief.setEdDt("2016.04.04 23:59:59");
		currentRmRelief.setReliefClfCd("P");
		currentRmRelief.setRelsRsnCd("RMRR04");
		currentRmRelief.setRmk("비고1");
		
		List<RmsRmBlkReliefReg> currentRmReliefList = new ArrayList<>();
		currentRmReliefList.add(currentRmRelief);
		
		RmsCustChg custChg = new RmsCustChg();
		custChg.setPayrSeq("2505194");
		custChg.setMphnNo("01012345678");
		custChg.setRegClf("R");
		custChg.setCustInfoChgCd("RMBRFR");
		custChg.setBlkRelsClf("B");
		custChg.setStDt("2016.03.04 16:16:41");
		custChg.setEdDt("2016.03.11 12:23:57");
		custChg.setChgRsnCd("RMRR04");
		custChg.setRmk("비고1|비고2");
		custChg.setInptId("test");
		
		when(rmsCnslMngMapper.selectCurrentRmReliefList(param)).thenReturn(currentRmReliefList);
		when(rmsCnslMngMapper.selectSysdate()).thenReturn(new Date());
		when(rmsCnslMngMapper.updateRmBlkReliefCust(currentRmRelief)).thenReturn(1);
		when(rmsCnslMngMapper.insertCustChg(custChg)).thenReturn(1);
		when(rmsCnslMngMapper.insertRmBlkReliefCust(param)).thenReturn(1);
		rmsCnslMngService.relieveRm(param);
	}
	
	@Test
	public void testBlockRm() throws Exception {
		RmsRmBlkReliefReg param = new RmsRmBlkReliefReg();
		param.setPayrSeq("2505194");
		param.setMphnNo("01062968092");
		param.setApplyStatus("B");
		param.setReliefClfCd("D");
		param.setBlkReliefCd("RMBRFR");
		param.setRmk("비고3");
		
		RmsRmBlkReliefReg currentRmRelief = new RmsRmBlkReliefReg();
		currentRmRelief.setRowId("AAAI/zAAPAABOXsAAB");
		currentRmRelief.setPayrSeq("2505194");
		currentRmRelief.setMphnNo("");
		currentRmRelief.setBlkReliefCd("RMBRFR");
		currentRmRelief.setStDt("2016.03.11 12:23:58");
		currentRmRelief.setEdDt("2016.04.11 23:59:59");
		currentRmRelief.setReliefClfCd("D");
		currentRmRelief.setRelsRsnCd("RMRR99");
		currentRmRelief.setRmk("비고1|비고2");
		
		List<RmsRmBlkReliefReg> currentRmReliefList = new ArrayList<>();
		currentRmReliefList.add(currentRmRelief);
		
		RmsCustChg custChg = new RmsCustChg();
		custChg.setPayrSeq("2505194");
		custChg.setMphnNo("");
		custChg.setRegClf("R");
		custChg.setCustInfoChgCd("RMBRFR");
		custChg.setBlkRelsClf("B");
		custChg.setStDt("2016.03.11 12:23:58");
		custChg.setEdDt("2016.03.11 12:29:19");
		custChg.setChgRsnCd("RMRR99");
		custChg.setRmk("비고1|비고2|비고3");
		custChg.setInptId("test");
		
		when(rmsCnslMngMapper.selectCurrentRmReliefList(param)).thenReturn(currentRmReliefList);
		when(rmsCnslMngMapper.selectSysdate()).thenReturn(new Date());
		when(rmsCnslMngMapper.updateRmBlkReliefCust(currentRmRelief)).thenReturn(1);
		when(rmsCnslMngMapper.insertCustChg(custChg)).thenReturn(1);
		rmsCnslMngService.blockRm(param);
	}
	
	@Test
	public void testRelieveFraud() throws Exception {
		RmsFraudReliefReg param = new RmsFraudReliefReg();
		param.setPayrSeq("2505194");
		param.setRelsRsnCd("RMFR01");
		param.setRmk("비고2");
		
		RmsFraudReliefReg currentFraudBlk = new RmsFraudReliefReg();
		currentFraudBlk.setRowId("AAAGczAAPAAAAQ3AAB");
		currentFraudBlk.setPayrSeq("2505194");
		currentFraudBlk.setFraudClfCd("RMFCC1");
		currentFraudBlk.setStDt("2016.03.04 11:11:50");
		currentFraudBlk.setEdDt("9999.12.31 23:59:59");
		currentFraudBlk.setRmk("비고1");
		
		RmsCustChg custChg = new RmsCustChg();
		custChg.setPayrSeq("2505194");
		custChg.setMphnNo("");
		custChg.setRegClf("F");
		custChg.setCustInfoChgCd("RMFCC1");
		custChg.setBlkRelsClf("R");
		custChg.setStDt("2016.03.04 11:11:50");
		custChg.setEdDt("2016.03.11 13:49:27");
		custChg.setChgRsnCd("RMFR01");
		custChg.setRmk("비고1|비고2");
		custChg.setInptId("test");
		
		when(rmsCnslMngMapper.selectCurrentFraudBlk(param)).thenReturn(currentFraudBlk);
		when(rmsCnslMngMapper.selectSysdate()).thenReturn(new Date());
		when(rmsCnslMngMapper.updateFraudCust(currentFraudBlk)).thenReturn(1);
		when(rmsCnslMngMapper.insertCustChg(custChg)).thenReturn(1);
	}
}