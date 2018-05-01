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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.model.PayMphnInfo;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.mapper.CodeNameMapper;
import com.skplanet.impay.ccs.common.service.mapper.UserMapper;
import com.skplanet.impay.ccs.csm.model.CnslSearch;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.rpm.model.PayBackRcpt;
import com.skplanet.impay.ccs.rpm.model.PayBackSearch;
import com.skplanet.impay.ccs.rpm.service.mapper.PayBackRcptMapper;

/**
 * CCS 접수/처리현황관리 
 * - 환불접수 Service
 * @author Junehee, Jang
 *
 */
@Service
public class PayBackRcptService {
	
	private static final Logger logger = LoggerFactory.getLogger(PayBackRcptService.class);
	
	@Autowired
	private PayBackRcptMapper payBackRcptMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired 
	private CodeNameMapper codeNameMapper;
	
	/**
	 * 환불 접수 목록 조회
	 * @param payBackSearch 환불 접수 조회 조건
	 * @return Page<PayBackRcpt>
	 */
	public Page<PayBackRcpt> getPayBackRcptList(PayBackSearch payBackSearch) {
		final List<PayBackRcpt> list = new ArrayList<PayBackRcpt>();
		payBackRcptMapper.selectPayBackRcptList(payBackSearch, new ResultHandler<PayBackRcpt>(){
			@Override
			public void handleResult(ResultContext<? extends PayBackRcpt> context) {
				PayBackRcpt payBackRcpt = context.getResultObject();
				PayMphnInfo pmi = new PayMphnInfo();
				pmi.setPayMphnId(payBackRcpt.getPayMphnId());
				
				List<PayMphnInfo> payMphnInfoList = userMapper.selectPayMphnInfoList(pmi);
				if(!payMphnInfoList.isEmpty()){
					payBackRcpt.setMphnNo(userMapper.selectPayMphnInfoList(pmi).get(0).getMphnNo());			// 휴대폰 번호
				}
				payBackRcpt.setBankNm(codeNameMapper.selectCodeName(payBackRcpt.getBankCd()));		// 은행 명
				payBackRcpt.setCommcClf(codeNameMapper.selectCodeName(payBackRcpt.getCommcClf()));	// 이통사 명
				payBackRcpt.setPybkTypCd(codeNameMapper.selectCodeName(payBackRcpt.getPybkTypCd()));// 환불 구분
				list.add(payBackRcpt);
			}
		});
		int count = payBackRcptMapper.selectPayBackRcptCount(payBackSearch);
		return new Page<PayBackRcpt>(count, list);
	}
	/**
	 * 환불 접수 상세 조회
	 * @param pybkRcptNo  환불 접수 번호
	 * @return PayBackRcpt
	 */
	public PayBackRcpt getPayBackRcptInfo(String pybkRcptNo) {
		PayBackRcpt pbr = payBackRcptMapper.selectPayBackRcptInfo(pybkRcptNo);
		pbr.setBankNm(codeNameMapper.selectCodeName(pbr.getBankCd()));
		pbr.setCommcClf(codeNameMapper.selectCodeName(pbr.getCommcClf()));
		pbr.setPybkTypCd(codeNameMapper.selectCodeName(pbr.getPybkTypCd()));
		
		return pbr;
	}
	/**
	 * 환불 접수 등록
	 * @param payBackRcpt 환불 접수 번호
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	public RestResult<String> addPayBackRcpt(PayBackRcpt payBackRcpt, CustomUserDetails userInfo) {
		RestResult<String> result = new RestResult<String>();
		payBackRcpt.setPayAmt(payBackRcpt.getPayAmt().replace(",", ""));
		if(StringUtils.isNotEmpty(payBackRcpt.getPybkReqDd())){
			payBackRcpt.setPybkReqDd(payBackRcpt.getPybkReqDd().replace(".", ""));
		};
		payBackRcpt.setRegr(userInfo.getUserNm());
		payBackRcpt.setLastChgr(userInfo.getUserNm());
		
		payBackRcpt.setBankNm(codeNameMapper.selectCodeName(payBackRcpt.getBankCd()));
		
		PayMphnInfo pmi = new PayMphnInfo();
		pmi.setMphnNo(payBackRcpt.getMphnNo());
		pmi.setCommcClf(payBackRcpt.getCommcClf());
		
		List<PayMphnInfo> list = userMapper.selectPayMphnInfoList(pmi);
		if(!list.isEmpty()){
			payBackRcpt.setPayMphnId(userMapper.selectPayMphnInfoList(pmi).get(0).getPayMphnId());	// 휴대폰 ID
			
			String commcClf = userMapper.selectPayMphnInfoList(pmi).get(0).getCommcClf();
			if(commcClf.equals(payBackRcpt.getCommcClf())){
				payBackRcptMapper.insertPayBackRcpt(payBackRcpt);
				result.setSuccess(true);
				result.setMessage("success");
			}
		}else{
			result.setSuccess(false);
			result.setMessage("commcClfError");
		}
		return result;
	}
	/**
	 * 환불 접수 
	 * CHECK 된 ROW 삭제여부 Y 수정  
	 * @param cnslSearch 상담관리 조회 조건
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	public RestResult<String> deletePayBackRcpt(CnslSearch cnslSearch, CustomUserDetails userInfo) {
		RestResult<String> result = new RestResult<String>();
		cnslSearch.setCcsEmployee(userInfo.getUserId());
		payBackRcptMapper.deletePayBackRcpt(cnslSearch);
		result.setSuccess(true);
		result.setMessage("success");
		return result;
	}
}
