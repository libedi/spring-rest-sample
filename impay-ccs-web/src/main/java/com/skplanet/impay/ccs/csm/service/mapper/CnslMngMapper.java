/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.csm.service.mapper;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.skplanet.impay.ccs.csm.model.CnslDetail;
import com.skplanet.impay.ccs.csm.model.CnslScrpt;
import com.skplanet.impay.ccs.csm.model.CnslSearch;
import com.skplanet.impay.ccs.csm.model.CnslTjurModel;
import com.skplanet.impay.ccs.csm.model.NpayHistoryAddDtl;
import com.skplanet.impay.ccs.csm.model.PayItcptModel;
import com.skplanet.impay.ccs.csm.model.TradeModel;

/**
 * 상담관리 Mapper interface
 * @author Sangjun, Park
 *
 */
public interface CnslMngMapper {

	/**
	 * 거래완료 리스트 조회 (페이징 처리)
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectTradeCompleteListByPaging(CnslSearch cnslSearch, ResultHandler<TradeModel> resultHandler);

	/**
	 * 거래완료 리스트 카운트 조회
	 * @param cnslSearch
	 * @return int
	 */
	int selectTradeCompleteListCount(CnslSearch cnslSearch);
	
	/** 
	 * 거래완료 리스트
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectTradeCompleteList(CnslSearch cnslSearch, ResultHandler<TradeModel> resultHandler);

	/**
	 * 전체 상담이력 리스트 조회 (페이징 처리)
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectCnslDetailList(CnslSearch cnslSearch, ResultHandler<CnslDetail> resultHandler);

	/**
	 * 전체 상담이력 카운트 조회
	 * @param cnslSearch
	 * @return int
	 */
	int selectCnslDetailListCount(CnslSearch cnslSearch);

	/**
	 * 상담내용 조회
	 * @param rcptNo
	 * @return CnslDetail
	 */
	CnslDetail selectCnslDetail(String rcptNo);

	/**
	 * 미납이력 및 가산금 부과내역 목록 카운트
	 * @author Junehee, Jang
	 * @param cnslSearch
	 * @return
	 */
	int selectNpayHistoryCount(CnslSearch cnslSearch);
	
	/**
	 * 미납이력 및 가산금 부과내역 목록 조회
	 * @author Junehee, Jang
	 * @param cnslSearch
	 * @param resultHandler 
	 * @return
	 */
	void selectNpayHistoryList(CnslSearch cnslSearch, ResultHandler<NpayHistoryAddDtl> resultHandler);
	
	/**
	 * 상담내역 등록
	 * @param cnslDetail
	 * @return int
	 */
	int insertCnslDtl(CnslDetail cnslDetail);

	/**
	 * 상담내역 수정
	 * @param cnslDetail
	 * @return int
	 */
	int updateCnslDtl(CnslDetail cnslDetail);

	/**
	 * 상담내역 변경이력 최종건 조회
	 * @param rcptNo
	 * @return CnslDetail
	 */
	CnslDetail selectCnslChgLast(String rcptNo);
	
	/**
	 * 상담내역 변경이력 등록
	 * @param cnslDetail
	 * @return int
	 */
	int insertCnslChg(CnslDetail cnslDetail);

	/**
	 * 상담내역 결제연동 등록
	 * @param cnslDetail
	 * @return int
	 */
	int insertCnslPayRel(CnslDetail cnslDetail);

	/**
	 * 상담내역 변경이력 리스트 조회 (페이징처리)
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectCnslChgListByPaging(CnslSearch cnslSearch, ResultHandler<CnslDetail> resultHandler);

	/**
	 * 상담내역 변경이력 카운트 조회
	 * @param cnslSearch
	 * @return int
	 */
	int selectCnslChgListCount(CnslSearch cnslSearch);
	
	/**
	 * 거래시도 리스트 조회 (페이징처리)
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectTradeTryListByPaging(CnslSearch cnslSearch, ResultHandler<TradeModel> resultHandler);
	
	/**
	 * 거래시도 리스트 카운트 조회
	 * @param cnslSearch
	 * @return int
	 */
	int selectTradeTryListCount(CnslSearch cnslSearch);
	
	/**
	 * 거래시도 리스트 조회
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectTradeTryList(CnslSearch cnslSearch, ResultHandler<TradeModel> resultHandler);

	/**
	 * 결제차단내역 리스트 조회 (페이징처리)
	 * @param cnslSearch
	 * @param resultHandler
	 */
	void selectPayItcptListByPaging(CnslSearch cnslSearch, ResultHandler<PayItcptModel> resultHandler);
	
	/**
	 * 결제차단내역 리스트 카운트 조회
	 * @param cnslSearch
	 * @return int
	 */
	int selectPayItcptListCount(CnslSearch cnslSearch);
	
	/**
	 * 결제차단이력 단건 조회
	 * @param itcptReqSeq
	 * @return PayItcptModel
	 */
	PayItcptModel selectPayItcpt(String itcptReqSeq);

	/**
	 * 결제차단 등록
	 * @param payItcptModel
	 * @return int
	 */
	int insertPayItcpt(PayItcptModel payItcptModel);

	/**
	 * 결제차단 해제
	 * @param payItcptModel
	 * @return int
	 */
	int updatePayItcpt(PayItcptModel payItcptModel);

	/**
	 * 처리내용 이관
	 * @param cnslTjurModel
	 * @return int
	 */
	int updateCnslCpTjur(CnslTjurModel cnslTjurModel);

	/**
	 * 문서보관함 리스트 조회
	 * @return List<String>
	 */
	List<String> selectSmsDocumentList();

	/**
	 * 상담 시나리오 리스트 전체조회
	 * @param srchText
	 * @return List
	 */
	List<CnslScrpt> selectCnslCrspScrptAll();
	
	/**
	 * 상담 시나리오 리스트 조회
	 * @param srchText
	 * @return List
	 */
	List<CnslScrpt> selectCnslCrspScrpt(String srchText);

	/**
	 * 상담이력 수정
	 * @param cnslDetail
	 * @return int
	 */
	int updateCnslChg(CnslDetail cnslDetail);

	/**
	 * 이관처리 이력 등록
	 * @param cnslTjurModel
	 */
	int insertCnslCpTjurHist(CnslTjurModel cnslTjurModel);
	
	/**
	 * 이관처리 이력 조회
	 * @param rcptNo
	 * @return List
	 */
	List<CnslTjurModel> selectCnslCpTjurHist(String rcptNo);
}
