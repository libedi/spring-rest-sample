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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.impay.ccs.common.service.mapper.SendReqMapper;
import com.skplanet.impay.ccs.common.model.SendReqModel;
import com.skplanet.impay.ccs.common.model.SmsSend;
import com.skplanet.impay.ccs.common.model.SmsAprv;

/**
 * SMS 발송 Service
 * @author Sangjun, Park
 *
 */
@Service
public class SmsSendService {
	
	private static Logger logger = LoggerFactory.getLogger(SmsSendService.class);

	@Autowired
	private SendReqService sendReqService;
	
	@Autowired
	private SendReqMapper sendReqMapper;
	
	/**
	 * SMS 발송 요청
	 * @param smsSend SMS 발송 정보
	 * @return 발송요청순번
	 */
	public String sendSms(SmsSend smsSend) {
		SendReqModel sendReq = new SendReqModel();
		sendReq.setIdvdSndYn(smsSend.getIdvdSendYn());
		sendReq.setIdvdMphnNo(smsSend.getMphnNo());
		sendReq.setSmsSndWord(smsSend.getSndCtnt());
		sendReq.setNotiTypId(smsSend.getNotiTypId());
		sendReq.setSndChnlFlg("S");
		String sndReqSeq = sendReqService.sendRequest(sendReq);
		logger.info("[SMS를 발송하였습니다.]");
		return sndReqSeq;
	}
	

	/**
	 * SMS인증(즉시발송) 발송 key얻기
	 * 
	 * @return String
	 */
	public String getAuthTiSeq(){
		return sendReqMapper.createAuthTiSeq();
	}
	
	/**
	 * SMS인증(즉시발송) insert 
	 * 
	 * @param smsAprv
	 */
	public int addSmsAuthReq(SmsAprv smsAprv){
		return sendReqMapper.insertSmsAuthReq(smsAprv);
	}
	
	/**
	 * SMS인증(즉시발송) 조회
	 * 
	 * @param smsAprv SMS 인증요청 정보
	 * @return SmsAprv
	 */
	public SmsAprv getSmsAuthReq(SmsAprv smsAprv){
		return sendReqMapper.selectSmsAuthReq(smsAprv);
	}

}
