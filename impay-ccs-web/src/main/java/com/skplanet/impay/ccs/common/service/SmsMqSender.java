/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.impay.common.sms.model.SendSmsRequest;


/**
 * SMS 즉시발송(Rabbit MQ)
 * @author jisu park
 */
@Service
public class SmsMqSender {
	
	private static Logger logger = LoggerFactory.getLogger(SmsMqSender.class);
		
	@Autowired
	private MessageConverter messageConverter;
	
	@Autowired
	private RabbitTemplate rabbitTemplate; 
    
    @PostConstruct
    public void afterPropertySet() {
        this.rabbitTemplate.setMessageConverter(messageConverter);
    }

	/**
	 * SMS즉시발송(Rabbit MQ)
	 * 
	 * @param reqId		인증요청key
	 * @param reveiver	수신자 휴대폰번호
	 * @param payload	메시지
	 * @return String
	 */
	public String sendSmsReq(String reqId, String reveiver, String payload) {
		String result = "error";
        
		this.rabbitTemplate.convertAndSend("CommonSmsSendQueue", new SendSmsRequest(reqId, reveiver, payload));
        logger.info("[SMS인증요청을 발송하였습니다.]");
        
        result = "success";		
		return result;
	}
}