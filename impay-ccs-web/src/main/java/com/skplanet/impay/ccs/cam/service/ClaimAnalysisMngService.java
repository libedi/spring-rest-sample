/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.cam.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.skplanet.impay.ccs.cam.model.ChartModel;
import com.skplanet.impay.ccs.cam.model.ClaimAnalysisMng;
import com.skplanet.impay.ccs.cam.model.ClmAnlsCase;
import com.skplanet.impay.ccs.cam.model.ClmAnlsTjurRcpt;
import com.skplanet.impay.ccs.cam.model.ClmAnlsType;
import com.skplanet.impay.ccs.cam.service.mapper.ClaimAnalysisMngMapper;
import com.skplanet.impay.ccs.common.constants.ComSCd;
import com.skplanet.impay.ccs.common.log.idms.service.IdmsCustomerLogService;
import com.skplanet.impay.ccs.common.service.MessageByLocaleServiceImpl;
import com.skplanet.impay.ccs.common.service.mapper.CodeNameMapper;
import com.skplanet.impay.ccs.common.service.mapper.UserMapper;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;


/**
 * 클레임 분석관리 Mapper
 * @author Jang
 *
 */
@Service
public class ClaimAnalysisMngService {

	@Autowired
	private ClaimAnalysisMngMapper claimAnalysisMngMapper;
	
	@Autowired
	private CodeNameMapper codeNameMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MessageByLocaleServiceImpl message;
	
	@Autowired
	private IdmsCustomerLogService idmsCustomerLogService;
	
	/**
	 * TAB-1
	 * 건별 조회
	 * @param clmAnlsCase 클레임 분석 - 건별 조회 정보
	 * @return ClaimAnalysisMng
	 */
	public ClaimAnalysisMng getClmAnslCaseList(ClmAnlsCase clmAnlsCase) {
		ClaimAnalysisMng cam = new ClaimAnalysisMng();
		
		final List<ClmAnlsCase> list = new ArrayList<ClmAnlsCase>();
		claimAnalysisMngMapper.selectClmAnslCaseList(clmAnlsCase, new ResultHandler<ClmAnlsCase>(){
			@Override
			public void handleResult(ResultContext<? extends ClmAnlsCase> context) {
				ClmAnlsCase obj = context.getResultObject();
				// 접수유형 명
				obj.setRcptMthdNm(codeNameMapper.selectCodeName(obj.getRcptMthdCd()));	
				// 상담 유형
				obj.setCnslTypNm(codeNameMapper.selectCodeName(obj.getCnslTypCd()));
				// 고객 유형
				if(StringUtils.isEmpty(obj.getCustTypFlg())) obj.setCustTypFlgNm("-");
				if(StringUtils.equals("B",obj.getCustTypFlg())){
					obj.setCustTypFlgNm(message.getMessage("csm.cnslmng.option.custtype1"));
				}else if(StringUtils.equals("S", obj.getCustTypFlg())){
					obj.setCustTypFlgNm(message.getMessage("csm.cnslmng.option.custtype2"));
				}else if(StringUtils.equals("E", obj.getCustTypFlg())){
					obj.setCustTypFlgNm(message.getMessage("csm.cnslmng.option.custtype3"));
				}else if(StringUtils.equals("C", obj.getCustTypFlg())){
					obj.setCustTypFlgNm(message.getMessage("csm.cnslmng.option.custtype4"));	
				}
				// 처리 여부
				obj.setProcNm(StringUtils.equals("Y",obj.getProcYn()) ? message.getMessage("cam.procY") : message.getMessage("cam.procN"));
				// 결제조건 명
				if(StringUtils.isNotEmpty(obj.getPayCndiCd()))obj.setPayCndiNm(codeNameMapper.selectCodeName(obj.getPayCndiCd()));	
				// 이통사 명
				if(StringUtils.isNotEmpty(obj.getCommcClf()))obj.setCommcClfNm(codeNameMapper.selectCodeName(obj.getCommcClf()));	
				// 상담구분
				if(StringUtils.isNotEmpty(obj.getCnslClfUprCd()))obj.setCnslClfUprCdNm(codeNameMapper.selectCodeName(obj.getCnslClfUprCd()));
				// 이관 구분
				if(StringUtils.isEmpty(obj.getTjurClfFlg())) obj.setTjurClfFlgNm("-");
				// 이관 구분 명 (제휴, 정산, 기술)
				if(StringUtils.equals("C", obj.getTjurClfFlgNm())) obj.setTjurClfFlgNm(message.getMessage("cam.cp"));
				else if(obj.getTjurClfFlgNm().equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ALLIANCE.value())) obj.setTjurClfFlgNm(message.getMessage("cam.alliance")); 
				else if(obj.getTjurClfFlgNm().equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ADJUSTMENT.value()))obj.setTjurClfFlgNm(message.getMessage("cam.adjustment"));
				else if(obj.getTjurClfFlgNm().equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_TECHNICAL.value()))obj.setTjurClfFlgNm(message.getMessage("cam.it"));
				// 접수자
				obj.setRegr(userMapper.selectUserId(obj.getRegr()));
				// 이벤트명
				if(StringUtils.isNotEmpty(obj.getCnslEvntCd())){
					obj.setCnslEvntNm(codeNameMapper.selectCodeName(obj.getCnslEvntCd()));	
				}
				list.add(obj);
			}
		});
		// 건별 차트 데이터		
		if(list != null){
			List<ChartModel> chartModel = claimAnalysisMngMapper.selectClmAnlsCaseChart(clmAnlsCase);
			cam.setChartModel(chartModel);
		}
		cam.setClmAnlsCaseList(list);
		if(!list.isEmpty()){
			cam.setTotal(list.get(0).getTotalCnt());
		} else {
			cam.setTotal(0);
		}
		return cam;
	}
	/**
	 * 건별 목록
	 * 엑셀 다운로드
	 * @param clmAnlsCase 클레임 분석 - 건별 조회 정보
	 * @param userInfo 사용자 정보
	 * @return Map<String, Object>
	 */
	public Map<String, Object> clmAnslCaseListExcelDown(ClmAnlsCase clmAnlsCase, @AuthenticationPrincipal CustomUserDetails userInfo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> colName = new ArrayList<String>();
		colName.add(message.getMessage("cam.rcptNo"));		// 접수번호
		colName.add(message.getMessage("cam.rcptDd"));		// 접수 일시
		colName.add(message.getMessage("cam.rcptType"));	// 접수 유형
		colName.add(message.getMessage("cam.rcpt.regr"));	// 접수자
		colName.add(message.getMessage("cam.custType"));	// 고객유형
		colName.add(message.getMessage("cam.payClf"));		// 결제조건
		colName.add(message.getMessage("cam.mphnNo"));		// 휴대폰번호
		colName.add(message.getMessage("cam.cpNm"));		// 가맹점 명
		colName.add(message.getMessage("cam.gods"));		// 상품 명
		colName.add(message.getMessage("cam.event"));		// 이벤트
		colName.add(message.getMessage("cam.commc"));		// 이통사
		colName.add(message.getMessage("cam.cnslType"));	// 상담유형
		colName.add(message.getMessage("cam.cnslClf"));		// 상담구분
		colName.add(message.getMessage("cam.procState"));	// 처리상태
		colName.add(message.getMessage("cam.tjurClf"));		// 이관구분
		colName.add(message.getMessage("cam.cnslCtnt"));	// 상담내용
		colName.add(message.getMessage("cam.procCtnt"));	// 처리내용
		
		Map<String, Object> valueMap = new HashMap<String, Object>();
		List<String> colValue = null;
		List<ClmAnlsCase> list  = claimAnalysisMngMapper.selectClmAnslCaseListExcelDown(clmAnlsCase);
		// 고객 정보 LOG 수집
		if(!list.isEmpty()){
			idmsCustomerLogService.printIdmsLog(list.get(0).getRcptNo(), "X", "CCS0008", "10005", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		
		int index = 0;
		for(ClmAnlsCase model:list){
			colValue = new ArrayList<String>();
			//접수 번호
			colValue.add(model.getRcptNo());
			//접수 일시
			colValue.add(model.getRegDt());
			//접수 유형
			colValue.add(codeNameMapper.selectCodeName(model.getRcptMthdCd()));
			//접수자
			colValue.add(userMapper.selectUserId(model.getRegr()));
			//고객유형
			if(StringUtils.isEmpty(model.getCustTypFlg())){
				colValue.add("-");
			}
			if(StringUtils.equals("B",model.getCustTypFlg())){
				colValue.add(message.getMessage("csm.cnslmng.option.custtype1"));
			}else if(StringUtils.equals("S",model.getCustTypFlg())){
				colValue.add(message.getMessage("csm.cnslmng.option.custtype2"));
			}else if(StringUtils.equals("E", model.getCustTypFlg())){
				colValue.add(message.getMessage("csm.cnslmng.option.custtype3"));
			}else if(StringUtils.equals("C", model.getCustTypFlg())){
				colValue.add(message.getMessage("csm.cnslmng.option.custtype4"));	
			}
			//결제조건
			if(StringUtils.isNotEmpty(model.getPayCndiCd())){
				colValue.add(codeNameMapper.selectCodeName(model.getPayCndiCd()));
			}else{
				colValue.add("-");
			}
			//휴대폰번호
			colValue.add(model.getMphnNo());
			//가맹점명
			if(StringUtils.isEmpty(model.getCpNm())){
                colValue.add("-");
            }else{
                colValue.add(model.getCpNm());
            }
			//상품명
			colValue.add(model.getGodsNm());
			//이벤트
			if(StringUtils.isNotEmpty(model.getCnslEvntCd())){
				colValue.add(codeNameMapper.selectCodeName(model.getCnslEvntCd()));
			} else {
				colValue.add("-");
			}
			//이통사
			if(StringUtils.isNotEmpty(model.getCommcClf())){
				colValue.add(codeNameMapper.selectCodeName(model.getCommcClf()));
			}else{
				colValue.add("-");
			}
			//상담유형
			if(StringUtils.isNotEmpty(model.getCnslTypCd())){
				colValue.add(codeNameMapper.selectCodeName(model.getCnslTypCd()));
			}else{
				colValue.add("-");
			}
			//상담구분
			if(StringUtils.isNotEmpty(model.getCnslClfUprCd())){
				colValue.add(codeNameMapper.selectCodeName(model.getCnslClfUprCd()));
			}else{
				colValue.add("-");
			}
			//처리상태
			colValue.add(StringUtils.equals("Y", model.getProcYn()) ? message.getMessage("cam.procY") : message.getMessage("cam.procN"));
			//이관구분
			if(StringUtils.isEmpty(model.getTjurClfFlg())){
				colValue.add("-");
			}else if(StringUtils.equals("C", model.getTjurClfFlgNm())){
				colValue.add(message.getMessage("cam.cp"));
			}else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ALLIANCE.value(), model.getTjurClfFlgNm())){
				colValue.add(message.getMessage("cam.alliance"));
			}else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ADJUSTMENT.value(), model.getTjurClfFlgNm())) {
				colValue.add(message.getMessage("cam.adjustment"));
			}else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_TECHNICAL.value(), model.getTjurClfFlgNm())) {
				colValue.add(message.getMessage("cam.it"));
			}
			// 상담 내용
			if(StringUtils.isNotEmpty(model.getCnslCtnt())){
				colValue.add(model.getCnslCtnt().replaceAll("<br/>", "\n"));
			}else{
				colValue.add("-");
			}
			// 처리 내용
			if(StringUtils.isNotEmpty(model.getProcCtnt())){
				colValue.add(model.getProcCtnt().replaceAll("<br/>", "\n"));
			}else{
				colValue.add("-");
			}
			valueMap.put(String.valueOf(index++), colValue);
		}
		resultMap.put("colName", colName);
		resultMap.put("colValue", valueMap);
		return resultMap;
	}
	/**
	 * 건별 목록
	 * 엑셀 다운로드건수
	 * @param clmAnlsCase 클레임 분석 - 건별 조회 정보
	 * @return int
	 */
	public int getCaseExcelDownCount(ClmAnlsCase clmAnlsCase) {
		List<ClmAnlsCase> list  = claimAnalysisMngMapper.selectClmAnslCaseListExcelDown(clmAnlsCase);
		if(list == null) return 0;
		else return list.size();
	}
	// TAB 2
	/**
	 * 유형별 조회
	 * @param clmAnlsType 클레임 분석 - 유형별 조회 정보
	 * @return ClaimAnalysisMng
	 */
	public ClaimAnalysisMng getClmAnslTypeList(ClmAnlsType clmAnlsType) {
		ClaimAnalysisMng cam = new ClaimAnalysisMng();
		
		int count = claimAnalysisMngMapper.selectClmAnslTypeCount(clmAnlsType);
		
		final List<ClmAnlsType> list = new ArrayList<ClmAnlsType>();
		claimAnalysisMngMapper.selectClmAnslTypeList(clmAnlsType, new ResultHandler<ClmAnlsType>(){
			@Override
			public void handleResult(ResultContext<? extends ClmAnlsType> context) {
				ClmAnlsType obj = context.getResultObject();
				obj.setRcptMthdNm(codeNameMapper.selectCodeName(obj.getRcptMthdCd()));	// 접수방법명
				if(StringUtils.isNotEmpty(obj.getPayCndiCd())){
					obj.setPayCndiNm(codeNameMapper.selectCodeName(obj.getPayCndiCd()));	// 결제조건명
				}
				obj.setCnslTypNm(codeNameMapper.selectCodeName(obj.getCnslTypCd()));	// 상담유형명
				if(StringUtils.isNotEmpty(obj.getCnslClfUprCd())){
					obj.setCnslClfUprCdNm(codeNameMapper.selectCodeName(obj.getCnslClfUprCd())); // 상담구분명
				}
				if(StringUtils.isNotEmpty(obj.getCommcClf())){
					obj.setCommcClfNm(codeNameMapper.selectCodeName(obj.getCommcClf()));	// 이통사명
				}
				if(StringUtils.isNotEmpty(obj.getCnslEvntCd())){
					obj.setCnslEvntNm(codeNameMapper.selectCodeName(obj.getCnslEvntCd()));	// 이벤트명
				}
				list.add(obj);
			}
		});
		// 유형별 차트 데이터
		if(list != null){		 
			List<ChartModel> chartModel = claimAnalysisMngMapper.selectClmAnlsTypeChart(clmAnlsType); 
			cam.setChartModel(chartModel);
		}
		cam.setClmAnlsTypeList(list);
		cam.setTotal(count);
		
		return cam;
	}
	/**
	 * 유형별 조회
	 * 엑셀 다운로드
	 * @param clmAnlsType 클레임 분석 - 유형별 조회 정보
	 * @return Map<String, Object>
	 */
	public Map<String, Object> clmAnslTypeListExcelDown(ClmAnlsType clmAnlsType) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> colName = new ArrayList<String>();
		colName.add(message.getMessage("cam.rcptDd")); 	// 접수 일자
		colName.add(message.getMessage("cam.cnslType"));// 상담 유형
		colName.add(message.getMessage("cam.event"));	// 이벤트
		colName.add(message.getMessage("cam.cnslClf"));	// 상담 구분
		colName.add(message.getMessage("cam.rcptType"));//접수유형
		colName.add(message.getMessage("cam.payClf"));	//결제조건
		colName.add(message.getMessage("cam.commc"));	// 이통사
		colName.add(message.getMessage("cam.rcptCnt"));// 접수건수
		colName.add(message.getMessage("cam.procY.count"));	// 처리건수
		colName.add(message.getMessage("cam.procN.count"));	// 미처리건수
		
		Map<String, Object> valueMap = new HashMap<String,Object>();
		List<String> colValue = null;
		List<ClmAnlsType> list = claimAnalysisMngMapper.selectClmAnslTypeListExcelDown(clmAnlsType);
		
		int index = 0;
		for(ClmAnlsType model:list){
			colValue = new ArrayList<String>();
			if(StringUtils.isEmpty(model.getPayCndiCd())) model.setPayCndiCd("-");
			if(StringUtils.isEmpty(model.getCommcClf())) model.setCommcClf("-");
			if(StringUtils.isEmpty(model.getCnslEvntCd())) model.setCnslEvntCd("-");
			if(StringUtils.isEmpty(model.getCnslClfUprCd())) model.setCnslClfUprCd("-");
			
			colValue.add(model.getRegDt());
			colValue.add(codeNameMapper.selectCodeName(model.getCnslTypCd()));
			colValue.add(codeNameMapper.selectCodeName(model.getCnslEvntCd()));
			colValue.add(codeNameMapper.selectCodeName(model.getCnslClfUprCd()));
			colValue.add(codeNameMapper.selectCodeName(model.getRcptMthdCd()));
			colValue.add(codeNameMapper.selectCodeName(model.getPayCndiCd()));
			colValue.add(codeNameMapper.selectCodeName(model.getCommcClf()));
			colValue.add(String.valueOf(model.getRcptCnt()));
			colValue.add(String.valueOf(model.getProcYCnt()));
			colValue.add(String.valueOf(model.getProcNCnt()));

			valueMap.put(String.valueOf(index++), colValue);
		}
		resultMap.put("colName", colName);
		resultMap.put("colValue", valueMap);
		return resultMap;
	}
	/**
	 * 유형별 조회
	 * 엑셀 다운로드 건수
	 * @param clmAnlsType 클레임 분석 - 유형별 조회 정보
	 * @return int
	 */
	public int getTypeExcelDownCount(ClmAnlsType clmAnlsType) {
		List<ClmAnlsType> list  = claimAnalysisMngMapper.selectClmAnslTypeListExcelDown(clmAnlsType);
		if(list == null) return 0;
		else return list.size();
	}
	//TAB-3
	/**
	 * 이관접수별 조회
	 * @param clmAnlsTjur 클레임 분석 - 이관접수별 조회 정보
	 * @return ClaimAnalysisMng
	 */
	public ClaimAnalysisMng getClmAnslTjurList(ClmAnlsTjurRcpt clmAnlsTjur) {
		ClaimAnalysisMng cam = new ClaimAnalysisMng();
		
		int count = claimAnalysisMngMapper.selectClmAnslTjurCount(clmAnlsTjur);
		
		final List<ClmAnlsTjurRcpt> list = new ArrayList<ClmAnlsTjurRcpt>(); 
		claimAnalysisMngMapper.selectClmAnslTjurList(clmAnlsTjur, new ResultHandler<ClmAnlsTjurRcpt>(){
			@Override
			public void handleResult(ResultContext<? extends ClmAnlsTjurRcpt> context) {
				ClmAnlsTjurRcpt obj = context.getResultObject();
				if(StringUtils.isNotEmpty(obj.getCommcClf())){
					obj.setCommcClfNm(codeNameMapper.selectCodeName(obj.getCommcClf()));	// 이통사명
				}
				if(StringUtils.isNotEmpty(obj.getCnslEvntCd())){
					obj.setCnslEvntNm(codeNameMapper.selectCodeName(obj.getCnslEvntCd()));	// 이벤트명
				}
				if(StringUtils.equals(obj.getTjurClfFlg(), "C")){							// 이관 구분 (C : 가맹점)
					obj.setDeptNm(message.getMessage("cam.cp"));
				}else if(StringUtils.equals(obj.getTjurClfFlg(),"B")){						// 이관 구분 (B : 각 부서)
					if(StringUtils.isNotEmpty(obj.getDeptCd())){
						if(obj.getDeptCd().equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ALLIANCE.value())){
							obj.setDeptNm(message.getMessage("cam.alliance")); 
						}else if(obj.getDeptCd().equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ADJUSTMENT.value())){
							obj.setDeptNm(message.getMessage("cam.adjustment"));
						}else if(obj.getDeptCd().equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_TECHNICAL.value())){
							obj.setDeptNm(message.getMessage("cam.it"));
						}
					}
				}
				obj.setCnslTypNm(codeNameMapper.selectCodeName(obj.getCnslTypCd()));		// 상담 유형 코드
				if(StringUtils.isNotEmpty(obj.getCnslClfUprCd())){
					obj.setCnslClfUprCdNm(codeNameMapper.selectCodeName(obj.getCnslClfUprCd()));
				}
				list.add(obj);
			}
		});
		if(list != null){
			List<ChartModel> chartModel = claimAnalysisMngMapper.selectClmAnlsTjurChart(clmAnlsTjur);
			cam.setChartModel(chartModel);
		}
		cam.setTotal(count);
		cam.setClmAnlsTjurRcptList(list);
		return cam;
	}
	/**
	 * 이관 접수별
	 * 엑셀 다운로드
	 * @param clmAnlsTjur 클레임 분석 - 이관접수별 조회 정보
	 * @return Map<String, Object>
	 */
	public Map<String, Object> clmAnslTjurListExcelDown(ClmAnlsTjurRcpt clmAnlsTjur) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> colName = new ArrayList<String>();
		colName.add("No.");
		colName.add(message.getMessage("cam.rcptDd")); 	// 접수 일자
		colName.add(message.getMessage("cam.tjurClf"));	// 이관 구분
		colName.add(message.getMessage("cam.cpNm"));	// 가맹점 명
		colName.add(message.getMessage("cam.cnslType"));// 상담 유형
		colName.add(message.getMessage("cam.cnslClf"));	// 상담 구분
		colName.add(message.getMessage("cam.event"));	// 이벤트
		colName.add(message.getMessage("cam.commc"));	// 이통사
		colName.add(message.getMessage("cam.rcptCnt"));// 접수건수
		colName.add(message.getMessage("cam.procY.count"));	// 처리건수
		colName.add(message.getMessage("cam.procN.count"));	// 미처리건수
		
		Map<String, Object> valueMap = new HashMap<String,Object>();
		List<String> colValue = null;
		List<ClmAnlsTjurRcpt> list = claimAnalysisMngMapper.selectClmAnslTjurListExcelDown(clmAnlsTjur);
		
		int index = 0;
		for(ClmAnlsTjurRcpt model:list){
			colValue = new ArrayList<String>();
			colValue.add(String.valueOf(model.getIdx()));
			colValue.add(model.getRegDt());
			if(StringUtils.equals(model.getTjurClfFlg(), "C")){							// 이관 구분 (C : 가맹점)
				model.setDeptNm(message.getMessage("cam.cp"));
			}else if(StringUtils.equals(model.getTjurClfFlg(),"B")){						// 이관 구분 (B : 각 부서)
				if(StringUtils.isNotEmpty(model.getDeptCd())){
					if(model.getDeptCd().equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ALLIANCE.value())){
						model.setDeptNm(message.getMessage("cam.alliance")); 
					}else if(model.getDeptCd().equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ADJUSTMENT.value())){
						model.setDeptNm(message.getMessage("cam.adjustment"));
					}else if(model.getDeptCd().equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_TECHNICAL.value())){
						model.setDeptNm(message.getMessage("cam.it"));
					}
				}
			}else{
				model.setDeptNm("");
			}
			// 이관 구분
			if(StringUtils.isNotEmpty(model.getDeptNm())){
				colValue.add(model.getDeptNm());
			}else{
				colValue.add("");
			}
			// 가맹점명
			if(StringUtils.isNotEmpty(model.getPaySvcNm())){
				colValue.add(model.getPaySvcNm());
			}else{
				colValue.add("");
			}
			// 상담유형
			if(StringUtils.isNotEmpty(model.getCnslTypCd())){
				colValue.add(codeNameMapper.selectCodeName(model.getCnslTypCd()));
			}else{
				colValue.add("");
			}
			// 상담구분
			if(StringUtils.isNotEmpty(model.getCnslClfUprCd())){
				colValue.add(codeNameMapper.selectCodeName(model.getCnslClfUprCd()));
			}else{
				colValue.add("");
			}
			// 이벤트
			if(StringUtils.isNotEmpty(model.getCnslEvntCd())){
				colValue.add(codeNameMapper.selectCodeName(model.getCnslEvntCd()));
			}else{
				colValue.add("");
			}
			// 이통사
			if(StringUtils.isNotEmpty(model.getCommcClf())){
				colValue.add(codeNameMapper.selectCodeName(model.getCommcClf()));
			}else{
				colValue.add("");
			}
			colValue.add(String.valueOf(model.getRcptCnt()));
			colValue.add(String.valueOf(model.getProcYCnt()));
			colValue.add(String.valueOf(model.getProcNCnt()));
			valueMap.put(String.valueOf(index++), colValue);
		}
		resultMap.put("colName", colName);
		resultMap.put("colValue", valueMap);
		return resultMap;
	}
	/**
	 * 이관 접수별
	 * 엑셀 다운로드 건수
	 * @param clmAnlsTjur 클레임 분석 - 이관접수별 조회 정보
	 * @return int
	 */
	public int getTjurExcelDownCount(ClmAnlsTjurRcpt clmAnlsTjur) {
		List<ClmAnlsTjurRcpt> list  = claimAnalysisMngMapper.selectClmAnslTjurListExcelDown(clmAnlsTjur);
		if(list == null) return 0;
		else return list.size();
	}
}
