/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skplanet.impay.Application;
import com.skplanet.impay.ccs.common.service.SmsMqSender;

/**
 * Sms즉시발송 Test
 * @author jisu park
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class SmsMqSenderTest {
	
	private static Logger logger = LoggerFactory.getLogger(SmsMqSenderTest.class);
	
	@Autowired
	private SmsMqSender smsSendService;

    @Test
	public void testSendSmsAuthReq() {
    	logger.debug("[TEST] 시작");
    	
    	/*SmsAprv smsAprv = new SmsAprv();
    	smsAprv.setAuthtiSeq("201604250000001");
    	smsAprv.setAuthtiNo("4567");
    	smsAprv.setMphnNo("01052458873");
    	smsAprv.setMsg("인증번호는 4567 입니다.");
    	*/
    	String seq = "201604250001001";
    	String mphnno = "01052458873";
    	String msg = "인증번호는 4567 입니다.";
    	
    	assertEquals("success", smsSendService.sendSmsReq(seq, mphnno, msg));
    	
    	logger.debug("[TEST] 종료");
    }
}
