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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.impay.ccs.common.model.CntcInfo;
import com.skplanet.impay.ccs.common.model.PayMphnInfo;
import com.skplanet.impay.ccs.common.model.PayrInfo;
import com.skplanet.impay.ccs.common.service.mapper.UserMapper;
import com.skplanet.impay.ccs.uim.model.UserMng;

/**
 * 사용자 Service
 * @author Sangjun, Park
 *
 */
@Service
public class UserService {
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 연락처 정보 조회
	 * @param cntcSeq 연락처 순번
	 * @return CntcInfo
	 */
	public CntcInfo getCntcInfo(int cntcSeq){
		return this.userMapper.selectCntcInfo(cntcSeq);
	}
	
	/**
	 * 연락처 정보 seq 얻기
	 * @return int
	 */
	public int getCntcSeq(){
		return this.userMapper.selectCntcSeq();
	}
	
	/**
	 * 연락처 정보 등록
	 * @param cntcInfo 사용자 - 연락처 정보
	 * @return CntcInfo
	 */
	@Transactional
	public int addCntcInfo(CntcInfo cntcInfo){
		return this.userMapper.insertCntcInfo(cntcInfo);
	}
	
	/**
	 * 연락처 정보 수정
	 * @param cntcInfo 사용자 - 연락처 정보
	 * @return int
	 */
	@Transactional
	public int setCntcInfo(CntcInfo cntcInfo){
		return this.userMapper.updateCntcInfo(cntcInfo);
	}
	
	/**
	 * 연락처 정보 삭제
	 * @param cntcInfo 사용자 - 연락처 정보
	 * @return int
	 */
	@Transactional
	public int removeCntcInfo(CntcInfo cntcInfo){
		return this.userMapper.deleteCntcInfo(cntcInfo);
	}
	
	/**
	 * 결제폰 상세정보 얻기
	 * @param payMphnInfo 결제폰 정보
	 * @return PayMphnInfo
	 */
	public PayMphnInfo getPayMphnInfo(PayMphnInfo payMphnInfo){
		return userMapper.selectPayMphnInfo(payMphnInfo);
	};
	
	/**
	 * 이용자 정보 조회
	 * @param payrSeq 이용자 순번
	 * @return PayrInfo
	 */
	public PayrInfo getPayrInfo(String payrSeq){
		return userMapper.selectPayrInfo(payrSeq);
	}
	/**
	 * 관리자 유형 코드별 
	 * 이용자 정보 조회
	 * @param admTypCd 관리자 유형 코드
	 * @return List<UserMng>
	 */
	public List<UserMng> getUserTypClfInfo(String admTypCd) {
		return userMapper.selectUserTypClfInfo(admTypCd);
	}
		 
}
