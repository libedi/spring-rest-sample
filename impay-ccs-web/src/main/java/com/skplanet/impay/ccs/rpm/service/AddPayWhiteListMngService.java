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

import com.nhncorp.lucy.security.xss.XssPreventer;
import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.model.PayMphnInfo;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.mapper.CodeNameMapper;
import com.skplanet.impay.ccs.common.service.mapper.UserMapper;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.rpm.model.AddPayWhiteList;
import com.skplanet.impay.ccs.rpm.model.RpmSearch;
import com.skplanet.impay.ccs.rpm.service.mapper.AddPayWhiteListMngMapper;

/**
 * CCS 접수/처리현황관리 
 * - 가산금 W/L 관리 Service
 * @author Junehee, Jang
 *
 */
@Service
public class AddPayWhiteListMngService {
	private final static Logger logger = LoggerFactory.getLogger(AddPayWhiteListMngService.class);
	@Autowired
	private AddPayWhiteListMngMapper addPayWhiteListMngMapper;
	
	@Autowired
	private CodeNameMapper codeNameMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 가산금 W/L 목록 조회
	 * @param rpmSearch 접수/처리 조회 조건
	 * @return Page<AddPayWhiteList>
	 */
	public Page<AddPayWhiteList> getAddPayWhiteList(RpmSearch rpmSearch) {
		final List<AddPayWhiteList> list = new ArrayList<AddPayWhiteList>();
		addPayWhiteListMngMapper.selectAddPayWhiteList(rpmSearch, new ResultHandler<AddPayWhiteList>(){
			@Override
			public void handleResult(ResultContext<? extends AddPayWhiteList> context) {
				AddPayWhiteList nal = context.getResultObject();
				if(StringUtils.isNotEmpty(nal.getRegClfCd())){
					nal.setRegClfCd(codeNameMapper.selectCodeName(nal.getRegClfCd()));	// 등록 구분
				}
				if(StringUtils.isEmpty(nal.getDelDt()))	nal.setDelDt("-");
				if(StringUtils.isEmpty(nal.getDelRsn())) nal.setDelRsn("-");
				list.add(nal);
			}
		});
		int count = addPayWhiteListMngMapper.selectAddPayWhiteCount(rpmSearch);
		return new Page<AddPayWhiteList>(count, list);
	}
	/**
	 * 가산금 W/L 상세 정보 조회
	 * @param wlRegNo WL(White List)등록 번호
	 * @return AddPayWhiteList
	 */
	public AddPayWhiteList getAddPayWhiteListInfo(String wlRegNo) {
		AddPayWhiteList list = addPayWhiteListMngMapper.selectAddPayWhiteListInfo(wlRegNo);
		list.setRegRsn(XssPreventer.unescape(list.getRegRsn())); // 등록사유
		list.setDelRsn(XssPreventer.unescape(list.getDelRsn())); // 삭제사유
		if(StringUtils.isNotEmpty(list.getRegClfCd())){
			list.setRegClfCd(codeNameMapper.selectCodeName(list.getRegClfCd()));// 등록 구분
		}
		if(StringUtils.isEmpty(list.getDelDt())) list.setDelDt("-");
		return list;
	}
	/**
	 * 가산금 W/L 등록
	 * @param addPayWhiteList 미납가산금 W/L 관리 정보
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	public RestResult<String> addPayAddWhiteList(AddPayWhiteList addPayWhiteList, CustomUserDetails userInfo) {
		RestResult<String> result = new RestResult<String>();
		
		PayMphnInfo pmi = new PayMphnInfo();
		pmi.setMphnNo(addPayWhiteList.getMphnNo());
		
		// 입력받은 휴대폰 결제폰정보 내역에 있는 지 유무 확인 및 결제폰 ID 가져오기
		List<PayMphnInfo> list =  userMapper.selectPayMphnInfoList(pmi);
		if(list == null || list.isEmpty()){ 	// 결제폰 정보 내역에 없는 경우	
			result.setMessage("mphNoError");
			return result;
		}else{					// 결제폰 정보 내역에 있는 경우
			addPayWhiteList.setPayMphnId(list.get(0).getPayMphnId()); // 결제폰 ID
			int check = addPayWhiteListMngMapper.selectAddPayCheckData(addPayWhiteList);
			if(check > 0){
				result.setMessage("dataCheck");
				return result;
			}else{
				addPayWhiteList.setRegr(userInfo.getUserNm());
				addPayWhiteList.setLastChgr(userInfo.getUserNm());
				addPayWhiteListMngMapper.insertAddPayWhiteList(addPayWhiteList);
				result.setSuccess(true);
				result.setMessage("success");	
			}
		}
		return result;
	}
	/**
	 * 가산금 W/L 삭제 사유 등록
	 * @param addPayWhiteList 미납가산금 W/L 관리 정보
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	public RestResult<String> updateAddPayDelWhiteList(AddPayWhiteList addPayWhiteList, CustomUserDetails userInfo) {
		RestResult<String> result = new RestResult<String>();
		addPayWhiteList.setDelProc(userInfo.getUserNm());
		addPayWhiteListMngMapper.updateAddPayDelWhiteList(addPayWhiteList);
		result.setSuccess(true);
		result.setMessage("success");
		return result;
	}
}
