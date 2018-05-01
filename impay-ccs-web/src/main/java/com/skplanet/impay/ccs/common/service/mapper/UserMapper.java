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

import com.skplanet.impay.ccs.common.model.CntcInfo;
import com.skplanet.impay.ccs.common.model.PayMphnInfo;
import com.skplanet.impay.ccs.common.model.PayrInfo;
import com.skplanet.impay.ccs.uim.model.UserMng;

/**
 * 사용자 Mapper
 * @author Sangjun, Park
 *
 */
public interface UserMapper {
	
	/**
	 * 연락처 정보 조회
	 * @param cntcSeq
	 * @return CntcInfo
	 */
	CntcInfo selectCntcInfo(int cntcSeq);
	
	/**
	 * 연락처 정보 Seq 얻기
	 * @return int
	 */
	int selectCntcSeq();
	
	/**
	 * 연락처 정보 등록
	 * @param cntcInfo
	 * @return int
	 */
	int insertCntcInfo(CntcInfo cntcInfo);
	
	/**
	 * 연락처 정보 수정
	 * @param cntcInfo
	 * @return int
	 */
	int updateCntcInfo(CntcInfo cntcInfo);
	
	/**
	 * 연락처 정보 삭제
	 * @param cntcInfo
	 * @return int
	 */
	int deleteCntcInfo(CntcInfo cntcInfo);

	/**
	 * 사용자 이름 얻기
	 * @param userSeq
	 * @return String
	 */
	String selectUserNm(String userSeq);
	
	/**
	 * 사용자 ID 얻기
	 * @param userSeq
	 * @return String
	 */
	String selectUserId(String userSeq);

	/**
	 * 결제폰 상세정보 얻기
	 * @param payMphnInfo
	 * @return PayMphnInfo
	 */
	PayMphnInfo selectPayMphnInfo(PayMphnInfo payMphnInfo);
	
	/**
	 * 결제폰 상세정보 리스트 얻기
	 * @param payMphnInfo
	 * @return List
	 */
	List<PayMphnInfo> selectPayMphnInfoList(PayMphnInfo payMphnInfo);

	/**
	 * 결제폰 정보 수정
	 * @param payMphnInfo
	 * @return int
	 */
	int updatePayMphnInfo(PayMphnInfo payMphnInfo);

	/**
	 * 이용자 정보 수정
	 * @param payrInfo
	 * @return int
	 */
	int updatePayrInfo(PayrInfo payrInfo);
	
	/**
	 * 이용자 정보 조회
	 * @param payrSeq
	 * @return PayrInfo
	 */
	PayrInfo selectPayrInfo(String payrSeq);
	
	/**
	 * 관리자 유형 코드별 
	 * 이용자 정보 조회
	 * @param admTypCd
	 * @return List
	 */
	List<UserMng> selectUserTypClfInfo(String admTypCd);
	
	/**
	 * 미거래고객 가상등록
	 * @param payMphnInfo
	 * @return int
	 */
	int insertPayMphnInfo(PayMphnInfo payMphnInfo);
}
