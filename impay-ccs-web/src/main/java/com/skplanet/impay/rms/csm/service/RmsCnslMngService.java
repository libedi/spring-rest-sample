/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.rms.csm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.impay.ccs.common.service.mapper.CodeNameMapper;
import com.skplanet.impay.ccs.csm.model.CnslSearch;
import com.skplanet.impay.ccs.framework.exception.BusinessException;
import com.skplanet.impay.ccs.framework.transaction.RmTransactional;
import com.skplanet.impay.framework.log.idms.IdmsCustomerDataAccessLog;
import com.skplanet.impay.rms.constants.RmsComEnum;
import com.skplanet.impay.rms.csm.model.RmsChrgRcptAcc;
import com.skplanet.impay.rms.csm.model.RmsChrgRcptMon;
import com.skplanet.impay.rms.csm.model.RmsCpBLInq;
import com.skplanet.impay.rms.csm.model.RmsCustChg;
import com.skplanet.impay.rms.csm.model.RmsDftNum;
import com.skplanet.impay.rms.csm.model.RmsFraudReliefReg;
import com.skplanet.impay.rms.csm.model.RmsRmBlkReliefReg;
import com.skplanet.impay.rms.csm.model.RmsRmChangeHis;
import com.skplanet.impay.rms.csm.service.mapper.RmsCnslMngMapper;
import com.skplanet.impay.rms.util.RmsAuthUtil;
import com.skplanet.impay.rms.util.RmsMessageUtil;

/**
 * 상담 관리 (RMS) Service
 * @author Sunghee Park
 *
 */
@Service
public class RmsCnslMngService {
	private final static Logger logger = LoggerFactory.getLogger(RmsCnslMngService.class);
	
	@Autowired
	private RmsMessageUtil rmsMessageUtil;
	
	@Autowired
	private CodeNameMapper codeNameMapper;
	
	@Autowired
	private RmsCnslMngMapper rmsCnslMngMapper;
	
	/**
	 * 청구/수납(누적) 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	public List<RmsChrgRcptAcc> getChrgRcptAccList(CnslSearch cnslSearch) {
		final List<RmsChrgRcptAcc> resultList = new ArrayList<>();
		
		rmsCnslMngMapper.selectChrgRcptAccList(cnslSearch, new ResultHandler<RmsChrgRcptAcc>() {
			private int idx = 1;
			
			@Override
			public void handleResult(ResultContext<? extends RmsChrgRcptAcc> context) {
				RmsChrgRcptAcc row = context.getResultObject();
				row.setIdx(idx++);
				row.setCommcClfNm(selectCodeName(row.getCommcClf()));
				resultList.add(row);
			}
		});
		
		// 고객정보조회 로그 수집
		if (resultList.size() > 0) {
			IdmsCustomerDataAccessLog log = new IdmsCustomerDataAccessLog(new Date(), RmsAuthUtil.getLocalServerIp(), RmsAuthUtil.getUserId(), RmsAuthUtil.getUserInfo().getClntIp(), resultList.get(0).getMphnNo(), "mphnNo", "RMS0006", "10001", resultList.size());
			logger.info(log.toString());
		}
		
		return resultList;
	}
	
	/**
	 * 청구/수납(월별) 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	public List<RmsChrgRcptMon> getChrgRcptMonList(CnslSearch cnslSearch) {
		final List<RmsChrgRcptMon> resultList = new ArrayList<>();
		
		rmsCnslMngMapper.selectChrgRcptMonList(cnslSearch, new ResultHandler<RmsChrgRcptMon>() {
			private int idx = 1;
			
			@Override
			public void handleResult(ResultContext<? extends RmsChrgRcptMon> context) {
				RmsChrgRcptMon row = context.getResultObject();
				row.setIdx(idx++);
				row.setCommcClfNm(selectCodeName(row.getCommcClf()));
				resultList.add(row);
			}
		});
		
		// 고객정보조회 로그 수집
		if (resultList.size() > 0) {
			IdmsCustomerDataAccessLog log = new IdmsCustomerDataAccessLog(new Date(), RmsAuthUtil.getLocalServerIp(), RmsAuthUtil.getUserId(), RmsAuthUtil.getUserInfo().getClntIp(), resultList.get(0).getMphnNo(), "mphnNo", "RMS0007", "10001", resultList.size());
			logger.info(log.toString());
		}
		
		return resultList;
	}
	
	/**
	 * 수납 회차별 미납횟수 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	public List<RmsDftNum> getNpayCntList(CnslSearch cnslSearch) {
		final List<RmsDftNum> resultList = new ArrayList<>();
		
		rmsCnslMngMapper.selectNpayCntList(cnslSearch, new ResultHandler<RmsDftNum>() {
			private int idx = 1;
			
			@Override
			public void handleResult(ResultContext<? extends RmsDftNum> context) {
				RmsDftNum row = context.getResultObject();
				row.setIdx(idx++);
				row.setCommcClfNm(selectCodeName(row.getCommcClf()));
				resultList.add(row);
			}
		});
		
		// 고객정보조회 로그 수집
		if (resultList.size() > 0) {
			IdmsCustomerDataAccessLog log = new IdmsCustomerDataAccessLog(new Date(), RmsAuthUtil.getLocalServerIp(), RmsAuthUtil.getUserId(), RmsAuthUtil.getUserInfo().getClntIp(), resultList.get(0).getMphnNo(), "mphnNo", "RMS0008", "10001", resultList.size());
			logger.info(log.toString());
		}
		
		return resultList;
	}
	
	/**
	 * 미납금액 상세 조회
	 * @param params
	 * @return
	 */
	public RmsDftNum getNpayAmtDetail(RmsDftNum params) {
		return rmsCnslMngMapper.selectNpayAmtDetail(params);
	}
	
	/**
	 * RM 차단/해제 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	public List<RmsRmBlkReliefReg> getRmBlkReliefList(CnslSearch cnslSearch) {
		final List<RmsRmBlkReliefReg> resultList = new ArrayList<>();
		
		rmsCnslMngMapper.selectRmBlkReliefList(cnslSearch, new ResultHandler<RmsRmBlkReliefReg>() {
			private int idx = 1;
			
			@Override
			public void handleResult(ResultContext<? extends RmsRmBlkReliefReg> context) {
				RmsRmBlkReliefReg row = context.getResultObject();
				row.setIdx(idx++);
				row.setApplyStatusNm(rmsMessageUtil.getMessage("rms.csm.rmsCnslMng.rmBlkReliefReg.applyStatus.", row.getApplyStatus()));
				row.setReliefClfNm(rmsMessageUtil.getMessage("rms.csm.rmsCnslMng.rmBlkReliefReg.reliefClfCd.", row.getReliefClfCd()));
				row.setRelsRsnNm(selectCodeName(row.getRelsRsnCd()));
				resultList.add(row);
			}
		});
		
		// 고객정보조회 로그 수집
		if (resultList.size() > 0) {
			IdmsCustomerDataAccessLog log = new IdmsCustomerDataAccessLog(new Date(), RmsAuthUtil.getLocalServerIp(), RmsAuthUtil.getUserId(), RmsAuthUtil.getUserInfo().getClntIp(), resultList.get(0).getMphnNo(), "mphnNo", "RMS0009", "10001", resultList.size());
			logger.info(log.toString());
		}
		
		return resultList;
	}
	
	/**
	 * RM 해제
	 * @param param
	 */
	@RmTransactional
	public void relieveRm(RmsRmBlkReliefReg param) {
		String userId = RmsAuthUtil.getUserId();
		
		// 고객번호로 해제하려는 경우, 휴대폰번호 파라미터 없앰
		if (StringUtils.equals(param.getReliefClfCd(), RmsComEnum.RELIEF_CLF_CD_PAYR_SEQ.value())) {
			param.setMphnNo("");
		}
		
		// 고객의 현재 진행중인 RM 해제 목록 조회
		List<RmsRmBlkReliefReg> currentRmReliefList = rmsCnslMngMapper.selectCurrentRmReliefList(param);
		
		long currentTimeMillis = getCurrentTimeMillis();
		
		if (currentRmReliefList != null && currentRmReliefList.size() > 0) {
			for (RmsRmBlkReliefReg currentRmRelief : currentRmReliefList) {
				// 휴대폰번호로 해제하려는데, 고객번호로 해제중인 데이터가 있는 경우
				if (StringUtils.equals(param.getReliefClfCd(), RmsComEnum.RELIEF_CLF_CD_MPHN_NO.value()) &&
					StringUtils.equals(currentRmRelief.getReliefClfCd(), RmsComEnum.RELIEF_CLF_CD_PAYR_SEQ.value())) {
					// "고객번호 기준으로 해제가 등록되어 있어, 휴대폰번호 기준으로 해제할 수 없습니다."
					throw new BusinessException(rmsMessageUtil.getMessage("rms.csm.rmsCnslMng.rmBlkReliefReg.save.rmRelief.payrSeqReliefExists"));
				}
				
				// 고객번호로 해제하려는 경우, 또는 휴대폰번호가 일치하는 해제 데이터인 경우
				if (StringUtils.equals(param.getReliefClfCd(), RmsComEnum.RELIEF_CLF_CD_PAYR_SEQ.value()) ||
					StringUtils.equals(param.getMphnNo(), currentRmRelief.getMphnNo())) {
					// 기존 RM 해제 데이터의 종료일시를 현재시간-1초로 변경하여 차단
					currentRmRelief.setEdDt(formatDate(currentTimeMillis - 1000));
					currentRmRelief.setRmk(mergeRmk(currentRmRelief.getRmk(), param.getRmk()));
					currentRmRelief.setUpdtId(userId);
					rmsCnslMngMapper.updateRmBlkReliefCust(currentRmRelief);
					
					// 이력 등록
					insertCustChg(currentRmRelief, RmsComEnum.BLK_RELS_CLF_BLOCK.value(), userId);
				}
			}
		}
		
		// 새로운 RM 해제 데이터 등록 (시작시간은 현재시간, 종료시간은 파라미터로 주어진 시간)
		param.setStDt(formatDate(currentTimeMillis));
		param.setEdDt(param.getEdDt()+" "+RmsComEnum.MAX_TIME.value());
		param.setRmk(StringUtils.defaultString(param.getRmk()));
		param.setInptId(userId);
		rmsCnslMngMapper.insertRmBlkReliefCust(param);
		
		// 이력 등록
		insertCustChg(param, RmsComEnum.BLK_RELS_CLF_RELIEF.value(), userId);
	}
	
	/**
	 * RM 차단(해제취소)
	 * @param param
	 */
	@RmTransactional
	public void blockRm(RmsRmBlkReliefReg param) {
		String userId = RmsAuthUtil.getUserId();
		
		// 고객번호로 차단하려는 경우, 휴대폰번호 파라미터 없앰
		if (StringUtils.equals(param.getReliefClfCd(), RmsComEnum.RELIEF_CLF_CD_PAYR_SEQ.value())) {
			param.setMphnNo("");
		}
		
		// 고객의 현재 진행중인 RM 해제 목록 조회
		List<RmsRmBlkReliefReg> currentRmReliefList = rmsCnslMngMapper.selectCurrentRmReliefList(param);
		
		int blkCount = 0;
		
		long currentTimeMillis = getCurrentTimeMillis();
		
		if (currentRmReliefList != null && currentRmReliefList.size() > 0) {
			for (RmsRmBlkReliefReg currentRmRelief : currentRmReliefList) {
				// 휴대폰번호로 차단하려는데, 고객번호로 해제중인 데이터가 있는 경우
				if (StringUtils.equals(param.getReliefClfCd(), RmsComEnum.RELIEF_CLF_CD_MPHN_NO.value()) &&
					StringUtils.equals(currentRmRelief.getReliefClfCd(), RmsComEnum.RELIEF_CLF_CD_PAYR_SEQ.value())) {
					// "고객번호 기준으로 해제가 등록되어 있어, 휴대폰번호 기준으로 해제취소할 수 없습니다."
					throw new BusinessException(rmsMessageUtil.getMessage("rms.csm.rmsCnslMng.rmBlkReliefReg.save.rmBlk.payrSeqReliefExists"));
				}
				
				// 고객번호로 차단하려는데, 휴대폰번호로 해제중인 데이터가 있는 경우
				if (StringUtils.equals(param.getReliefClfCd(), RmsComEnum.RELIEF_CLF_CD_PAYR_SEQ.value()) &&
					StringUtils.equals(currentRmRelief.getReliefClfCd(), RmsComEnum.RELIEF_CLF_CD_MPHN_NO.value())) {
					// "휴대폰번호 기준으로 해제가 등록되어 있어, 고객번호 기준으로 해제취소할 수 없습니다."
					throw new BusinessException(rmsMessageUtil.getMessage("rms.csm.rmsCnslMng.rmBlkReliefReg.save.rmBlk.mphnNoReliefExists"));
				}
				
				// 고객번호로 차단하려는 경우, 또는 휴대폰번호가 일치하는 해제 데이터인 경우
				if (StringUtils.equals(param.getReliefClfCd(), RmsComEnum.RELIEF_CLF_CD_PAYR_SEQ.value()) ||
					StringUtils.equals(param.getMphnNo(), currentRmRelief.getMphnNo())) {
					// 기존 RM 해제 데이터의 종료일시를 현재시간-1초로 변경하여 차단
					currentRmRelief.setEdDt(formatDate(currentTimeMillis - 1000));
					currentRmRelief.setRmk(mergeRmk(currentRmRelief.getRmk(), param.getRmk()));
					currentRmRelief.setUpdtId(userId);
					rmsCnslMngMapper.updateRmBlkReliefCust(currentRmRelief);
					
					// 이력 등록
					insertCustChg(currentRmRelief, RmsComEnum.BLK_RELS_CLF_BLOCK.value(), userId);
					
					blkCount++;
				}
			}
		}
		
		if (blkCount == 0) {
			// "현재 해제중이 아닙니다."
			throw new BusinessException(rmsMessageUtil.getMessage("rms.csm.rmsCnslMng.rmBlkReliefReg.save.rmBlk.noRelief"));
		}
	}
	
	/**
	 * RM 차단/해제에 대한 이력 등록
	 * @param rmsRmBlkReliefReg RM 차단/해제 VO
	 * @param blkRelsClf 차단해제구분
	 * @param inptId 입력자ID
	 * @return
	 */
	private int insertCustChg(RmsRmBlkReliefReg rmsRmBlkReliefReg, String blkRelsClf, String inptId) {
		RmsCustChg custChg = new RmsCustChg();
		custChg.setPayrSeq(rmsRmBlkReliefReg.getPayrSeq());
		custChg.setMphnNo(rmsRmBlkReliefReg.getMphnNo());
		custChg.setRegClf(RmsComEnum.CUST_CHG_REG_CLF_RM.value());
		custChg.setCustInfoChgCd(rmsRmBlkReliefReg.getBlkReliefCd());
		custChg.setBlkRelsClf(blkRelsClf);
		custChg.setStDt(rmsRmBlkReliefReg.getStDt());
		custChg.setEdDt(rmsRmBlkReliefReg.getEdDt());
		custChg.setChgRsnCd(rmsRmBlkReliefReg.getRelsRsnCd());
		custChg.setRmk(rmsRmBlkReliefReg.getRmk());
		custChg.setInptId(inptId);
		return rmsCnslMngMapper.insertCustChg(custChg);
	}
	
	/**
	 * 불량고객 차단 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	public List<RmsFraudReliefReg> getFraudBlkList(CnslSearch cnslSearch) {
		final List<RmsFraudReliefReg> resultList = new ArrayList<>();
		
		rmsCnslMngMapper.selectFraudBlkList(cnslSearch, new ResultHandler<RmsFraudReliefReg>() {
			private int idx = 1;
			
			@Override
			public void handleResult(ResultContext<? extends RmsFraudReliefReg> context) {
				RmsFraudReliefReg row = context.getResultObject();
				row.setIdx(idx++);
				resultList.add(row);
			}
		});
		
		return resultList;
	}
	
	/**
	 * 불량고객 해제
	 * @param param
	 */
	@RmTransactional
	public void relieveFraud(RmsFraudReliefReg param) {
		String userId = RmsAuthUtil.getUserId();
		
		// 불량고객 차단중인 데이터 조회
		RmsFraudReliefReg currentFraudBlk = rmsCnslMngMapper.selectCurrentFraudBlk(param);
		
		if (currentFraudBlk == null) {
			// 현재 차단중이 아닙니다.
			throw new BusinessException(rmsMessageUtil.getMessage("rms.csm.rmsCnslMng.fraudReliefReg.save.fraudRelief.noBlk"));
		}
		
		// 불량고객 차단중인 데이터의 종료일시를 현재시간-1초로 변경하여 해제
		currentFraudBlk.setEdDt(formatDate(getCurrentTimeMillis() - 1000));
		currentFraudBlk.setRelsRsnCd(param.getRelsRsnCd());
		currentFraudBlk.setRmk(mergeRmk(currentFraudBlk.getRmk(), param.getRmk()));
		currentFraudBlk.setUpdtId(userId);
		rmsCnslMngMapper.updateFraudCust(currentFraudBlk);
		
		// 이력 등록
		insertCustChg(currentFraudBlk, userId);
	}
	
	/**
	 * 불량고객 해제에 대한 이력 등록
	 * @param rmsFraudReliefReg 불량고객 해제 VO
	 * @param inptId 입력자ID
	 * @return
	 */
	private int insertCustChg(RmsFraudReliefReg rmsFraudReliefReg, String inptId) {
		RmsCustChg custChg = new RmsCustChg();
		custChg.setPayrSeq(rmsFraudReliefReg.getPayrSeq());
		custChg.setMphnNo("");
		custChg.setRegClf(RmsComEnum.CUST_CHG_REG_CLF_FRAUD.value());
		custChg.setCustInfoChgCd(rmsFraudReliefReg.getFraudClfCd());
		custChg.setBlkRelsClf(RmsComEnum.BLK_RELS_CLF_RELIEF.value());
		custChg.setStDt(rmsFraudReliefReg.getStDt());
		custChg.setEdDt(rmsFraudReliefReg.getEdDt());
		custChg.setChgRsnCd(rmsFraudReliefReg.getRelsRsnCd());
		custChg.setRmk(rmsFraudReliefReg.getRmk());
		custChg.setInptId(inptId);
		return rmsCnslMngMapper.insertCustChg(custChg);
	}
	
	/**
	 * 가맹점B/L 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	public List<RmsCpBLInq> getCpBLInqList(CnslSearch cnslSearch) {
		final List<RmsCpBLInq> resultList = new ArrayList<>();
		
		rmsCnslMngMapper.selectCpBLInqList(cnslSearch, new ResultHandler<RmsCpBLInq>() {
			private int idx = 1;
			
			@Override
			public void handleResult(ResultContext<? extends RmsCpBLInq> context) {
				RmsCpBLInq row = context.getResultObject();
				row.setIdx(idx++);
				row.setValClfNm(rmsMessageUtil.getMessage("rms.csm.rmsCnslMng.cpBLInq.valClf.", row.getValClf()));
				row.setCpClfNm("["+rmsMessageUtil.getMessage("rms.csm.rmsCnslMng.cpBLInq.cpClf.", row.getCpClf())+"] "+row.getCpId()+" "+row.getCpNm());
				resultList.add(row);
			}
		});
		
		// 고객정보조회 로그 수집
		if (resultList.size() > 0) {
			IdmsCustomerDataAccessLog log = new IdmsCustomerDataAccessLog(new Date(), RmsAuthUtil.getLocalServerIp(), RmsAuthUtil.getUserId(), RmsAuthUtil.getUserInfo().getClntIp(), resultList.get(0).getVal(), "mphnNo", "RMS0010", "10001", resultList.size());
			logger.info(log.toString());
		}
		
		return resultList;
	}
	
	/**
	 * RM 변경이력 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	public List<RmsRmChangeHis> getRmChangeHisList(CnslSearch cnslSearch) {
		final List<RmsRmChangeHis> resultList = new ArrayList<>();
		
		rmsCnslMngMapper.selectRmChangeHisList(cnslSearch, new ResultHandler<RmsRmChangeHis>() {
			private int idx = 1;
			
			@Override
			public void handleResult(ResultContext<? extends RmsRmChangeHis> context) {
				RmsRmChangeHis row = context.getResultObject();
				row.setIdx(idx++);
				row.setCustInfoChgNm(selectCodeName(row.getCustInfoChgCd()));
				row.setBlkRelsClfNm(rmsMessageUtil.getMessage("rms.csm.rmsCnslMng.rmChangeHis.blkRelsClf.", row.getBlkRelsClf()));
				row.setChgRsnNm(selectCodeName(row.getChgRsnCd()));
				resultList.add(row);
			}
		});
		
		// 고객정보조회 로그 수집
		if (resultList.size() > 0) {
			IdmsCustomerDataAccessLog log = new IdmsCustomerDataAccessLog(new Date(), RmsAuthUtil.getLocalServerIp(), RmsAuthUtil.getUserId(), RmsAuthUtil.getUserInfo().getClntIp(), resultList.get(0).getMphnNo(), "mphnNo", "RMS0011", "10001", resultList.size());
			logger.info(log.toString());
		}
		
		return resultList;
	}
	
	/**
	 * 현재시간을 밀리초로 획득
	 * @return
	 */
	private long getCurrentTimeMillis() {
		return rmsCnslMngMapper.selectSysdate().getTime();
	}
	
	/**
	 * 주어진 코드에 대한 코드명 리턴
	 * @param cd 코드
	 * @return 코드명
	 */
	private String selectCodeName(String cd) {
		if (StringUtils.isNotEmpty(cd)) {
			return StringUtils.trimToEmpty(codeNameMapper.selectCodeName(cd));
		}
		return "";
	}
	
	/**
	 * 주어진 밀리초를 "yyyy.MM.dd HH:mm:ss"로 포맷
	 * @param millis 밀리초
	 * @return 포맷된 문자열
	 */
	private String formatDate(long millis) {
		return DateFormatUtils.format(millis, RmsComEnum.DATETIME_FORMAT.value());
	}
	
	/**
	 * 기존 비고에 새 비고를 병합
	 * @param targetRmk 기존 비고
	 * @param newRmk 새 비고
	 * @return 병합된 비고
	 */
	private String mergeRmk(String targetRmk, String newRmk) {
		if (StringUtils.isNotEmpty(newRmk)) {
			StringBuilder strBuilder = new StringBuilder(targetRmk);
			if (StringUtils.isNotEmpty(targetRmk)) {
				strBuilder.append("|");
			}
			strBuilder.append(newRmk);
			targetRmk = strBuilder.toString();
		}
		return StringUtils.defaultString(targetRmk);
	}
}