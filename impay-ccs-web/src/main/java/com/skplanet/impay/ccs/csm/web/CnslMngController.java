/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.csm.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skplanet.impay.ccs.common.constants.ComLCd;
import com.skplanet.impay.ccs.common.constants.ComMCd;
import com.skplanet.impay.ccs.common.constants.ComSCd;
import com.skplanet.impay.ccs.common.log.idms.service.IdmsCustomerLogService;
import com.skplanet.impay.ccs.common.model.Code;
import com.skplanet.impay.ccs.common.model.FileInfo;
import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.model.PageParam;
import com.skplanet.impay.ccs.common.model.PdfModel;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.CodeService;
import com.skplanet.impay.ccs.common.service.MessageByLocaleServiceImpl;
import com.skplanet.impay.ccs.common.util.DateUtil;
import com.skplanet.impay.ccs.common.util.ExcelView;
import com.skplanet.impay.ccs.common.util.PdfView;
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
import com.skplanet.impay.ccs.csm.service.CnslMngService;
import com.skplanet.impay.ccs.framework.model.ValidationMarker.Create;
import com.skplanet.impay.ccs.framework.model.ValidationMarker.Update;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.sysm.model.AnnoDescReg;
import com.skplanet.impay.ccs.sysm.model.AnnoDescRegSearch;
import com.skplanet.impay.ccs.sysm.service.AnnoDescRegService;
import com.skplanet.impay.rms.constants.RmsComCd;

/**
 * 상담관리 Controller
 * @author Sangjun, Park
 *
 */
@Controller
@RequestMapping("/cnslMng")
public class CnslMngController {
	
	private static final Logger logger = LoggerFactory.getLogger(CnslMngController.class);
	
	private final String EMAIL_ADDR_ALLI = "alliance@impay.com";
	private final String EMAIL_ADDR_ADJT = "adjustment@impay.com";
	private final String EMAIL_ADDR_TECH = "tech@impay.com";
	
	@Autowired
	private CnslMngService cnslMngService;
	
	@Autowired
	private AnnoDescRegService annoDescRegService;
	
	@Autowired
	private ExcelView excelView;
	
	@Autowired
	private MessageByLocaleServiceImpl message;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private PdfView pdfView;
	
	@Autowired
	private IdmsCustomerLogService idmsCustomerLogService;
	
	/**
	 * 상담관리 화면
	 * @param search 상담관리 조회 조건
	 * @param model Model
	 * @param userInfo 사용자 정보
	 * @return String
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public String cnslView(CnslSearch search, Model model, @AuthenticationPrincipal CustomUserDetails userInfo){
		// 공지사항 파라미터 셋팅
		AnnoDescRegSearch notiSearch = new AnnoDescRegSearch();
		PageParam pageParam = new PageParam();
		pageParam.setPageIndex(1);
		pageParam.setRowCount(5);
		notiSearch.setPageParam(pageParam);
		
		String today = DateUtil.getDate("yyyy.MM.dd", 0);
		String prevYear = DateUtil.getDate("yyyy.MM.dd", -365);
		
		// 타메뉴 접근시 데이터 셋팅
		if(search != null && StringUtils.isNotEmpty(search.getRcptNo())){
			search.setSelectDate("T");
			search.setStrtDt(prevYear);
			search.setEndDt(today);
			search.setPageParam(pageParam);
			this.cnslMngService.setDefaultData(search, model, userInfo);
		}
		
		Page<AnnoDescReg> notiList = this.annoDescRegService.getNotiBoardList(notiSearch);
		List<Code> cnslClfCdList = this.codeService.selectSCodeByUprCd(ComLCd.COUNSEL_CLASSFICATION_UPPER.value());
		List<Code> cnslTypCdList = this.codeService.selectCodeByUprCd(ComMCd.COUNSEL_TYPE.value());
		List<Code> rcptMthdCdList = this.codeService.selectSCodeByUprCd(ComLCd.RECEIPT_TYPE.value());
		List<Code> evntTypCdList = this.codeService.selectCodeByUprCd(ComMCd.EVENT_TYPE.value());
		
		List<Code> rmsBlkReliefCdList = this.codeService.selectCodeByUprCd(RmsComCd.BLK_RELIEF_CD.value());
		if (!RmsComCd.MST_Y.value().equals(userInfo.getMstYn())) { // 최고관리자 권한이 아니면
			for (int i = 0; i < rmsBlkReliefCdList.size(); i++) {
				Code code = rmsBlkReliefCdList.get(i);
				if (RmsComCd.WHITE_LIST.value().equals(code.getCd())) { // White List 이면
					rmsBlkReliefCdList.remove(i); // 목록에서 White List 제거
				}
			}
		}
		List<Code> rmsRmRelsRsnCdList  = this.codeService.selectCodeByUprCd(RmsComCd.RM_RELS_RSN_CD.value());
		List<Code> rmsFraudRelsRsnCdList = this.codeService.selectCodeByUprCd(RmsComCd.FRAUD_RELS_RSN_CD.value());
		
		model.addAttribute("notiList", notiList);				// 공지사항
		model.addAttribute("cnslClfCdList", cnslClfCdList);		// 상담구분
		model.addAttribute("cnslTypCdList", cnslTypCdList);		// 상담유형
		model.addAttribute("rcptMthdCdList", rcptMthdCdList);	// 접수유형
		model.addAttribute("evntTypCdList", evntTypCdList);		// 이벤트
		
		model.addAttribute("rmsBlkReliefCdList", rmsBlkReliefCdList);		// RM 차단/해제 등록 : 적용범위
		model.addAttribute("rmsRmRelsRsnCdList", rmsRmRelsRsnCdList);		// RM 차단/해제 등록 : 해제사유
		model.addAttribute("rmsFraudRelsRsnCdList", rmsFraudRelsRsnCdList);	// 불량고객 해제 등록 : 해제사유
		
		model.addAttribute("todayMonth", DateUtil.getDate("yyyy.MM", 0));
		model.addAttribute("today", today);
		model.addAttribute("prevYear", prevYear);
		
		return "csm/cnslMng";
	}
	
	/**
	 * 고객정보 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @param userInfo 사용자 정보
	 * @return TradeCmplModel
	 */
	@RequestMapping(value = "/search/custInfo", method = RequestMethod.GET)
	public @ResponseBody TradeCmplModel getCustInfo(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		TradeCmplModel result = cnslMngService.getCustInfo(cnslSearch);
		// 고객 정보 LOG 수집
		if(result.getPayMphnInfo() != null){
			idmsCustomerLogService.printIdmsLog(result.getPayMphnInfo().getPayrSeq(), "X", "CCS0011", "10001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		
		return result;
	}
	
	/**
	 * 조회탭 - 거래완료 탭 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @param userInfo 사용자 정보
	 * @return TradeCmplModel
	 */
	@RequestMapping(value = "/search/totalTrdCmplList", method = RequestMethod.GET)
	public @ResponseBody TradeCmplModel getTotalTradeCompleteList(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		TradeCmplModel result = cnslMngService.getTotalTradeCompleteList(cnslSearch);
		if(result.getPayMphnInfo() != null){
			idmsCustomerLogService.printIdmsLog(result.getMphnList().get(0).getPayrSeq(), "X","CCS0012", "10001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		return result;
	}
	
	/**
	 * 거래완료 리스트 조회 (페이징 처리)
	 * @param cnslSearch 상담관리 조회 조건
	 * @param userInfo 사용자 정보
	 * @return Page<TradeModel>
	 */
	@RequestMapping(value = "/search/trdCmplList", method = RequestMethod.GET)
	public @ResponseBody Page<TradeModel> getTradeCompleteListByPaging(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		Page<TradeModel> result = cnslMngService.getTradeCompleteListByPaging(cnslSearch);
		// 고객 정보 LOG 수집
		if(result.getTotal() > 0){
			idmsCustomerLogService.printIdmsLog(result.getContent().get(0).getPayrSeq(), "X", "CCS0013", "10001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		return result;
	}
	
	/**
	 * 거래완료 리스트 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @param userInfo 사용자 정보
	 * @return List<TradeModel>
	 */
	@RequestMapping(value = "/search/trdCmplAllList", method = RequestMethod.GET)
	public @ResponseBody List<TradeModel> getTradeCompleteList(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		List<TradeModel> result = cnslMngService.getTradeCompleteList(cnslSearch);
		// 고객 정보 LOG 수집
		if(!result.isEmpty()){
			// getPayrSeq = 고객번호
			idmsCustomerLogService.printIdmsLog(result.get(0).getPayrSeq(), "X", "CCS0014", "10001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		return result;
	}
	
	/**
	 * 거래완료 리스트 카운트 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return int
	 */
	@RequestMapping(value = "/search/trdCmplListCount", method = RequestMethod.GET)
	public @ResponseBody int getTradeCompleteListCount(CnslSearch cnslSearch){
		return this.cnslMngService.getTradeCompleteListCount(cnslSearch);
	}
	
	/**
	 * 전체 상담이력 리스트 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return Page<CnslDetail>
	 */
	@RequestMapping(value = "/search/cnslDtlList", method = RequestMethod.GET)
	public @ResponseBody Page<CnslDetail> getCnslDetailList(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		Page<CnslDetail> result = cnslMngService.getCnslDetailListByPaging(cnslSearch);
		// 고객 정보 LOG 수집
		if(result.getTotal() > 0){
			// getPayrSeq = 고객번호
			idmsCustomerLogService.printIdmsLog(result.getContent().get(0).getPayrSeq(), "X", "CCS0015", "40001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		return result;
	}
	
	/**
	 * 조회탭 - 거래완료 엑셀 다운로드
	 * @param cnslSearch 상담관리 조회 조건
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search/trdCmplList/excelDown", method = RequestMethod.GET)
	public ModelAndView getTradeCompleteListExcelDown(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		return new ModelAndView(this.excelView, this.cnslMngService.getTradeCompleteListExcelDown(cnslSearch, userInfo));
	}
	
	/**
	 * 거래완료 단건 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return RestResult<TradeCmplModel>
	 */
	@RequestMapping(value = "/search/trdCmpl", method = RequestMethod.GET)
	public @ResponseBody RestResult<TradeCmplModel> getTradeComplete(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		RestResult<TradeCmplModel> result = new RestResult<>();
		TradeCmplModel model = this.cnslMngService.getTradeCompleteWithCnslChgList(cnslSearch);

		if(model.getTradeList().getTotal() == 0){
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
			result.setResult(null);
		} else {
			idmsCustomerLogService.printIdmsLog(model.getTradeList().getContent().get(0).getPayrSeq(), "X", "CCS0017", "10001", 1, userInfo);
			result.setSuccess(true);
			result.setMessage("search success");
			result.setResult(model);
		}
		return result;
	}
	
	/**
	 * 거래명세서 PDF 출력
	 * @param cnslSearch 상담관리 조회 조건
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/print/receipt", method = RequestMethod.GET)
	public ModelAndView printReceipt(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		PdfModel model = this.cnslMngService.makeReceiptData(cnslSearch, userInfo);
		model.setDownloadFileName(model.getDownloadFileName());
		return new ModelAndView(pdfView, model.getModel());
	}
	
	/**
	 * 거래명세서 발송
	 * @param cnslSearch 상담관리 조회 조건
	 * @param bindingResult BindingResult
	 * @return RestResult<FileInfo>
	 */
	@RequestMapping(value = "/send/receipt", method = RequestMethod.POST)
	public @ResponseBody RestResult<FileInfo> sendReceipt(CnslSearch cnslSearch, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userInfo) {
		RestResult<FileInfo> result = new RestResult<>();
		if(bindingResult.hasFieldErrors()){
			logger.error("sendReceipt() - {} :: {}", bindingResult.getFieldError().getDefaultMessage(), bindingResult.getFieldError().getField());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
		} else {
			result = this.cnslMngService.sendReceipt(cnslSearch, userInfo);
		}
		return result;
	}
	
	/**
	 * 상담내용, 처리내용 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return CnslDetail
	 */
	@RequestMapping(value = "/search/cnslCtnt", method = RequestMethod.GET)
	public @ResponseBody CnslDetail getCnslCtnt(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		CnslDetail result = cnslMngService.getCnslDetail(cnslSearch); 
		if(result != null){
			idmsCustomerLogService.printIdmsLog(result.getPayrSeq(), "X", "CCS0019", "40001", 1, userInfo);
		}
		return result;
	}
	
	/**
	 * 상담내용 저장
	 * @param cnslDetail 상담 내역 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	@RequestMapping(value = "/save/cnslCtnt", method = RequestMethod.POST)
	public @ResponseBody RestResult<String> saveCnslCtnt(@Validated(Create.class) CnslDetail cnslDetail, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userInfo){
		
		RestResult<String> result = new RestResult<>();
		if(bindingResult.hasFieldErrors()){
			logger.error("saveCnslCtnt() - {} :: {}", bindingResult.getFieldError().getDefaultMessage(), bindingResult.getFieldError().getField());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
		} else {
			int insertCnt = this.cnslMngService.insertCnslCtnt(cnslDetail, userInfo);
			result.setSuccess(true);
			result.setMessage("success");
			result.setInsertCnt(insertCnt);
			result.setResult(cnslDetail.getRcptNo());
			logger.debug("SUCCESS TO SAVE COUNSEL CONTENT!");
		}
		return result;
	}
	
	/**
	 * 상담내용 수정
	 * @param cnslDetail 상담 내역 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	@RequestMapping(value = "/update/cnslCtnt", method = RequestMethod.POST)
	public @ResponseBody RestResult<String> updateCnslCtnt(@Validated(Update.class) CnslDetail cnslDetail, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userInfo){

		RestResult<String> result = new RestResult<>();
		if(bindingResult.hasFieldErrors()){
			logger.error("updateCnslCtnt() - {} :: {}", bindingResult.getFieldError().getDefaultMessage(), bindingResult.getFieldError().getField());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
		} else {
			int updateCnt = this.cnslMngService.updateCnslCtnt(cnslDetail, userInfo);
			idmsCustomerLogService.printIdmsLog(cnslDetail.getPayrSeq(), "X", "CCS0021", "40001", 1, userInfo);
			result.setSuccess(true);
			result.setMessage("success");
			result.setUpdateCnt(updateCnt);
			result.setResult(cnslDetail.getRcptNo());
			logger.debug("update!");
		}
		return result;
	}
	/**
	 * 미납이력 및 가산금 부과내역 popup 호출
	 * @param model Model
	 * @param payMphnId 결제폰ID
	 * @return String
	 */
	@RequestMapping(value = "/openNpayHistoryPopup/{payMphnId}", method = RequestMethod.GET)
	public String NpayHistoryPopup(Model model, @PathVariable("payMphnId") String payMphnId ){
		model.addAttribute("payMphnId", payMphnId);
		return "csm/popup/NpayHistoryPopup";
	}
    /**
     * 미납이력 및 가산금 부과내역 layer 조회
     * @param cnslSearch 상담관리 조회 조건
     * @param userInfo 사용자 정보
     * @return Page<NpayHistoryAddDtl>
     */
    @RequestMapping(value = "/layer/npayHistory", method = RequestMethod.POST)
    public @ResponseBody Page<NpayHistoryAddDtl> getNapyHistory(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
    	Page<NpayHistoryAddDtl> result = cnslMngService.getNpayHistoryList(cnslSearch);
    	// 고객 정보 LOG 수집
		if(result.getTotal() > 0){
			idmsCustomerLogService.printIdmsLog(result.getContent().get(0).getPayMphnId(), "X", "CCS0009", "10001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
        return result;
    }
	
	/**
	 * 거래시도 탭 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return Page<TradeModel>
	 */
	@RequestMapping(value = "/search/trdTryList", method= RequestMethod.GET)
	public @ResponseBody Page<TradeModel> getTradeTryList(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		Page<TradeModel> result = cnslMngService.getTradeTryListByPaging(cnslSearch); 
		// 고객 정보 LOG 수집
		if(result.getTotal() > 0){
			idmsCustomerLogService.printIdmsLog(result.getContent().get(0).getPayrSeq(), "X", "CCS0022", "10001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		return result;
	}
	
	/**
	 * 거래시도 리스트 엑셀 다운로드
	 * @param cnslSearch 상담관리 조회 조건
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search/trdTryList/excelDown", method = RequestMethod.GET)
	public ModelAndView getTradeTryListExcelDown(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		return new ModelAndView(this.excelView, this.cnslMngService.getTradeTryListExcelDown(cnslSearch, userInfo));
	}
	
	/**
	 * 거래시도 리스트 카운트 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return int
	 */
	@RequestMapping(value = "/search/trdTryListCount", method = RequestMethod.GET)
	public @ResponseBody int getTradeTryListCount(CnslSearch cnslSearch){
		return this.cnslMngService.getTradeTryListCount(cnslSearch);
	}
	
	/**
	 * 거래시도 단건 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return TradeModel
	 */
	@RequestMapping(value = "/search/trdTry", method= RequestMethod.GET)
	public @ResponseBody TradeModel getTradeTry(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		TradeModel result = cnslMngService.getTradeTry(cnslSearch);
		// 고객 정보 LOG 수집
		if(StringUtils.isNotEmpty(result.getPayrSeq())){
			idmsCustomerLogService.printIdmsLog(result.getPayrSeq(), "X", "CCS0024", "10001", 1, userInfo);
		}
		return result;
	}
	
	/**
	 * 이전 상담내역 리스트 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return Page<CnslDetail>
	 */
	@RequestMapping(value = "/search/cnslChgList", method = RequestMethod.GET)
	public @ResponseBody Page<CnslDetail> getCnslChgList(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		Page<CnslDetail> result = cnslMngService.getCnslChgListByPaging(cnslSearch);
		// 고객 정보 LOG 수집
		if(result.getTotal() > 0){
			idmsCustomerLogService.printIdmsLog(result.getContent().get(0).getPayrSeq(), "X", "CCS0025", "40001", 1, userInfo);
		}
		return result;
	}
	
	/**
	 * 결제차단내역 등록화면 폼 데이터 조회
	 * @return List<Code>
	 */
	@RequestMapping(value = "/payItcpt/view", method = RequestMethod.GET)
	public @ResponseBody List<Code> payItcptRegView(){
		List<Code> commList = new ArrayList<>();
		commList.addAll(this.codeService.selectCodeByUprCd(ComMCd.COMMUNICATIONS_CORPORATION_CODE.value()));
		commList.addAll(this.codeService.selectCodeByUprCd(ComMCd.COMMUNICATIONS_CORPORATION_MVNO.value()));
		return commList;
	}
	
	/**
	 * 결제차단내역 리스트 조회
	 * @param cnslSearch 상담관리 조회 조건
	 * @return Page<PayItcptModel>
	 */
	@RequestMapping(value = "/search/payItcptList", method = RequestMethod.GET)
	public @ResponseBody Page<PayItcptModel> getPayItcptList(CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		Page<PayItcptModel> result = cnslMngService.getPayItcptListByPaging(cnslSearch);
		// 고객 정보 LOG 수집
		if(result.getTotal() > 0){
			idmsCustomerLogService.printIdmsLog(result.getContent().get(0).getPayrSeq(), "X", "CCS0026", "10001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		return result;
	}
	
	/**
	 * 결제차단내역 단건 조회
	 * @param itcptRegSeq 차단등록순번
	 * @return PayItcptModel
	 */
	@RequestMapping(value = "/search/payItcpt", method = RequestMethod.GET)
	public @ResponseBody PayItcptModel getPayItcpt(@RequestParam(value = "itcptRegSeq") String itcptRegSeq, @AuthenticationPrincipal CustomUserDetails userInfo){
		PayItcptModel result = cnslMngService.getPayItcpt(itcptRegSeq);
		// 고객 정보 LOG 수집
		if(result != null){
			idmsCustomerLogService.printIdmsLog(result.getPayrSeq(), "X", "CCS0027", "10001", 1, userInfo);
		}
		return result;
	}
	
	/**
	 * 결제차단 등록
	 * @param payItcptModel 결제차단 등록 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @return RestResult<PayItcptModel>
	 */
	@RequestMapping(value = "/save/payItcpt", method = RequestMethod.POST)
	public @ResponseBody RestResult<PayItcptModel> savePayItcpt(@Validated(Create.class) PayItcptModel payItcptModel, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userInfo){
		
		RestResult<PayItcptModel> result = new RestResult<>();
		if(bindingResult.hasFieldErrors()){
			logger.error("savePayItcpt() - {} :: {}", bindingResult.getFieldError().getDefaultMessage(), bindingResult.getFieldError().getField());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
		} else {
			// 고객 정보 LOG 수집
			idmsCustomerLogService.printIdmsLog(payItcptModel.getPayrSeq(), "X", "CCS0028", "10001", 1, userInfo);
			result = this.cnslMngService.insertPayItcpt(payItcptModel, userInfo);
		}
		return result;
	}
	
	/**
	 * 결제차단 해제
	 * @param payItcptModel 결제차단 등록 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	@RequestMapping(value = "/update/payItcpt", method = RequestMethod.POST)
	public @ResponseBody RestResult<String> updatePayItcpt(@Validated(Update.class) PayItcptModel payItcptModel, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userInfo){
		
		RestResult<String> result = new RestResult<>();
		if(bindingResult.hasFieldErrors()){
			logger.error("updatePayItcpt() - {} :: {}", bindingResult.getFieldError().getDefaultMessage(), bindingResult.getFieldError().getField());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
		} else {
			// 고객 정보 LOG 수집
			if(payItcptModel.getPayrSeq() != null){
				idmsCustomerLogService.printIdmsLog(payItcptModel.getPayrSeq(), "X", "CCS0029", "10001", 1, userInfo);
			}
			int updateCnt = this.cnslMngService.updatePayItcpt(payItcptModel, userInfo);
			if(updateCnt > 0){
				result.setSuccess(true);
				result.setMessage("success");
				result.setUpdateCnt(updateCnt);
				logger.debug("update!");
			} else {
				result.setSuccess(false);
				result.setMessage(message.getMessage("common.error.message"));
				logger.error("updatePayItcpt() - UPDATE FAIL!");
			}
		}
		return result;
	}
	
	/**
	 * 처리내용 이관
	 * @param cnslTjurModel 이관접수 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	@RequestMapping(value = "/tjurProc/procCtnt", method = RequestMethod.POST)
	public @ResponseBody RestResult<String> tjurProcCtnt(@Validated(Create.class) CnslTjurModel cnslTjurModel, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userInfo){
		
		RestResult<String> result = new RestResult<>();
		if(bindingResult.hasFieldErrors()){
			logger.error("tjurProcCtnt() - {} :: {}", bindingResult.getFieldError().getDefaultMessage(), bindingResult.getFieldError().getField());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
		} else {
			// 고객 정보 LOG 수집
			idmsCustomerLogService.printIdmsLog(cnslTjurModel.getRcptNo(), "X", "CCS0030", "40001", 1, userInfo);
			
			String resultMsg = this.cnslMngService.updateCnslCpTjur(cnslTjurModel, userInfo);
			if(StringUtils.isNotEmpty(resultMsg)){
				result.setSuccess(true);
				result.setMessage("update success");
				result.setResult(resultMsg);
				logger.debug("TJUR UPDATE SUCCESS !");
			} else {
				result.setSuccess(false);
				result.setMessage(message.getMessage("common.error.message"));
				result.setResult(resultMsg);
				logger.error("TJUR UPDATE FAIL !");
			}
		}
		return result;
	}
	
	/**
	 * 이관처리 이력 조회
	 * @param rcptNo 접수번호
	 * @return List<CnslTjurModel>
	 */
	@RequestMapping(value = "/search/tjurProcHist", method = RequestMethod.GET)
	public @ResponseBody List<CnslTjurModel> getTjurProcHist(@RequestParam(value = "rcptNo") String rcptNo){
		return this.cnslMngService.getTjurProcHist(rcptNo);
	}
	
	/**
	 * 이관처리 이력 등록
	 * @param cnslTjurModel 이관접수 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @return RestResult<CnslTjurModel>
	 */
	@RequestMapping(value = "/save/tjurProcHist", method = RequestMethod.POST)
	public @ResponseBody RestResult<CnslTjurModel> saveTjurProcHist(@Validated(Update.class) CnslTjurModel cnslTjurModel,
			BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userInfo){
		
		RestResult<CnslTjurModel> result = new RestResult<>();
		if(bindingResult.hasFieldErrors()){
			logger.error("saveTjurProcHist() - {} :: {}", bindingResult.getFieldError().getDefaultMessage(), bindingResult.getFieldError().getField());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
		} else {
			int insertCnt = this.cnslMngService.saveTjurProcHist(cnslTjurModel, userInfo);
			if(insertCnt > 0){
				// 고객 정보 LOG 수집
				idmsCustomerLogService.printIdmsLog(cnslTjurModel.getRcptNo(), "X", "CCS0031", "40001", 1, userInfo);
				result.setSuccess(true);
				result.setInsertCnt(insertCnt);
				result.setMessage("SUCCESS TO INSERT TJUR HISTORY");
				logger.debug("SUCCESS TO INSERT TJUR HISTORY");
			} else {
				result.setSuccess(false);
				result.setMessage(message.getMessage("common.error.message"));
				logger.error("FAIL TO INSERT TJUR HISTORY");
			}
			result.setResult(cnslTjurModel);
		}
		return result;
	}
	
	/**
	 * 사업부 이관 메일 폼
	 * @return List<Code>
	 */
	@RequestMapping(value = "/tjurProc/view", method = RequestMethod.GET)
	public @ResponseBody List<Code> viewTjurEmailForm(){
		Code code1 = new Code();
		code1.setCd(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ALLIANCE.value());
		code1.setCdNm(message.getMessage("csm.cnslmng.data.tjur.dept.alliance.shrt"));
		code1.setPrepWord1(this.EMAIL_ADDR_ALLI);
		
		Code code2 = new Code();
		code2.setCd(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ADJUSTMENT.value());
		code2.setCdNm(message.getMessage("csm.cnslmng.data.tjur.dept.adjust.shrt"));
		code2.setPrepWord1(this.EMAIL_ADDR_ADJT);
		
		Code code3 = new Code();
		code3.setCd(ComSCd.IMPAY_BUSINESS_DEPARTMENT_TECHNICAL.value());
		code3.setCdNm(message.getMessage("csm.cnslmng.data.tjur.dept.tech.shrt"));
		code3.setPrepWord1(this.EMAIL_ADDR_TECH);
		
		List<Code> list = new ArrayList<Code>();
		list.add(code1);
		list.add(code2);
		list.add(code3);
		
		return list;
	}
	
	/**
	 * 사업부 이관 메일 발송
	 * @param tjurEmail 이관 이메일 발송 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	@RequestMapping(value = "/tjurProc/sendEmail", method = RequestMethod.POST)
	public @ResponseBody RestResult<String> sendTjurProc(@Validated(Create.class) TjurEmail tjurEmail, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userInfo){
		
		RestResult<String> result = new RestResult<>();
		if(bindingResult.hasFieldErrors()){
			logger.error("sendTjurProc() - {} :: {}", bindingResult.getFieldError().getDefaultMessage(), bindingResult.getFieldError().getField());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
		} else {
			if(this.cnslMngService.sendTjurProc(tjurEmail, userInfo)){
				// 고객 정보 LOG 수집
				idmsCustomerLogService.printIdmsLog(tjurEmail.getRcptNo(), "X", "CCS0032", "40001", 1, userInfo);
				result.setSuccess(true);
				result.setMessage("SEND SUCCESS!");
				logger.debug("DEPARTMENT TJUR SUCCESS!");
			} else {
				result.setSuccess(false);
				result.setMessage(message.getMessage("common.error.message"));
				logger.debug("DEPARTMENT TJUR FAIL!");
			}
		}
		return result;
	}
	
	/**
	 * 결과 통보
	 * @param notiResult 결과통보 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	@RequestMapping(value = "/send/notiRslt", method = RequestMethod.POST)
	public @ResponseBody RestResult<String> sendNotiResult(@Validated(Create.class) NotiResult notiResult, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userInfo){
		
		RestResult<String> result = new RestResult<>();
		if(bindingResult.hasFieldErrors()){
			logger.error("sendNotiResult() - {} :: {}", bindingResult.getFieldError().getDefaultMessage(), bindingResult.getFieldError().getField());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
		} else {
			result.setResult(this.cnslMngService.sendNotiResult(notiResult, userInfo));
			result.setSuccess(true);
			result.setMessage("SEND SUCCESS!");
			logger.debug("SEND SUCCESS!");
		}
		return result;
	}
	
	/**
	 * 결과통보 조회
	 * @param sndReqSeq 발송요청번호
	 * @return NotiResult
	 */
	@RequestMapping(value = "/search/notiRslt", method = RequestMethod.GET)
	public @ResponseBody NotiResult getNotiResult(@RequestParam(value = "sndReqSeq") String sndReqSeq, @AuthenticationPrincipal CustomUserDetails userInfo){
		NotiResult result = cnslMngService.getNotiResult(sndReqSeq); 
		if(result != null){
			// 고객 정보 LOG 수집
			idmsCustomerLogService.printIdmsLog(result.getRcptNo(), "X", "CCS0033", "40001", 1, userInfo);
		}
		return result;
	}
	
	/**
	 * 문서보관함 리스트 조회
	 * @return List<String>
	 */
	@RequestMapping(value = "/search/smsDocuList", method = RequestMethod.GET)
	public @ResponseBody List<String> getSmsDocumentList(){
		return this.cnslMngService.getSmsDocumentList();
	}
	
	/**
	 * 상담 시나리오 팝업 호출
	 * @param model Model
	 * @return String
	 */
	@RequestMapping(value = "/openCnslScenarioPopup", method = RequestMethod.GET)
	public String cnslScenarioPopup(Model model){
		List<Code> cnslClfCdList = this.codeService.selectSCodeByUprCd(ComLCd.COUNSEL_CLASSFICATION_UPPER.value());	// 상담구분
		List<CnslScrpt> cnslScnrList = this.cnslMngService.getCnslScriptContentAll();
		model.addAttribute("cnslClfCdList", cnslClfCdList);		// 상담구분
		model.addAttribute("cnslScnrList", cnslScnrList);
		return "csm/popup/cnslScenarioPopup";
	}
	
	/**
	 * 상담 시나리오 조회
	 * @param srchText 상담 시나리오 검색어
	 * @return List<CnslScrpt>
	 */
	@RequestMapping(value = "/search/cnslScrptList", method = RequestMethod.POST)
	public @ResponseBody List<CnslScrpt> getCnslScriptContent(@RequestParam(value = "srchText") String srchText){
		return this.cnslMngService.getCnslScriptContent(srchText);
	}
	
	/**
	 * 거래명세서 인쇄팝업 호출
	 * @param model Model
	 * @param cnslSearch 상담관리 조회 조건
	 * @return String
	 */
	@RequestMapping(value = "/openReceiptPrint", method = RequestMethod.GET)
	public String openReceiptPopup(Model model, CnslSearch cnslSearch, @AuthenticationPrincipal CustomUserDetails userInfo){
		String mphnNo = cnslSearch.getCustMphnNo();
		cnslSearch.setCustMphnNo(null);
		model.addAttribute("mphnNo", mphnNo);
		model.addAttribute("trdList", this.cnslMngService.getTradeCompleteList(cnslSearch));
		// 고객 정보 LOG 수집
		idmsCustomerLogService.printIdmsLog(cnslSearch.getPayrSeq(), "X", "CCS0034", "X", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		return "csm/popup/receiptPrint";
	}
}
