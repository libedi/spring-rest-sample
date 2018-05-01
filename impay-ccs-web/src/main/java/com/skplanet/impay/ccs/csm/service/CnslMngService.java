/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.csm.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhncorp.lucy.security.xss.XssPreventer;
import com.skplanet.impay.ccs.common.constants.ComSCd;
import com.skplanet.impay.ccs.common.log.idms.service.IdmsCustomerLogService;
import com.skplanet.impay.ccs.common.model.CntcInfo;
import com.skplanet.impay.ccs.common.model.Email;
import com.skplanet.impay.ccs.common.model.EntpModel;
import com.skplanet.impay.ccs.common.model.FileInfo;
import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.model.PageParam;
import com.skplanet.impay.ccs.common.model.PayMphnInfo;
import com.skplanet.impay.ccs.common.model.PayrInfo;
import com.skplanet.impay.ccs.common.model.PdfModel;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.model.SendReqModel;
import com.skplanet.impay.ccs.common.service.EmailService;
import com.skplanet.impay.ccs.common.service.FileService;
import com.skplanet.impay.ccs.common.service.MessageByLocaleServiceImpl;
import com.skplanet.impay.ccs.common.service.SendReqService;
import com.skplanet.impay.ccs.common.service.mapper.CodeNameMapper;
import com.skplanet.impay.ccs.common.service.mapper.CommonMapper;
import com.skplanet.impay.ccs.common.service.mapper.SendReqMapper;
import com.skplanet.impay.ccs.common.service.mapper.UserMapper;
import com.skplanet.impay.ccs.common.util.CustomStringUtils;
import com.skplanet.impay.ccs.common.util.DateUtil;
import com.skplanet.impay.ccs.csm.model.CnslDetail;
import com.skplanet.impay.ccs.csm.model.CnslScrpt;
import com.skplanet.impay.ccs.csm.model.CnslSearch;
import com.skplanet.impay.ccs.csm.model.CnslTjurModel;
import com.skplanet.impay.ccs.csm.model.NotiResult;
import com.skplanet.impay.ccs.csm.model.NpayHistoryAddDtl;
import com.skplanet.impay.ccs.csm.model.PayItcptModel;
import com.skplanet.impay.ccs.csm.model.TjurEmail;
import com.skplanet.impay.ccs.csm.model.TradeCmplModel;
import com.skplanet.impay.ccs.csm.model.TradeModel;
import com.skplanet.impay.ccs.csm.service.mapper.CnslMngMapper;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.uim.service.mapper.UserMngMapper;
import com.skplanet.impay.common.sms.model.SendSmsRequest;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 상담관리 Service
 * @author Sangjun, Park
 *
 */
@Service
public class CnslMngService {
	private final static Logger logger = LoggerFactory.getLogger(CnslMngService.class);
	
	private final String NO_REG_CUSTOMER = "idmsCustomerLogService.MAX_CUSTOMER_COUNTidmsCustomerLogService.MAX_CUSTOMER_COUNTidmsCustomerLogService.MAX_CUSTOMER_COUNT9";		// 미거래 고객
	private final String SMS_ROUTING_KEY = "CommonSmsSendQueue";
	
	@Autowired
	private CnslMngMapper cnslMngMapper;
	
	@Autowired
	private MessageByLocaleServiceImpl message;
	
	@Autowired
	private CodeNameMapper codeNameMapper;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SendReqService sendReqService;
	
	@Autowired
	private SendReqMapper sendReqMapper;
	
	@Autowired
	private UserMngMapper userMngMapper;
	
	@Autowired
	private CommonMapper commonMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private IdmsCustomerLogService idmsCustomerLogService;
	
	@Value("${ccs.url}")
	private String hostAddress;
	
	/**
	 * 타 메뉴 접근시 초기 데이터 셋팅
	 * @param search 상담관리 조회 조건
	 * @param model Model
	 * @param userInfo 사용자 정보
	 */
	public void setDefaultData(CnslSearch search, Model model, CustomUserDetails userInfo) {
		// 조회조건 기본값 셋팅
		search.setChgListYn("Y");
		if(search.getPageParam() == null){
			PageParam pageParam = new PageParam();
			pageParam.setPageIndex(1);
			pageParam.setRowCount(5);
			search.setPageParam(pageParam);
		}
		// 1. 상담내역 조회
		CnslDetail cnslDetail = this.getCnslDetail(search);
		if(cnslDetail != null){
			// 고객 정보 LOG 수집
			idmsCustomerLogService.printIdmsLog(cnslDetail.getPayrSeq(),"X", "CCS0010", "40001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);

			search.setPayMphnId(cnslDetail.getPayMphnId());
			// 거래내역이 있으면
			if(cnslDetail.getTradeModel() != null && StringUtils.isNotEmpty(cnslDetail.getTradeModel().getTrdNo())){
				String[] trdNos = {cnslDetail.getTradeModel().getTrdNo()};
				search.setTrdNos(trdNos);
			}
			
			// 2. 거래완료탭 조회
			TradeCmplModel trdCmpl = this.getTotalTradeCompleteList(search);
			model.addAttribute("TEMP_CNSL", cnslDetail);					// 상담내역
			model.addAttribute("TEMP_PROC", cnslDetail);					// 처리내역
			model.addAttribute("mphnNo", trdCmpl.getPayMphnInfo().getMphnNo());
			try {
				model.addAttribute("totTrdCmpl", objectMapper.writeValueAsString(trdCmpl));		// 거래완료정보
				model.addAttribute("cnslCtnt", objectMapper.writeValueAsString(cnslDetail));	// 상담내역
				if(cnslDetail.getCnslChgList() != null){
					model.addAttribute("cnslChg", objectMapper.writeValueAsString(cnslDetail.getCnslChgList()));	// 이전 상담내역
					// 3. 결과통보 조회
					List<CnslDetail> chgList = cnslDetail.getCnslChgList().getContent();
					if(!chgList.isEmpty()){
						String sndReqSeq = chgList.get(0).getSndReqSeq();
						if(StringUtils.isNotEmpty(sndReqSeq)){
							model.addAttribute("notiResult", objectMapper.writeValueAsString(this.getNotiResult(sndReqSeq)));	// 결과통보내역
						}
					}
				}
				if(StringUtils.isNotEmpty(search.getHash())){
					model.addAttribute("hash", search.getHash());				// 화면 위치 이동할 hash값
				}
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 고객정보 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return TradeCmplModel
	 */
	public TradeCmplModel getCustInfo(CnslSearch cnslSearch){
		TradeCmplModel tradeCmplModel = new TradeCmplModel();
		// 고객 결제폰 정보 조회
		if(StringUtils.isNotEmpty(cnslSearch.getCustMphnNo()) || StringUtils.isNotEmpty(cnslSearch.getPayMphnId())){
			PayMphnInfo param = new PayMphnInfo();
			param.setMphnNo(cnslSearch.getCustMphnNo());
			param.setPayMphnId(cnslSearch.getPayMphnId());
			List<PayMphnInfo> mphnList = this.userMapper.selectPayMphnInfoList(param);
			/*
			 * 휴대번호에 대한 결제폰정보(고객정보)가 1개이면 고객정보 fix.
			 * 1개 이상이면 UI에서 고객정보 선택.
			 */
			if(mphnList != null && !mphnList.isEmpty()){
				tradeCmplModel.setMphnList(mphnList);
				// 결제폰정보가 여러개 조회될 경우
				if(mphnList.size() > 1){
					List<String> payMphnIdList = new ArrayList<>();
					for(PayMphnInfo mphn : mphnList){
						// 고객번호로 재조회시 선택한 결제폰정보 셋팅
						if(StringUtils.equals(mphn.getPayMphnId(), cnslSearch.getPayMphnId())){
							tradeCmplModel.setPayMphnInfo(mphn);
						}
						if(StringUtils.isEmpty(cnslSearch.getPayMphnId())){
							payMphnIdList.add(mphn.getPayMphnId());
						}
					}
					cnslSearch.setPayMphnIdList(payMphnIdList);
				} else if(mphnList.size() == 1){
					// 결제폰정보가 1개일 경우
					tradeCmplModel.setPayMphnInfo(mphnList.get(0));
					cnslSearch.setPayMphnId(mphnList.get(0).getPayMphnId());
				}
			}
		}
		return tradeCmplModel;
	}
	
	/**
	 * 조회 탭 - 거래완료 탭 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return TradeCmplModel
	 */
	public TradeCmplModel getTotalTradeCompleteList(CnslSearch cnslSearch) {
		TradeCmplModel tradeCmplModel = new TradeCmplModel();
		// 고객 결제폰 정보 조회
		if(StringUtils.isNotEmpty(cnslSearch.getCustMphnNo()) || StringUtils.isNotEmpty(cnslSearch.getPayMphnId())){
			PayMphnInfo param = new PayMphnInfo();
			param.setMphnNo(cnslSearch.getCustMphnNo());
			param.setPayMphnId(cnslSearch.getPayMphnId());
			List<PayMphnInfo> mphnList = this.userMapper.selectPayMphnInfoList(param);
			/*
			 * 휴대번호에 대한 결제폰정보(고객정보)가 1개이면 고객정보 fix.
			 * 1개 이상이면 UI에서 고객정보 선택.
			 */
			if(mphnList != null && !mphnList.isEmpty()){
				tradeCmplModel.setMphnList(mphnList);
				// 결제폰정보가 여러개 조회될 경우
				if(mphnList.size() > 1){
					List<String> payMphnIdList = new ArrayList<>();
					for(PayMphnInfo mphn : mphnList){
						// 고객번호로 재조회시 선택한 결제폰정보 셋팅
						if(StringUtils.equals(mphn.getPayMphnId(), cnslSearch.getPayMphnId())){
							tradeCmplModel.setPayMphnInfo(mphn);
						}
						if(StringUtils.isEmpty(cnslSearch.getPayMphnId())){
							payMphnIdList.add(mphn.getPayMphnId());
						}
					}
					cnslSearch.setPayMphnIdList(payMphnIdList);
				} else if(mphnList.size() == 1){
					// 결제폰정보가 1개일 경우
					tradeCmplModel.setPayMphnInfo(mphnList.get(0));
					cnslSearch.setPayMphnId(mphnList.get(0).getPayMphnId());
				}
				// 거래완료 건 조회
				tradeCmplModel.setTradeList(this.getTradeCompleteListByPaging(cnslSearch));
			}
		}
		/*
		 * 상담이력 조회 조건
		 * 1. 거래완료 건 조회결과 O : 상담이력 조회 -> 기존 고객 상담
		 * 2. 거래완료 건 조회결과 X and 상담자번호 O : 상담이력 조회 -> 미거래 고객 상담
		 * 3. 거래완료 건 조회결과 X and 상담자번호 X : 상담이력 조회않음
		 */
		if((tradeCmplModel.getTradeList() != null && tradeCmplModel.getTradeList().getTotal() != 0) || StringUtils.isNotEmpty(cnslSearch.getCustMphnNo())){
			// 전체 상담 이력 조회
			tradeCmplModel.setCnslDetailList(this.getCnslDetailListByPaging(cnslSearch));
		}
		return tradeCmplModel;
	}
	
	/**
	 * 거래완료 리스트
	 * @param cnslSearch 상담관리 조회 조건
	 * @return List<TradeModel>
	 */
	public List<TradeModel> getTradeCompleteList(CnslSearch cnslSearch) {
		// 고객번호 미선택 조회시
		if((StringUtils.isEmpty(cnslSearch.getPayMphnId()) || cnslSearch.getPayMphnIdList() == null || cnslSearch.getPayMphnIdList().isEmpty()) 
				&& StringUtils.isNotEmpty(cnslSearch.getCustMphnNo())){
			this.getCustInfo(cnslSearch);
		}
		final List<TradeModel> list = new ArrayList<>();
		this.cnslMngMapper.selectTradeCompleteList(cnslSearch, new ResultHandler<TradeModel>(){
			@Override
			public void handleResult(ResultContext<? extends TradeModel> context) {
				TradeModel obj = context.getResultObject();
				// 결제조건명
				obj.setTrdTypNm(codeNameMapper.selectCodeName(obj.getTrdTypCd()));
				// 통신사명
				obj.setCommcClfNm(codeNameMapper.selectCodeName(obj.getCommcClf()));
				// 결제상태
				if(StringUtils.equals("N", obj.getCnclYn())){
					obj.setPayStat(message.getMessage("csm.cnslmng.data.paystat.success"));	// 결제 완료
				} else {
					obj.setPayStat(message.getMessage("csm.cnslmng.data.paystat.cancel"));	// 결제 취소
				}
				// 고객상태
				if(StringUtils.equals("Y", obj.getSmplPayYn())){
					obj.setSmplPayNm(message.getMessage("csm.cnslmng.data.custstat.simple"));	// 간소화 결제
				} else {
					obj.setSmplPayNm(message.getMessage("csm.cnslmng.data.custstat.normal"));	// 일반 결제
				}
				// 인증구분
				if(StringUtils.equals("S", obj.getAuthtiClfFlg())){
					obj.setAuthtiClfFlgNm("SMS");
				} else if(StringUtils.equals("A", obj.getAuthtiClfFlg())){
					obj.setAuthtiClfFlgNm("ARS");
				} else if(StringUtils.equals("O", obj.getAuthtiClfFlg())){
					obj.setAuthtiClfFlgNm("U-OTP");
				} else if(StringUtils.equals("N", obj.getAuthtiClfFlg())){
					obj.setAuthtiClfFlgNm(message.getMessage("csm.cnslmng.data.authflg.n"));
				}
				// 휴대폰번호 * 표시
				if(StringUtils.isNotEmpty(obj.getMphnNo())){
					obj.setMphnNo(CustomStringUtils.replaceTelNoForAsterik(obj.getMphnNo()));
				}
				
				list.add(obj);
			}
		});
		return list;
	}
	
	/**
	 * 거래완료 리스트 조회 (페이징처리)
	 * @param cnslSearch 상담관리 조회 조건
	 * @return Page<TradeModel>
	 */
	public Page<TradeModel> getTradeCompleteListByPaging(CnslSearch cnslSearch) {
		// 고객번호 미선택 조회시
		if((StringUtils.isEmpty(cnslSearch.getPayMphnId()) || cnslSearch.getPayMphnIdList() == null || cnslSearch.getPayMphnIdList().isEmpty()) 
				&& StringUtils.isNotEmpty(cnslSearch.getCustMphnNo())){
			this.getCustInfo(cnslSearch);
		}
		final List<TradeModel> list = new ArrayList<>();
		int totalCount = this.cnslMngMapper.selectTradeCompleteListCount(cnslSearch);
		if(totalCount > 0){
			this.cnslMngMapper.selectTradeCompleteListByPaging(cnslSearch, new ResultHandler<TradeModel>(){
				@Override
				public void handleResult(ResultContext<? extends TradeModel> context) {
					TradeModel obj = context.getResultObject();
					// 결제조건명
					obj.setTrdTypNm(codeNameMapper.selectCodeName(obj.getTrdTypCd()));
					// 통신사명
					obj.setCommcClfNm(codeNameMapper.selectCodeName(obj.getCommcClf()));
					// 결제상태
					if(StringUtils.equals("N", obj.getCnclYn())){
						obj.setPayStat(message.getMessage("csm.cnslmng.data.paystat.success"));	// 결제 완료
					} else {
						obj.setPayStat(message.getMessage("csm.cnslmng.data.paystat.cancel"));	// 결제 취소
					}
					// 고객상태
					if(StringUtils.equals("Y", obj.getSmplPayYn())){
						obj.setSmplPayNm(message.getMessage("csm.cnslmng.data.custstat.simple"));	// 간소화 결제
					} else {
						obj.setSmplPayNm(message.getMessage("csm.cnslmng.data.custstat.normal"));	// 일반 결제
					}
					// 인증구분
					if(StringUtils.equals("S", obj.getAuthtiClfFlg())){
						obj.setAuthtiClfFlgNm("SMS");
					} else if(StringUtils.equals("A", obj.getAuthtiClfFlg())){
						obj.setAuthtiClfFlgNm("ARS");
					} else if(StringUtils.equals("O", obj.getAuthtiClfFlg())){
						obj.setAuthtiClfFlgNm("U-OTP");
					} else if(StringUtils.equals("N", obj.getAuthtiClfFlg())){
						obj.setAuthtiClfFlgNm(message.getMessage("csm.cnslmng.data.authflg.n"));
					}
					list.add(obj);
				}
			});
		}
		return new Page<TradeModel>(totalCount, list);
	}
	
	/**
	 * 거래완료 리스트 총건수 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return int
	 */
	public int getTradeCompleteListCount(CnslSearch cnslSearch) {
		return this.cnslMngMapper.selectTradeCompleteListCount(cnslSearch);
	}
	
	/**
	 * 전체 상담이력 리스트 조회 (페이징 처리)
	 * @param cnslSearch 상담관리 조회 조건
	 * @return Page<CnslDetail>
	 */
	public Page<CnslDetail> getCnslDetailListByPaging(CnslSearch cnslSearch) {
		final List<CnslDetail> list = new ArrayList<>();
		int totalCount = this.cnslMngMapper.selectCnslDetailListCount(cnslSearch);
		if(totalCount > 0){
			this.cnslMngMapper.selectCnslDetailList(cnslSearch, new ResultHandler<CnslDetail>(){
				@Override
				public void handleResult(ResultContext<? extends CnslDetail> context) {
					CnslDetail obj = context.getResultObject();
					if(StringUtils.isNotEmpty(obj.getCnslClfUprCd())){
						obj.setCnslClfUprNm(codeNameMapper.selectCodeName(obj.getCnslClfUprCd()));
					}
					if(StringUtils.isNotEmpty(obj.getProcYn())){
						if(StringUtils.equals("Y", obj.getProcYn())){
							obj.setProcYnNm(message.getMessage("csm.cnslmng.data.procstat.complete"));		// 완료
						} else {
							obj.setProcYnNm(message.getMessage("csm.cnslmng.data.procstat.processing"));	// 처리중
						}
					}
					if(StringUtils.isNotEmpty(obj.getCnslTjurCd())){
						if(StringUtils.equals("C", obj.getCnslTjurCd())){
							obj.setCnslTjurNm(message.getMessage("csm.cnslmng.data.tjur.cp"));					// 가맹점
						} else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ALLIANCE.value(), obj.getCnslTjurCd())){
							obj.setCnslTjurNm(message.getMessage("csm.cnslmng.data.tjur.dept.alliance.shrt"));	// 제휴
						} else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ADJUSTMENT.value(), obj.getCnslTjurCd())){
							obj.setCnslTjurNm(message.getMessage("csm.cnslmng.data.tjur.dept.adjust.shrt"));	// 정산
						} else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_TECHNICAL.value(), obj.getCnslTjurCd())){
							obj.setCnslTjurNm(message.getMessage("csm.cnslmng.data.tjur.dept.tech.shrt"));		// 기술
						}
					}
					if(StringUtils.isNotEmpty(obj.getCnslCtnt())){
						obj.setCnslCtnt(obj.getCnslCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
					}
					if(StringUtils.isNotEmpty(obj.getProcCtnt())){
						obj.setProcCtnt(obj.getProcCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
					}
					list.add(obj);
				}
			});
		}
		return new Page<CnslDetail>(totalCount, list);
	}

	/**
	 * 조회 탭 - 거래완료 리스트 엑셀 다운로드
	 * @param cnslSearch 상담관리 조회 조건
	 * @param userInfo 사용자 정보
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getTradeCompleteListExcelDown(CnslSearch cnslSearch, CustomUserDetails userInfo) {
		Map<String, Object> resultMap = new HashMap<>();
		
		List<List<String>> headerList = new ArrayList<>();
		List<String> colName = new ArrayList<>();
		colName.add(message.getMessage("csm.cnslmng.th.conditaion.pay"));	// 결제 조건
		colName.add(message.getMessage("csm.cnslmng.th.date.trade"));		// 거래 일시
		colName.add(message.getMessage("csm.cnslmng.th.date.cancel"));		// 취소 일시
		colName.add(message.getMessage("csm.cnslmng.th.cancel.yn"));		// 취소 가능 여부
		colName.add(message.getMessage("csm.cnslmng.th.birthday"));			// 생년월일
		colName.add(message.getMessage("csm.cnslmng.th.commc"));			// 통신사
		colName.add(message.getMessage("csm.cnslmng.label.custno"));		// 고객 번호
		colName.add(message.getMessage("csm.cnslmng.th.telno"));			// 휴대폰 번호
		colName.add(message.getMessage("csm.cnslmng.th.tradeamount"));		// 거래 금액
		colName.add(message.getMessage("csm.cnslmng.th.saleamount"));		// 할인 금액
		colName.add(message.getMessage("csm.cnslmng.th.reqtradeamount"));	// 청구 금액
		colName.add(message.getMessage("csm.cnslmng.th.cpname"));			// 가맹점명
		colName.add(message.getMessage("csm.cnslmng.th.goodsname"));		// 상품명
		colName.add(message.getMessage("csm.cnslmng.th.status.cust"));		// 고객 상태
		colName.add(message.getMessage("csm.cnslmng.th.status.pay"));		// 결제 상태
		colName.add(message.getMessage("csm.cnslmng.th.tradeno.trd"));		// 결제승인(구매)번호
		colName.add(message.getMessage("csm.cnslmng.th.authflg"));			// 인증 구분
		colName.add(message.getMessage("csm.cnslmng.th.center"));			// 고객 센터
		headerList.add(colName);
		
		List<List<String>> dataList = new ArrayList<>();
		List<TradeModel> list = this.getTradeCompleteList(cnslSearch);
		
		// 고객 정보 LOG 수집
		if(!list.isEmpty()){
			// getPayMphnId = 결제폰 ID, getBrthYmd = 생년월일
			idmsCustomerLogService.printIdmsLog(list.get(0).getPayrSeq(),"X", "CCS0016", "10001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		
		for(TradeModel model : list){
			List<String> colValue = new ArrayList<>();
			colValue.add(model.getTrdTypNm());
			colValue.add(model.getTrdDt());
			colValue.add(model.getCnclDt());
			colValue.add(model.getAvlCncl());
			colValue.add(model.getBrthYmd());
			colValue.add(model.getCommcClfNm());
			colValue.add(model.getPayrSeq());
			colValue.add(model.getMphnNo());
			colValue.add(CustomStringUtils.formatMoney(model.getPayAmt()));
			colValue.add(CustomStringUtils.formatMoney(model.getSaleAmt()));
			colValue.add(CustomStringUtils.formatMoney(model.getAcPayAmt()));
			colValue.add(model.getPaySvcNm());
			colValue.add(model.getGodsNm());
			colValue.add(model.getSmplPayNm());
			colValue.add(model.getPayStat());
			colValue.add(model.getTrdNo());
			colValue.add(model.getAuthtiClfFlgNm());
			colValue.add(model.getTelNo());
			dataList.add(colValue);
		}
		
		short[] dataAlignArray = new short[]{
				CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER,
				CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_RIGHT, CellStyle.ALIGN_RIGHT, CellStyle.ALIGN_RIGHT, CellStyle.ALIGN_LEFT,
				CellStyle.ALIGN_LEFT,	CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER
				};
		
		String title = message.getMessage("csm.cnslmng.header.title.tradecompletelist");
		resultMap.put("excelName", title);		// Excel File Name 설정
		resultMap.put("sheetName", title);		// sheet name 설정
		resultMap.put("headerList", headerList);
		resultMap.put("dataList", dataList);
		resultMap.put("dataAlignArray", dataAlignArray);
		return resultMap;
	}
	
	/**
	 * 상담내용 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return CnslDetail
	 */
	public CnslDetail getCnslDetail(CnslSearch cnslSearch) {
		CnslDetail cnslDetail = this.cnslMngMapper.selectCnslDetail(cnslSearch.getRcptNo());
		// 상담내용 코드값 조회
		if(StringUtils.isNotEmpty(cnslDetail.getCnslClfUprCd())){
			cnslDetail.setCnslClfUprNm(this.codeNameMapper.selectCodeName(cnslDetail.getCnslClfUprCd()));
		}
		if(StringUtils.isNotEmpty(cnslDetail.getCnslClfLwrCd())){
			cnslDetail.setCnslClfLwrNm(this.codeNameMapper.selectCodeName(cnslDetail.getCnslClfLwrCd()));
		}
		if(StringUtils.isNotEmpty(cnslDetail.getCnslTypCd())){
			cnslDetail.setCnslTypNm(this.codeNameMapper.selectCodeName(cnslDetail.getCnslTypCd()));
		}
		if(StringUtils.isNotEmpty(cnslDetail.getPayCndiCd())){
			cnslDetail.setPayCndiNm(this.codeNameMapper.selectCodeName(cnslDetail.getPayCndiCd()));			
		}
		if(StringUtils.isNotEmpty(cnslDetail.getCommcClf())){
			cnslDetail.setCommcClfNm(this.codeNameMapper.selectCodeName(cnslDetail.getCommcClf()));			
		}
		if(StringUtils.isNotEmpty(cnslDetail.getCnslEvntCd())){
			cnslDetail.setCnslEvntNm(this.codeNameMapper.selectCodeName(cnslDetail.getCnslEvntCd()));			
		}
		if(StringUtils.isNotEmpty(cnslDetail.getProcDd())){
			cnslDetail.setProcDt(
					new StringBuilder().append(cnslDetail.getProcDd().substring(0, 4)).append(".").append(cnslDetail.getProcDd().substring(4, 6))
					.append(".").append(cnslDetail.getProcDd().substring(6)).toString()
				);
		}
		if(StringUtils.equalsIgnoreCase("C", cnslDetail.getCnslTjurCd()) && StringUtils.isNotEmpty(cnslDetail.getEntpId())){
			cnslDetail.setCnslTjurNm(this.commonMapper.selectEntpInfo(cnslDetail.getEntpId()).getEntpNm());
		} else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ALLIANCE.value(), cnslDetail.getCnslTjurCd())){
			cnslDetail.setCnslTjurNm(message.getMessage("csm.cnslmng.data.tjur.dept.alliance"));
		} else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ADJUSTMENT.value(), cnslDetail.getCnslTjurCd())){
			cnslDetail.setCnslTjurNm(message.getMessage("csm.cnslmng.data.tjur.dept.adjust"));
		} else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_TECHNICAL.value(), cnslDetail.getCnslTjurCd())){
			cnslDetail.setCnslTjurNm(message.getMessage("csm.cnslmng.data.tjur.dept.tech"));
		}
		cnslDetail.setRcptMthdNm(this.codeNameMapper.selectCodeName(cnslDetail.getRcptMthdCd()));
		// 개행문자 br 처리
		if(StringUtils.isNotEmpty(cnslDetail.getCnslCtnt())){
			cnslDetail.setCnslCtnt(XssPreventer.unescape(cnslDetail.getCnslCtnt()).replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		}
		if(StringUtils.isNotEmpty(cnslDetail.getProcCtnt())){
			cnslDetail.setProcCtnt(XssPreventer.unescape(cnslDetail.getProcCtnt()).replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		}
		// 결제정보 코드값 조회
		TradeModel tradeModel = cnslDetail.getTradeModel();
		if(tradeModel != null){
			if(StringUtils.isNotEmpty(tradeModel.getCommcClf())){
				tradeModel.setCommcClfNm(this.codeNameMapper.selectCodeName(tradeModel.getCommcClf()));
			}
			if(StringUtils.isNotEmpty(tradeModel.getTrdTypCd())){
				tradeModel.setTrdTypNm(this.codeNameMapper.selectCodeName(tradeModel.getTrdTypCd()));
			}
		}
		// 상담내역 변경이력 조회
		if(StringUtils.equalsIgnoreCase("Y", cnslSearch.getChgListYn())){
			cnslDetail.setCnslChgList(this.getCnslChgListByPaging(cnslSearch));
		}
		return cnslDetail;
	}
	
	/**
	 * 미납이력 및 가산금 부과내역 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return Page<NpayHistoryAddDtl>
	 */
	public Page<NpayHistoryAddDtl> getNpayHistoryList(CnslSearch cnslSearch) {
		
		if(StringUtils.isNotEmpty(cnslSearch.getStrtDt())){
			cnslSearch.setStrtDt(cnslSearch.getStrtDt().replace(".", ""));
			cnslSearch.setEndDt(cnslSearch.getEndDt().replace(".", ""));
		}
		if(StringUtils.isNotEmpty(cnslSearch.getStrtMon())){
			cnslSearch.setStrtMon(cnslSearch.getStrtMon().replace(".", ""));
		}
		
		final List<NpayHistoryAddDtl> list = new ArrayList<NpayHistoryAddDtl>();
		cnslMngMapper.selectNpayHistoryList(cnslSearch, new ResultHandler<NpayHistoryAddDtl>(){
			@Override
			public void handleResult(ResultContext<? extends NpayHistoryAddDtl> context) {
				NpayHistoryAddDtl npayHis = context.getResultObject();
				npayHis.setTrdStat(
						npayHis.getTrdStat().equals("Y") ? message.getMessage("csm.cnslmng.data.trdstat.cancel"):message.getMessage("csm.cnslmng.data.trdstat.normal")
					);// 취소여부 Y/N에 따른 취소와 정상 처리
				list.add(npayHis);
			}
		});
		int count = cnslMngMapper.selectNpayHistoryCount(cnslSearch);
		
        return new Page<NpayHistoryAddDtl>(count, list);
    }
	
	/**
	 * 상담내용 저장
	 * @param cnslDetail 상담 내역 정보
	 * @param userInfo 사용자 정보
	 * @return int
	 */
	@Transactional
	public int insertCnslCtnt(CnslDetail cnslDetail, CustomUserDetails userInfo) {
		int result = 0;
		// 미거래고객인 경우 결제폰정보 가상등록
		if(StringUtils.isEmpty(cnslDetail.getPayMphnId())){
			PayMphnInfo vrMphnInfo = new PayMphnInfo();
			vrMphnInfo.setMphnNo(cnslDetail.getMphnNo());
			result = this.userMapper.insertPayMphnInfo(vrMphnInfo);
			cnslDetail.setPayMphnId(vrMphnInfo.getPayMphnId());
		}
		// 상품정보 있을시 거래정보 셋팅
		if(cnslDetail.getTradeModel() != null && StringUtils.isNotEmpty(cnslDetail.getTradeModel().getTrdNo())){
			String[] trdNos = {cnslDetail.getTradeModel().getTrdNo()};
			CnslSearch search = new CnslSearch();
			search.setTrdNos(trdNos);
			TradeModel trd = this.getTradeTryList(search).get(0);
			cnslDetail.setEntpId(trd.getEntpId());
			cnslDetail.setCpCd(trd.getCpCd());
			cnslDetail.setCommcClf(trd.getCommcClf());
			cnslDetail.setPayCndiCd(trd.getTrdTypCd());
			cnslDetail.setTrdDd(trd.getReqDd());
			cnslDetail.setCnclYn(trd.getCnclYn());
			cnslDetail.setCnclDd(trd.getCnclDd());
		}
		// 접수 구분 셋팅 (제휴문의시 가맹점 설정)
		if(StringUtils.equals(cnslDetail.getCnslClfUprCd(), ComSCd.INQUIRE_COOPERATION.value())){
			cnslDetail.setRcptClf("C");		// 가맹점
		} else {
			cnslDetail.setRcptClf("P");		// 이용자
		}
		cnslDetail.setRegr(userInfo.getUserSeq());
		cnslDetail.setLastChgr(userInfo.getUserSeq());
		
		// TEXTAREA 개행문자 치환
		cnslDetail.setCnslCtnt(cnslDetail.getCnslCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		if(StringUtils.isNotEmpty(cnslDetail.getProcCtnt())){
			cnslDetail.setProcCtnt(cnslDetail.getProcCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		}
		
		// 1. 상담내용 등록
		result = this.cnslMngMapper.insertCnslDtl(cnslDetail);
		
		// 2. 상담내용 변경이력 추가
		this.cnslMngMapper.insertCnslChg(cnslDetail);
		
		// 3. 상담내용 결제연동 등록 (상품과 상관없을시 등록안함)
		if(cnslDetail.getTradeModel() != null && StringUtils.isNotEmpty(cnslDetail.getTradeModel().getTrdNo())){
			this.cnslMngMapper.insertCnslPayRel(cnslDetail);
		}
		
		return result;
	}
	
	/**
	 * 상담내용 수정
	 * @param cnslDetail 상담 내역 정보
	 * @param userInfo 사용자 정보
	 * @return int
	 */
	@Transactional
	public int updateCnslCtnt(CnslDetail cnslDetail, CustomUserDetails userInfo) {
		int result = 0;
		cnslDetail.setLastChgr(userInfo.getUserSeq());
		if(StringUtils.isNotEmpty(cnslDetail.getProcDt())){
			cnslDetail.setProcDd(cnslDetail.getProcDt().replaceAll("[.]", ""));
		}
		// TEXTAREA 개행문자 치환
		if(StringUtils.isNotEmpty(cnslDetail.getCnslCtnt())){
			cnslDetail.setCnslCtnt(cnslDetail.getCnslCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		}
		if(StringUtils.isNotEmpty(cnslDetail.getProcCtnt())){
			cnslDetail.setProcCtnt(cnslDetail.getProcCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		}
		// 1. 상담내용 수정
		result = this.cnslMngMapper.updateCnslDtl(cnslDetail);
		// 2. 수정사항 셋팅
		CnslDetail updatedCnsl = this.cnslMngMapper.selectCnslDetail(cnslDetail.getRcptNo());
		cnslDetail.setCnslCtnt(updatedCnsl.getCnslCtnt());
		cnslDetail.setProcCtnt(updatedCnsl.getProcCtnt());
		cnslDetail.setProcYn(updatedCnsl.getProcYn());
		cnslDetail.setProcDd(updatedCnsl.getProcDd());
		
		// 결과통보 발송전 수정인 경우
		if(StringUtils.isEmpty(cnslDetail.getRsltNotiMthd())){
			// 3. 상담내용 변경이력 추가
			this.cnslMngMapper.updateCnslChg(cnslDetail);
			// 4. 이관 처리여부 수정
			if(StringUtils.isNotEmpty(cnslDetail.getTjurYn()) && StringUtils.equals("Y", cnslDetail.getTjurYn())){
				CnslTjurModel cnslTjurModel = new CnslTjurModel();
				cnslTjurModel.setRcptNo(updatedCnsl.getRcptNo());
				cnslTjurModel.setTjurProcYn(updatedCnsl.getProcYn());
				if(StringUtils.isNotEmpty(updatedCnsl.getCnslTjurCd())){
					if(StringUtils.equals("C", updatedCnsl.getCnslTjurCd())){
						cnslTjurModel.setTjurClfFlg(updatedCnsl.getCnslTjurCd());
						if(StringUtils.isNotEmpty(updatedCnsl.getEntpId())){
							cnslTjurModel.setEntpId(updatedCnsl.getEntpId());
						}
						if(StringUtils.isNotEmpty(updatedCnsl.getCpCd())){
							cnslTjurModel.setCpCd(updatedCnsl.getCpCd());
						}
					} else {
						cnslTjurModel.setTjurClfFlg("B");
						cnslTjurModel.setDeptCd(updatedCnsl.getCnslTjurCd());
					}
				}
				cnslTjurModel.setCnslTjurCtnt(updatedCnsl.getCnslCtnt());
				cnslTjurModel.setProcTjurCtnt(StringUtils.isNotEmpty(updatedCnsl.getProcCtnt()) ? updatedCnsl.getProcCtnt() : "");
				this.updateCnslCpTjur(cnslTjurModel, userInfo);
			}
		} else {
			// 3. 상담내용 변경이력 추가
			this.cnslMngMapper.insertCnslChg(cnslDetail);
		}
		return result;
	}
	
	/**
	 * 거래시도 리스트 조회(페이징처리)
	 * @param cnslDetail 상담 내역 정보
	 * @return Page<TradeModel>
	 */
	public Page<TradeModel> getTradeTryListByPaging(CnslSearch cnslSearch) {
		final List<TradeModel> list = new ArrayList<>();
		int totalCount = this.cnslMngMapper.selectTradeTryListCount(cnslSearch);
		if(totalCount > 0){
			this.cnslMngMapper.selectTradeTryListByPaging(cnslSearch, new ResultHandler<TradeModel>(){
				@Override
				public void handleResult(ResultContext<? extends TradeModel> context) {
					TradeModel obj = context.getResultObject();
					obj.setTrdTypNm(codeNameMapper.selectCodeName(obj.getTrdTypCd()));
					obj.setCommcClfNm(codeNameMapper.selectCodeName(obj.getCommcClf()));
					if(StringUtils.equals("1", obj.getTransCd())){
						obj.setTransCdNm(message.getMessage("csm.cnslmng.data.transcd.pay"));		// 결제
					} else if(StringUtils.equals("2", obj.getTransCd())){
						obj.setTransCdNm(message.getMessage("csm.cnslmng.data.transcd.cancel"));	// 취소
					} else if(StringUtils.equals("3", obj.getTransCd())){
						obj.setTransCdNm(message.getMessage("csm.cnslmng.data.transcd.auth"));		// 인증
					}
					// 인증구분
					if(StringUtils.equals("S", obj.getAuthtiClfFlg())){
						obj.setAuthtiClfFlgNm("SMS");
					} else if(StringUtils.equals("A", obj.getAuthtiClfFlg())){
						obj.setAuthtiClfFlgNm("ARS");
					} else if(StringUtils.equals("O", obj.getAuthtiClfFlg())){
						obj.setAuthtiClfFlgNm("U-OTP");
					} else if(StringUtils.equals("N", obj.getAuthtiClfFlg())){
						obj.setAuthtiClfFlgNm(message.getMessage("csm.cnslmng.data.authflg.n"));
					}
					list.add(obj);
				}
			});
		}
		return new Page<TradeModel>(totalCount, list);
	}
	
	/**
	 * 거래시도 리스트 카운트 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return int
	 */
	public int getTradeTryListCount(CnslSearch cnslSearch){
		return this.cnslMngMapper.selectTradeTryListCount(cnslSearch);
	}
	
	/**
	 * 거래시도 리스트 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return List<TradeModel>
	 */
	public List<TradeModel> getTradeTryList(CnslSearch cnslSearch){
		final List<TradeModel> list = new ArrayList<>();
		this.cnslMngMapper.selectTradeTryList(cnslSearch, new ResultHandler<TradeModel>(){
			@Override
			public void handleResult(ResultContext<? extends TradeModel> context) {
				TradeModel obj = context.getResultObject();
				obj.setTrdTypNm(codeNameMapper.selectCodeName(obj.getTrdTypCd()));
				obj.setCommcClfNm(codeNameMapper.selectCodeName(obj.getCommcClf()));
				if(StringUtils.equals("1", obj.getTransCd())){
					obj.setTransCdNm(message.getMessage("csm.cnslmng.data.transcd.pay"));		// 결제
				} else if(StringUtils.equals("2", obj.getTransCd())){
					obj.setTransCdNm(message.getMessage("csm.cnslmng.data.transcd.cancel"));	// 취소
				} else if(StringUtils.equals("3", obj.getTransCd())){
					obj.setTransCdNm(message.getMessage("csm.cnslmng.data.transcd.auth"));		// 인증
				}
				// 인증구분
				if(StringUtils.equals("S", obj.getAuthtiClfFlg())){
					obj.setAuthtiClfFlgNm("SMS");
				} else if(StringUtils.equals("A", obj.getAuthtiClfFlg())){
					obj.setAuthtiClfFlgNm("ARS");
				} else if(StringUtils.equals("O", obj.getAuthtiClfFlg())){
					obj.setAuthtiClfFlgNm("U-OTP");
				} else if(StringUtils.equals("N", obj.getAuthtiClfFlg())){
					obj.setAuthtiClfFlgNm(message.getMessage("csm.cnslmng.data.authflg.n"));
				}
				list.add(obj);
			}
		});
		return list;
	}

	/**
	 * 거래시도 엑셀 다운로드
	 * @param cnslSearch 상담관리 조회 조건
	 * @param userInfo 
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getTradeTryListExcelDown(CnslSearch cnslSearch, CustomUserDetails userInfo) {
		Map<String, Object> resultMap = new HashMap<>();
		
		List<List<String>> headerList= new ArrayList<>();
		List<String> colName = new ArrayList<>();
		colName.add(message.getMessage("csm.cnslmng.th.conditaion.pay"));	// 결제 조건
		colName.add(message.getMessage("csm.cnslmng.th.date.trade"));		// 거래 일시
		colName.add(message.getMessage("csm.cnslmng.th.birthday"));			// 생년 월일
		colName.add(message.getMessage("csm.cnslmng.th.commc"));			// 통신사
		colName.add(message.getMessage("csm.cnslmng.th.custno"));			// 고객 번호
		colName.add(message.getMessage("csm.cnslmng.th.telno"));			// 휴대폰 번호
		colName.add(message.getMessage("csm.cnslmng.th.tradeamount"));		// 거래 금액
		colName.add(message.getMessage("csm.cnslmng.th.cpname"));			// 가맹점명
		colName.add(message.getMessage("csm.cnslmng.th.error.inner"));		// 내부오류
		colName.add(message.getMessage("csm.cnslmng.th.error.outer"));		// 외부오류
		colName.add(message.getMessage("csm.cnslmng.th.error.msg"));		// 오류 메세지
		colName.add(message.getMessage("csm.cnslmng.th.sendcode"));			// 전문코드
		colName.add(message.getMessage("csm.cnslmng.th.tradeno"));			// 결제승인(구매)번호
		colName.add(message.getMessage("csm.cnslmng.th.authflg"));			// 인증 구분
		colName.add(message.getMessage("csm.cnslmng.th.center"));			// 고객 센터
		headerList.add(colName);
		
		List<List<String>> dataList= new ArrayList<>();
		List<TradeModel> list = this.getTradeTryList(cnslSearch);
		// 고객 정보 LOG 수집
		if(!list.isEmpty()){
			idmsCustomerLogService.printIdmsLog(list.get(0).getPayrSeq(), "X", "CCS0023", "10001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		for(TradeModel model : list){
			List<String> colValue = new ArrayList<>();
			colValue.add(model.getTrdTypNm());
			colValue.add(model.getTrdDt());
			colValue.add(model.getBrthYmd());
			colValue.add(model.getCommcClfNm());
			colValue.add(model.getPayrSeq());
			colValue.add(model.getMphnNo());
			colValue.add(CustomStringUtils.formatMoney(model.getPayAmt()));
			colValue.add(model.getPaySvcNm());
			colValue.add(model.getInRsltCd());
			colValue.add(model.getExtrRsltCd());
			colValue.add(model.getInRsltMsg());
			colValue.add(model.getTransCdNm());
			colValue.add(model.getTrdNo());
			colValue.add(model.getAuthtiClfFlgNm());
			colValue.add(model.getTelNo());
			dataList.add(colValue);
		}
		
		short[] dataAlignArray = new short[]{
				CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER,
				CellStyle.ALIGN_CENTER, CellStyle.ALIGN_RIGHT, CellStyle.ALIGN_LEFT, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER,
				CellStyle.ALIGN_LEFT, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER
				};
		
		String title = message.getMessage("csm.cnslmng.header.paytry");
		resultMap.put("excelName", title);		// Excel File Name 설정
		resultMap.put("sheetName", title);		// sheet name 설정
		resultMap.put("headerList", headerList);
		resultMap.put("dataList", dataList);
		resultMap.put("dataAlignArray", dataAlignArray);
		return resultMap;
	}
	
	/**
	 * 거래시도 단건 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return TradeModel
	 */
	public TradeModel getTradeTry(CnslSearch cnslSearch){
		return this.getTradeTryList(cnslSearch).get(0);
	}

	/**
	 * 이전 상담내역 리스트 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return Page<CnslDetail>
	 */
	public Page<CnslDetail> getCnslChgListByPaging(CnslSearch cnslSearch) {
		final List<CnslDetail> list = new ArrayList<>();
		int totalCount = this.cnslMngMapper.selectCnslChgListCount(cnslSearch);
		if(totalCount > 0){
			this.cnslMngMapper.selectCnslChgListByPaging(cnslSearch, new ResultHandler<CnslDetail>(){
				@Override
				public void handleResult(ResultContext<? extends CnslDetail> context) {
					CnslDetail obj = context.getResultObject();
					// 상담내용 코드값 조회
					if(StringUtils.isNotEmpty(obj.getCnslClfUprCd())){
						obj.setCnslClfUprNm(codeNameMapper.selectCodeName(obj.getCnslClfUprCd()));
					}
					if(StringUtils.isNotEmpty(obj.getCnslClfLwrCd())){
						obj.setCnslClfLwrNm(codeNameMapper.selectCodeName(obj.getCnslClfLwrCd()));
					}
					if(StringUtils.isNotEmpty(obj.getPayCndiCd())){
						obj.setPayCndiNm(codeNameMapper.selectCodeName(obj.getPayCndiCd()));			
					}
					obj.setRcptMthdNm(codeNameMapper.selectCodeName(obj.getRcptMthdCd()));
					if(StringUtils.isNotEmpty(obj.getProcDd())){
						obj.setProcDt(
								new StringBuilder().append(obj.getProcDd().substring(0, 4)).append(".").append(obj.getProcDd().substring(4, 6))
								.append(".").append(obj.getProcDd().substring(6)).toString()
							);
					}
					if(StringUtils.isNotEmpty(obj.getCnslTjurCd())){
						if(StringUtils.equals("C", obj.getCnslTjurCd())){
							obj.setCnslTjurNm(message.getMessage("csm.cnslmng.data.tjur.cp"));					// 가맹점
						} else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ALLIANCE.value(), obj.getCnslTjurCd())){
							obj.setCnslTjurNm(message.getMessage("csm.cnslmng.data.tjur.dept.alliance.shrt"));	// 제휴
						} else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ADJUSTMENT.value(), obj.getCnslTjurCd())){
							obj.setCnslTjurNm(message.getMessage("csm.cnslmng.data.tjur.dept.adjust.shrt"));	// 정산
						} else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_TECHNICAL.value(), obj.getCnslTjurCd())){
							obj.setCnslTjurNm(message.getMessage("csm.cnslmng.data.tjur.dept.tech.shrt"));		// 기술
						}
					}
					if(StringUtils.isNotEmpty(obj.getCnslCtnt())){
						obj.setCnslCtnt(obj.getCnslCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
					}
					if(StringUtils.isNotEmpty(obj.getProcCtnt())){
						obj.setProcCtnt(obj.getProcCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
					}
					list.add(obj);
				}
			});
		}
		return new Page<CnslDetail>(totalCount, list);
	}
	
	/**
	 * 거래완료 건 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return TradeCmplModel
	 */
	public TradeCmplModel getTradeCompleteWithCnslChgList(CnslSearch cnslSearch) {
		TradeCmplModel model = new TradeCmplModel();
		List<TradeModel> tradeList = this.getTradeCompleteList(cnslSearch);
		model.setTradeList(new Page<TradeModel>(tradeList.size(), tradeList));
		model.setCnslDetailList(this.getCnslChgListByPaging(cnslSearch));
		return model;
	}

	/**
	 * 거래명세서 발송
	 * @param cnslSearch 상담관리 조회 조건
	 * @return RestResult<FileInfo>
	 */
	@Transactional
	public RestResult<FileInfo> sendReceipt(CnslSearch cnslSearch, CustomUserDetails userInfo){
		RestResult<FileInfo> result = new RestResult<>();
		// PDF 데이터 생성
		PdfModel model = this.makeReceiptData(cnslSearch, userInfo);
		// PDF 생성
		FileInfo pdfFile = this.fileService.createPdf(model);
		pdfFile.setFileClfCd(ComSCd.ATTACH_FILE_GENERAL.value());
		// 파일 테이블 등록
		int insertCtn = this.fileService.insertAttchFile(pdfFile);
		// 이메일 발송
		Email email = new Email();
		email.setAttchFileSeq(pdfFile.getAttchFileSeq());
		email.setIdvdSendYn("Y");
		email.setTitle(message.getMessage("common.email.label.title.value"));
		email.setSncCtnt(message.getMessage("csm.cnslmng.email.comment1"));
		email.setReceipient(cnslSearch.getEmail());
		this.emailService.sendEmailDB(email);
		// 반환값 셋팅
		result.setInsertCnt(insertCtn);
		result.setSuccess(true);
		result.setResult(pdfFile);
		return result;
	}
	
	/**
	 * 거래명세서 PDF 데이터 생성
	 * @param cnslSearch 상담관리 조회 조건
	 * @param userInfo 사용자 정보
	 * @return PdfModel
	 */
	public PdfModel makeReceiptData(CnslSearch cnslSearch, CustomUserDetails userInfo){
		String mphnNo = cnslSearch.getCustMphnNo();
		cnslSearch.setCustMphnNo(null);
		// 결제건 조회
		List<TradeModel> trdList = this.getTradeCompleteList(cnslSearch);
		
		// 고객 정보 LOG 수집
		if(!trdList.isEmpty()){
			// getPayrSeq = 고객번호
			idmsCustomerLogService.printIdmsLog(trdList.get(0).getPayrSeq(), "X", "CCS0018", "10001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		PdfModel model = new PdfModel(message.getMessage("csm.cnslmng.receipt.filename"), "statement.ftl");
		model.put("trdList", trdList);
		model.put("mphnNo", mphnNo);
		model.put("header", message.getMessage("csm.cnslmng.receipt.header"));
		model.put("title", message.getMessage("csm.cnslmng.receipt.title"));
		model.put("text1", message.getMessage("csm.cnslmng.receipt.text1"));
		model.put("text2", message.getMessage("csm.cnslmng.receipt.text2"));
		model.put("col1", message.getMessage("csm.cnslmng.receipt.col1"));
		model.put("col2", message.getMessage("csm.cnslmng.receipt.col2"));
		model.put("col3", message.getMessage("csm.cnslmng.receipt.col3"));
		model.put("col4", message.getMessage("csm.cnslmng.receipt.col4"));
		model.put("col5", message.getMessage("csm.cnslmng.receipt.col5"));
		model.put("col6", message.getMessage("csm.cnslmng.receipt.col6"));
		model.put("col7", message.getMessage("csm.cnslmng.receipt.col7"));
		model.put("col8", message.getMessage("csm.cnslmng.receipt.col8"));
		return model;
	}

	/**
	 * 결제차단내역 리스트 조회 (페이징처리)
	 * @param cnslSearch 상담관리 조회 조건
	 * @return Page<PayItcptModel>
	 */
	public Page<PayItcptModel> getPayItcptListByPaging(CnslSearch cnslSearch) {
		final List<PayItcptModel> list = new ArrayList<>();
		int totalCount = this.cnslMngMapper.selectPayItcptListCount(cnslSearch);
		if(totalCount > 0){
			this.cnslMngMapper.selectPayItcptListByPaging(cnslSearch, new ResultHandler<PayItcptModel>(){
				@Override
				public void handleResult(ResultContext<? extends PayItcptModel> context) {
					PayItcptModel obj = context.getResultObject();
					// 차단기준명 셋팅
					if(StringUtils.isNotEmpty(obj.getItcptClfFlg())){
						if(StringUtils.equalsIgnoreCase("U", obj.getItcptClfFlg())){
							obj.setItcptClfFlgNm(message.getMessage("csm.cnslmng.data.itcpt.clfflg.custno"));	// 고객번호
						} else if(StringUtils.equalsIgnoreCase("P", obj.getItcptClfFlg())){
							obj.setItcptClfFlgNm(message.getMessage("csm.cnslmng.data.itcpt.clfflg.telno"));	// 휴대폰
						}
					}
					// 차단여부명 셋팅
					if(StringUtils.isNotEmpty(obj.getProcClfFlg())){
						if(StringUtils.equalsIgnoreCase("R", obj.getProcClfFlg())){
							obj.setProcClfFlgNm(message.getMessage("csm.cnslmng.data.itcpt.procflg.reg"));		// 차단
						} else if(StringUtils.equalsIgnoreCase("U", obj.getProcClfFlg())){
							obj.setProcClfFlgNm(message.getMessage("csm.cnslmng.data.itcpt.procflg.unreg"));	// 해제
						}
					}
					obj.setRegrNm(userMngMapper.selectUsername(obj.getRegr()));
					obj.setLastChgrNm(userMngMapper.selectUsername(obj.getLastChgr()));
					obj.setMphnNo(CustomStringUtils.replaceTelNoForAsterik(obj.getMphnNo()));
					
					list.add(obj);
				}
			});
		}
		return new Page<PayItcptModel>(totalCount, list);
	}
	
	/**
	 * 결제차단내역 단건 조회
	 * @param itcptRegSeq 차단등록순번
	 * @return PayItcptModel
	 */
	public PayItcptModel getPayItcpt(String itcptRegSeq){
		PayItcptModel result = this.cnslMngMapper.selectPayItcpt(itcptRegSeq);
		// 차단기준명 셋팅
		if(StringUtils.isNotEmpty(result.getItcptClfFlg())){
			if(StringUtils.equalsIgnoreCase("U", result.getItcptClfFlg())){
				result.setItcptClfFlgNm(message.getMessage("csm.cnslmng.data.itcpt.clfflg.custno"));	// 고객번호
			} else if(StringUtils.equalsIgnoreCase("P", result.getItcptClfFlg())){
				result.setItcptClfFlgNm(message.getMessage("csm.cnslmng.data.itcpt.clfflg.telno"));		// 휴대폰
			}
		}
		// 차단여부명 셋팅
		if(StringUtils.isNotEmpty(result.getProcClfFlg())){
			if(StringUtils.equalsIgnoreCase("R", result.getProcClfFlg())){
				result.setProcClfFlgNm(message.getMessage("csm.cnslmng.data.itcpt.procflg.reg"));	// 차단
			} else if(StringUtils.equalsIgnoreCase("U", result.getProcClfFlg())){
				result.setProcClfFlgNm(message.getMessage("csm.cnslmng.data.itcpt.procflg.unreg"));	// 해제
			}
		}
		result.setRegrNm(userMngMapper.selectUsername(result.getRegr()));
		result.setLastChgrNm(userMngMapper.selectUsername(result.getLastChgr()));
		result.setProcRsn(XssPreventer.unescape(result.getProcRsn()));
		if(StringUtils.isNotEmpty(result.getItcptRelsRsn())){
			result.setItcptRelsRsn(XssPreventer.unescape(result.getItcptRelsRsn()));
		}
		return result;
	}

	/**
	 * 결제차단 등록
	 * @param payItcptModel 결제차단 등록 정보
	 * @param userInfo 사용자 정보
	 * @return RestResult<PayItcptModel>
	 */
	@Transactional
	public RestResult<PayItcptModel> insertPayItcpt(PayItcptModel payItcptModel, CustomUserDetails userInfo) {
		RestResult<PayItcptModel> result = new RestResult<>();
		payItcptModel.setRegr(userInfo.getUserSeq());

		// 미거래 고객인 경우, 가상 사용자로 결제폰 정보를 등록한다.
		if(StringUtils.isEmpty(payItcptModel.getPayMphnId())){
			PayMphnInfo vrMphnInfo = new PayMphnInfo();
			vrMphnInfo.setMphnNo(payItcptModel.getMphnNo());
			vrMphnInfo.setCommcClf(payItcptModel.getCommcClf());
			vrMphnInfo.setBrthYmd(payItcptModel.getBrthYmd().replaceAll("[.]", "").substring(2) + "9");
			vrMphnInfo.setBrthYy(payItcptModel.getBrthYmd().substring(0, payItcptModel.getBrthYmd().indexOf(".")));
			this.userMapper.insertPayMphnInfo(vrMphnInfo);
			payItcptModel.setPayMphnId(vrMphnInfo.getPayMphnId());
			payItcptModel.setPayrSeq(NO_REG_CUSTOMER);			// 미거래 고객 등록번호
		}
		
		PayMphnInfo payMphnInfo = new PayMphnInfo();
		payMphnInfo.setPayMphnId(payItcptModel.getPayMphnId());
		payMphnInfo.setPayrNm(payItcptModel.getPayrNm());
		
		if(StringUtils.equals(NO_REG_CUSTOMER, payItcptModel.getPayrSeq())){
			payMphnInfo.setPayrSeq(payItcptModel.getPayrSeq());
			payMphnInfo.setCommcClf(payItcptModel.getCommcClf());
			payMphnInfo.setBrthYmd(payItcptModel.getBrthYmd().replaceAll("[.]", "").substring(2));
			payMphnInfo.setBrthYy(payItcptModel.getBrthYmd().substring(0, payItcptModel.getBrthYmd().indexOf(".")));
		}
		
		/*
		 * 차단기준
		 * U : 고객번호 -> 이용자 정보 수정
		 * P : 휴대폰번호 -> 결제폰 정보 수정
		 */
		if(StringUtils.equalsIgnoreCase("U", payItcptModel.getItcptClfFlg())){
			// 이전 차단여부 조회
			PayrInfo payrInfo = this.userMapper.selectPayrInfo(payItcptModel.getPayrSeq());
			if(payrInfo != null && StringUtils.equalsIgnoreCase("Y", payrInfo.getPayItcptYn())){
				result.setSuccess(false);
				result.setMessage(message.getMessage("csm.cnslmng.msg.itcpt"));
				return result;
			}
			payrInfo = new PayrInfo();
			payrInfo.setPayrSeq(payItcptModel.getPayrSeq());
			payrInfo.setPayItcptYn("Y");
			payrInfo.setPayItcptRegr(payItcptModel.getRegr());
			this.userMapper.updatePayrInfo(payrInfo);
		} else if(StringUtils.equalsIgnoreCase("P", payItcptModel.getItcptClfFlg())){
			// 이전 차단여부 조회
			PayMphnInfo prevPayMphn = this.userMapper.selectPayMphnInfo(payMphnInfo);
			if(prevPayMphn != null && StringUtils.equalsIgnoreCase("Y", prevPayMphn.getPayItcptYn())){
				result.setSuccess(false);
				result.setMessage(message.getMessage("csm.cnslmng.msg.itcpt"));
				return result;
			}
			payMphnInfo.setPayItcptYn("Y");
			payMphnInfo.setPayItcptRegr(payItcptModel.getRegr());
		}
		this.userMapper.updatePayMphnInfo(payMphnInfo);
		// 결제차단이력 등록
		int insertCnt = this.cnslMngMapper.insertPayItcpt(payItcptModel);
		if(insertCnt > 0){
			result.setSuccess(true);
			result.setMessage(message.getMessage("csm.cnslmng.msg.regok"));
			result.setInsertCnt(insertCnt);
			result.setResult(payItcptModel);
		} else {
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
			logger.error("insertPayItcpt() - INSERT FAIL!");
		}
		
		return result;
	}

	/**
	 * 결제차단 수정
	 * @param payItcptModel 결제차단 등록 정보
	 * @param userInfo 사용자 정보
	 * @return int
	 */
	@Transactional
	public int updatePayItcpt(PayItcptModel payItcptModel, CustomUserDetails userInfo) {
		payItcptModel.setLastChgr(userInfo.getUserSeq());
		
		PayrInfo payrInfo = new PayrInfo();
		payrInfo.setPayrSeq(payItcptModel.getPayrSeq());
		payrInfo.setPayItcptRegr(payItcptModel.getLastChgr());
		
		PayMphnInfo payMphnInfo = new PayMphnInfo();
		payMphnInfo.setPayMphnId(payItcptModel.getPayMphnId());
		payMphnInfo.setPayrSeq(payItcptModel.getPayrSeq());
		payMphnInfo.setPayrNm(payItcptModel.getPayrNm());
		payMphnInfo.setCommcClf(payItcptModel.getCommcClf());
		payMphnInfo.setPayItcptRegr(payItcptModel.getLastChgr());
		
		// 차단건에 대한 수정일 경우
		if(StringUtils.equalsIgnoreCase("R", payItcptModel.getProcClfFlg())){
			/*
			 * 차단기준
			 * U : 고객번호 -> 이용자 정보 수정
			 * P : 휴대폰번호 -> 결제폰 정보 수정
			 */
			if(StringUtils.equalsIgnoreCase("U", payItcptModel.getItcptClfFlg())){
				payrInfo.setPayItcptYn("Y");
				payMphnInfo.setPayItcptYn("N");
			} else if(StringUtils.equalsIgnoreCase("P", payItcptModel.getItcptClfFlg())){
				payrInfo.setPayItcptYn("N");
				payMphnInfo.setPayItcptYn("Y");
			}
		} else {
			// 차단 해제하는 경우
			payrInfo.setPayItcptYn("N");
			payMphnInfo.setPayItcptYn("N");
		}
		// 사용자 정보의 결제차단 값 수정
		if(StringUtils.isNotEmpty(payrInfo.getPayrSeq()) && !StringUtils.equals(NO_REG_CUSTOMER, payrInfo.getPayrSeq())){
			this.userMapper.updatePayrInfo(payrInfo);
		}
		this.userMapper.updatePayMphnInfo(payMphnInfo);
		// 결제차단이력 수정
		return this.cnslMngMapper.updatePayItcpt(payItcptModel);
	}

	/**
	 * 처리내용 이관
	 * @param cnslTjurModel 이관접수 정보
	 * @param userInfo 사용자 정보
	 * @return String
	 */
	@Transactional
	public String updateCnslCpTjur(CnslTjurModel cnslTjurModel, CustomUserDetails userInfo) {
		String retMsg = "";
		
		cnslTjurModel.setRegr(userInfo.getUserSeq());
		cnslTjurModel.setTjurProc(userInfo.getUserSeq());
		// TEXTAREA 개행문자 처리
		cnslTjurModel.setCnslTjurCtnt(cnslTjurModel.getCnslTjurCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		cnslTjurModel.setProcTjurCtnt(cnslTjurModel.getProcTjurCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		int updateCnt = this.cnslMngMapper.updateCnslCpTjur(cnslTjurModel);
		if(updateCnt > 0){
			String targetTjur = "";
			if(StringUtils.equalsIgnoreCase("C", cnslTjurModel.getTjurClfFlg()) && StringUtils.isNotEmpty(cnslTjurModel.getEntpId())){
				// 가맹점 이관시
				EntpModel entpModel = this.commonMapper.selectEntpInfo(cnslTjurModel.getEntpId());
				targetTjur = entpModel.getEntpNm();
			} else if(StringUtils.equalsIgnoreCase("B", cnslTjurModel.getTjurClfFlg())){
				// 사업부 이관시
				if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ALLIANCE.value(), cnslTjurModel.getDeptCd())){
					targetTjur = message.getMessage("csm.cnslmng.data.tjur.dept.alliance");	// 제휴부서
				} else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ADJUSTMENT.value(), cnslTjurModel.getDeptCd())){
					targetTjur = message.getMessage("csm.cnslmng.data.tjur.dept.adjust");	// 정산부서
				} else if(StringUtils.equals(ComSCd.IMPAY_BUSINESS_DEPARTMENT_TECHNICAL.value(), cnslTjurModel.getDeptCd())){
					targetTjur = message.getMessage("csm.cnslmng.data.tjur.dept.tech");		// 기술부서
				}
			}
			String[] args = {cnslTjurModel.getRegDt(), targetTjur};
			retMsg = message.getMessage("csm.cnslmng.comment3", args);
			// 이관처리 이력 등록
			this.cnslMngMapper.insertCnslCpTjurHist(cnslTjurModel);
		}
		return retMsg;
	}
	
	/**
	 * 이관처리 이력 조회
	 * @param rcptNo 접수번호
	 * @return List
	 */
	public List<CnslTjurModel> getTjurProcHist(String rcptNo) {
		List<CnslTjurModel> tjurProcHistList = this.cnslMngMapper.selectCnslCpTjurHist(rcptNo);
		for(CnslTjurModel tjurHist : tjurProcHistList){
			tjurHist.setProcTjurCtnt(StringUtils.isNotEmpty(tjurHist.getProcTjurCtnt()) ? tjurHist.getProcTjurCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>") : "");
		}
		return tjurProcHistList;
	}
	
	/**
	 * 이관처리 이력 등록
	 * @param cnslTjurModel 이관접수 정보
	 * @param userInfo 사용자 정보
	 * @return int
	 */
	public int saveTjurProcHist(CnslTjurModel cnslTjurModel, CustomUserDetails userInfo){
		List<CnslTjurModel> tjurProcHistList = this.cnslMngMapper.selectCnslCpTjurHist(cnslTjurModel.getRcptNo());
		CnslTjurModel prevTjurHist = tjurProcHistList.get(tjurProcHistList.size()-1);
		cnslTjurModel.setCnslTjurCtnt(prevTjurHist.getCnslTjurCtnt());
		cnslTjurModel.setCpCd(prevTjurHist.getCpCd());
		cnslTjurModel.setDeptCd(prevTjurHist.getDeptCd());
		cnslTjurModel.setEntpId(prevTjurHist.getEntpId());
		cnslTjurModel.setTjurClfFlg(prevTjurHist.getTjurClfFlg());
		cnslTjurModel.setTjurProcYn(prevTjurHist.getTjurProcYn());
		cnslTjurModel.setRegr(userInfo.getUserSeq());
		cnslTjurModel.setTjurProc(userInfo.getUserSeq());
		this.cnslMngMapper.updateCnslCpTjur(cnslTjurModel);	// 이관처리 수정
		return this.cnslMngMapper.insertCnslCpTjurHist(cnslTjurModel); // 이관처리 이력 등록
	}

	/**
	 * 사업부 이관 메일 발송
	 * @param tjurEmail 이관 이메일 발송 정보
	 * @param userInfo 사용자 정보
	 * @return true if sending mail is sucess
	 */
	public boolean sendTjurProc(TjurEmail tjurEmail, CustomUserDetails userInfo) {
		boolean result = true;
		
		Map<String, Object> dataMap = new HashMap<>();
		tjurEmail.setCnslCtnt(tjurEmail.getCnslCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		tjurEmail.setReqCtnt(tjurEmail.getReqCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		// 매핑데이터 설정
		dataMap.put("data", tjurEmail);
		dataMap.put("resources", hostAddress + "/resources");
		dataMap.put("text1", message.getMessage("csm.cnslmng.th.center"));					// 고객센터
		dataMap.put("text2", message.getMessage("csm.cnslmng.th.date.rcpt"));				// 접수 일자
		dataMap.put("text3", message.getMessage("csm.cnslmng.th.rcptregr"));				// 접수자
		dataMap.put("text4", message.getMessage("csm.cnslmng.th.time.rcpt"));				// 접수 시간
		dataMap.put("text5", message.getMessage("csm.cnslmng.th.rcptno"));					// 접수 번호
		dataMap.put("text6", message.getMessage("csm.cnslmng.th.conditaion.pay"));			// 결제 조건
		dataMap.put("text7", message.getMessage("csm.cnslmng.th.date.trade"));				// 거래 일시
		dataMap.put("text8", message.getMessage("csm.cnslmng.th.commc"));					// 통신사
		dataMap.put("text9", message.getMessage("csm.cnslmng.th.date.cancel"));				// 취소 일시
		dataMap.put("text10", message.getMessage("csm.cnslmng.th.tradeamount"));			// 거래 금액
		dataMap.put("text11", message.getMessage("csm.cnslmng.th.cancel.yn"));				// 취소 가능 여부
		dataMap.put("text12", message.getMessage("csm.cnslmng.th.cpname"));					// 가맹점명
		dataMap.put("text13", message.getMessage("csm.cnslmng.th.tradeno"));				// 결제 승인 번호
		dataMap.put("text14", message.getMessage("csm.cnslmng.th.goodsname"));				// 상품명
		dataMap.put("text15", message.getMessage("csm.cnslmng.th.center"));					// 고객센터
		dataMap.put("text16", message.getMessage("csm.cnslmng.th.cnslgubun"));				// 상담구분
		dataMap.put("text17", message.getMessage("csm.cnslmng.th.event"));					// 이벤트
		dataMap.put("text18", message.getMessage("csm.cnslmng.th.cnsltype"));				// 상담 유형
		dataMap.put("text19", message.getMessage("csm.cnslmng.th.rcpttype"));				// 접수 유형
		dataMap.put("text20", message.getMessage("csm.cnslmng.th.content.cnsl"));			// 상담 내용
		dataMap.put("text21", message.getMessage("csm.cnslmng.th.content.tjur.br"));		// 이관 요청 내용
		dataMap.put("text22", message.getMessage("common.footer.info.cs.tel2"));
		dataMap.put("text23", message.getMessage("common.footer.info.cs.runtime2"));
		dataMap.put("text24", message.getMessage("csm.cnslmng.header.title.content.cnsl"));	// 상담 내역
		
		try {
			Template template = freeMarkerConfigurer.createConfiguration().getTemplate("mail_forum_trans.ftl");
			String htmlStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap)
					.replaceAll("\"", "\\\"").replaceAll("\'", "\\\'");
			
			logger.debug("SEND TJUR CONTENT :\n{}", htmlStr);
			
			CntcInfo cntcInfo = this.userMapper.selectCntcInfo(userInfo.getCntcSeq());
			Email email = new Email();
			email.setTo(tjurEmail.getEmailAddr());
			email.setFrom((cntcInfo != null) ? cntcInfo.getEmail() : null);	// 상담원의 이메일 주소로 전송
			email.setSubject(tjurEmail.getTitle());
			email.setMessage(htmlStr);
			this.emailService.sendEmail(email);
			
		} catch (IOException | TemplateException | MessagingException | MailSendException e) {
			logger.error(e.getMessage(), e);
			result = false;
		}
		return result;
	}

	/**
	 * 결과 통보
	 * @param notiResult 결과통보 정보
	 * @param userInfo 사용자 정보
	 * @return String
	 */
	@Transactional
	public String sendNotiResult(NotiResult notiResult, CustomUserDetails userInfo) {
		String seq = "";
		String sndReqSeq = "";
		// 1. 테스트 발송여부
		if(StringUtils.equals("Y", notiResult.getTestYn())){
			CntcInfo cntcInfo = this.userMapper.selectCntcInfo(userInfo.getCntcSeq());
			notiResult.setEmail(cntcInfo.getEmail());
			notiResult.setMphnNo(cntcInfo.getMphnNo());
		}
		// 2. 결과 통보 발송
		if(StringUtils.equals("M", notiResult.getRsltNotiMthd())){
			// 이메일 발송
			Email email = new Email();
			email.setIdvdSendYn("Y");
			email.setReceipient(notiResult.getEmail());
			email.setTitle(notiResult.getTitle());
			email.setSncCtnt(notiResult.getProcCtnt().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
			sndReqSeq = this.emailService.sendEmailDB(email);
		} else {
			// SMS 즉시발송
			String reqId = new StringBuilder().append("CCS_").append(DateUtil.getCurrentDateTime()).append((long)(Math.random()*(100))).toString();
			this.rabbitTemplate.convertAndSend(this.SMS_ROUTING_KEY, new SendSmsRequest(reqId, notiResult.getMphnNo(), notiResult.getProcCtnt()));
			// 완료된 상태로 발송내역 저장
			if(!StringUtils.equals("Y", notiResult.getTestYn())){
				SendReqModel sendReqModel = new SendReqModel();
				sendReqModel.setIdvdSndYn("Y");
				sendReqModel.setIdvdMphnNo(notiResult.getMphnNo());
				sendReqModel.setSmsSndWord(notiResult.getProcCtnt());
				sendReqModel.setSndChnlFlg("S");
				sendReqModel.setSndYn("Y");
				sndReqSeq = this.sendReqService.sendRequest(sendReqModel);
			}
		}
		// 3. 테스트 발송이 아닌경우, 상담내용에 결과통보저장
		if(!StringUtils.equals("Y", notiResult.getTestYn())){
			CnslDetail cnslDetail = new CnslDetail();
			cnslDetail.setRcptNo(notiResult.getRcptNo());
			cnslDetail.setRsltNotiMthd(notiResult.getRsltNotiMthd());
			cnslDetail.setSndReqSeq(sndReqSeq);
			this.updateCnslCtnt(cnslDetail, userInfo);
			seq = String.valueOf(cnslDetail.getSeq());
		}
		return seq;
	}
	
	/**
	 * 결과통보 조회
	 * @param sndReqSeq 발송요청번호
	 * @return NotiResult
	 */
	public NotiResult getNotiResult(String sndReqSeq) {
		NotiResult notiResult = null;
		SendReqModel reqDtl = this.sendReqMapper.selectSendReqDtl(sndReqSeq);
		if(reqDtl != null && StringUtils.equalsIgnoreCase("Y", reqDtl.getIdvdSndYn())){
			notiResult = new NotiResult();
			if(StringUtils.isNotEmpty(reqDtl.getIdvdEmail())){
				notiResult.setRsltNotiMthd("M");
				notiResult.setEmail(reqDtl.getIdvdEmail());
				notiResult.setTitle(reqDtl.getSndTitl());
				if(StringUtils.isNotEmpty(reqDtl.getSndCtnt())){
					notiResult.setProcCtnt(reqDtl.getSndCtnt());
				} else {
					notiResult.setProcCtnt("");
				}
			} else {
				notiResult.setRsltNotiMthd("S");
				notiResult.setMphnNo(reqDtl.getIdvdMphnNo());
				if(StringUtils.isNotEmpty(reqDtl.getSmsSndWord())){
					notiResult.setProcCtnt(reqDtl.getSmsSndWord().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
				} else {
					notiResult.setProcCtnt("");
				}
			}
		}
		return notiResult;
	}

	/**
	 * 문서보관함 리스트
	 * @return List<String>
	 */
	public List<String> getSmsDocumentList() {
		List<String> list = this.cnslMngMapper.selectSmsDocumentList();
		for(String str : list){
			str = XssPreventer.unescape(str).replaceAll("(\r\n|\n\r|\r|\n)", "<br/>");
		}
		return list;
	}

	/**
	 * 상담 시나리오 리스트 전체 조회
	 * @return List<CnslScrpt>
	 */
	public List<CnslScrpt> getCnslScriptContentAll() {
		List<CnslScrpt> list = this.cnslMngMapper.selectCnslCrspScrptAll();
		for(CnslScrpt obj : list){
			obj.setCnslClfUprCdNm(this.codeNameMapper.selectCodeName(obj.getCnslClfUprCd()));
			for(CnslScrpt scrpt : obj.getCnslScrptList()){
				scrpt.setCnslClfLwrCdNm(this.codeNameMapper.selectCodeName(scrpt.getCnslClfLwrCd()));
			}
		}
		return list;
	}
	
	/**
	 * 상담 시나리오 리스트 조회
	 * @param srchText 상담 시나리오 검색어
	 * @return List<CnslScrpt>
	 */
	public List<CnslScrpt> getCnslScriptContent(String srchText) {
		List<CnslScrpt> list = this.cnslMngMapper.selectCnslCrspScrpt(srchText);
		for(CnslScrpt obj : list){
			obj.setCnslClfUprCdNm(this.codeNameMapper.selectCodeName(obj.getCnslClfUprCd()));
			for(CnslScrpt scrpt : obj.getCnslScrptList()){
				scrpt.setCnslClfLwrCdNm(this.codeNameMapper.selectCodeName(scrpt.getCnslClfLwrCd()));
				// 강조표시
				if(StringUtils.isNotEmpty(srchText)){
					scrpt.setCnslCrspScrptCtnt(
							scrpt.getCnslCrspScrptCtnt().replaceAll(srchText, new StringBuilder().append("<em>").append(srchText).append("</em>").toString())
						);
					scrpt.setCnslCrspScrptQstn(
							scrpt.getCnslCrspScrptQstn().replaceAll(srchText, new StringBuilder().append("<em>").append(srchText).append("</em>").toString())
						);
				}
			}
		}
		return list;
	}

}