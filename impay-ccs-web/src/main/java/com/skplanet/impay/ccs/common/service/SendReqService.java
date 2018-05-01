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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.impay.ccs.common.model.NotiTypeModel;
import com.skplanet.impay.ccs.common.model.SendReqModel;
import com.skplanet.impay.ccs.common.model.SendResult;
import com.skplanet.impay.ccs.common.service.mapper.SendReqMapper;
import com.skplanet.impay.ccs.framework.exception.InvalidParameterException;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;


/**
 * 발송 요청 Service
 * 
 * @author Sangjun, Park
 *
 */
@Service
public class SendReqService {
	
	private static Logger logger = LoggerFactory.getLogger(SendReqService.class);
	
	private final String IDVD_NOTI_TYPE_ID = "NTIDVD";
	
	@Autowired
	private SendReqMapper sendReqMapper;
	
	@Autowired
	private MessageByLocaleServiceImpl message;
	
	/**
	 * 발송 요청 (SMS, email)
	 * @param sendReqModel 발송 요청 정보
	 * @return String 발송요청순번
	 */
	@Transactional
	public String sendRequest(SendReqModel sendReqModel){
		// 세션 사용자정보 가져오기
		CustomUserDetails userInfo = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		sendReqModel.setRegr(userInfo.getUserSeq());
		sendReqModel.setLastChgr(userInfo.getUserSeq());
		// 개별발송건인 경우, 개별통보유형 셋팅
		if(StringUtils.equals("Y", sendReqModel.getIdvdSndYn())){
			// 개별 발송 휴대폰 번호 또는 이메일 번호 입력 필수
			if(StringUtils.isEmpty(sendReqModel.getIdvdMphnNo()) && StringUtils.isEmpty(sendReqModel.getIdvdEmail())){
				throw new InvalidParameterException(message.getMessage("common.exception.invalidParameter"));
			}
			// 발송채널구분값 셋팅
			if(StringUtils.isEmpty(sendReqModel.getSndChnlFlg())){
				if(StringUtils.isEmpty(sendReqModel.getIdvdMphnNo())){
					sendReqModel.setSndChnlFlg("M");
				} else if(StringUtils.isEmpty(sendReqModel.getIdvdEmail())){
					sendReqModel.setSndChnlFlg("S");
				} else {
					sendReqModel.setSndChnlFlg("A");
				}
			}
			sendReqModel.setNotiTypId(IDVD_NOTI_TYPE_ID);
		}
		this.sendReqMapper.insertSendReqDtl(sendReqModel);
		logger.info("[발송 요청 성공] :: SND_REQ_SEQ = {}", sendReqModel.getSndReqSeq());
		return sendReqModel.getSndReqSeq();
	}
	
	/**
	 * 발송 요청건 수정
	 * @param sendReqModel 발송 요청 정보
	 */
	@Transactional
	public void modifyRequest(SendReqModel sendReqModel){
		//세션 사용자정보 가져오기
		CustomUserDetails userInfo = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		sendReqModel.setLastChgr(userInfo.getUserSeq());
		this.sendReqMapper.updateSendReqDtl(sendReqModel);
	}
	
	/**
	 * 발송 결과 건 조회
	 * @param sndReqSeq 발송 결과 번호
	 * @return SendResult 
	 */
	public SendResult getSendResult(String sndReqSeq){
		return this.sendReqMapper.selectSndRsltDtl(sndReqSeq);
	}
	
	/**
	 * 발송 결과 리스트 조회
	 * @param sendResult 발송 결과 정보
	 * @return List<SendResult>
	 */
	public List<SendResult> getSendResultList(SendResult sendResult){
		return this.sendReqMapper.selectSndRsltDtlList(sendResult);
	}
	
	/**
	 * 통보 유형 조회
	 * @param notiTypId 통보유형ID
	 * @return NotiTypeModel
	 */
	public NotiTypeModel getNotiType(String notiTypId){
		return this.sendReqMapper.selectNotiTyp(notiTypId);
	}
	
	/**
	 * 통보 유형 리스트 조회
	 * @param notiTypeModel 통보 유형 정보
	 * @return List<NotiTypeModel>
	 */
	public List<NotiTypeModel> getNotiTypeList(NotiTypeModel notiTypeModel){
		return this.sendReqMapper.selectNotiTypList(notiTypeModel);
	}
	
	/**
	 * 수신그룹 연락처를 포함한 통보 유형 조회
	 * @param notiTypId 통보유형ID
	 * @return NotiTypeModel
	 */
	public NotiTypeModel getNotiTypeWithCntcInfo(String notiTypId){
		return this.sendReqMapper.selectNotiTypWithCntcInfo(notiTypId);
	}
}
