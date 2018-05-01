/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.service.mapper;

import java.util.List;

import com.skplanet.impay.ccs.common.model.NotiTypeModel;
import com.skplanet.impay.ccs.common.model.SendReqModel;
import com.skplanet.impay.ccs.common.model.SendResult;
import com.skplanet.impay.ccs.common.model.SmsAprv;

/**
 * 발송 요청 Mapper interface
 * @author Sangjun, Park
 *
 */
public interface SendReqMapper {

	/**
	 * 발송 요청
	 * @param sendReqModel
	 */
	void insertSendReqDtl(SendReqModel sendReqModel);

	/**
	 * 발송 요청건 수정
	 * @param sendReqModel
	 */
	void updateSendReqDtl(SendReqModel sendReqModel);
	
	/**
	 * 발송 요청건 조회
	 * @param sndReqSeq
	 */
	SendReqModel selectSendReqDtl(String sndReqSeq);
	
	/**
	 * 발송 결과 건 조회
	 * @param sndReqSeq
	 * @return SendResult
	 */
	SendResult selectSndRsltDtl(String sndReqSeq);

	/**
	 * 발송 결과 리스트 조회
	 * @param sendResult
	 * @return List
	 */
	List<SendResult> selectSndRsltDtlList(SendResult sendResult);

	/**
	 * 통보 유형 조회
	 * @param notiTypId
	 * @return NotiTypeModel
	 */
	NotiTypeModel selectNotiTyp(String notiTypId);
	
	/**
	 * 통보 유형 리스트 조회
	 * @param notiTypeModel
	 * @return List
	 */
	List<NotiTypeModel> selectNotiTypList(NotiTypeModel notiTypeModel);

	/**
	 * 수신그룹 연락처를 포함한 통보 유형 조회
	 * @param notiTypId
	 * @return NotiTypeModel
	 */
	NotiTypeModel selectNotiTypWithCntcInfo(String notiTypId);

	/**
	 * SMS 즉시발송
	 * @param smsAprv
	 */
	int insertSmsAuthReq(SmsAprv smsAprv);
	
	/**
	 * SMS 즉시발송 SEQ 채번
	 * @param  none
	 */
	String createAuthTiSeq();
	
	/**
	 * SMS 조회
	 * @param smsAprv
	 * @return
	 */
	SmsAprv selectSmsAuthReq(SmsAprv smsAprv);
	
}
