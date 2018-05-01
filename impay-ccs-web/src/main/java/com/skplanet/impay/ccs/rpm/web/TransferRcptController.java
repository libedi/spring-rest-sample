/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.rpm.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skplanet.impay.ccs.common.constants.ComSCd;
import com.skplanet.impay.ccs.common.log.idms.service.IdmsCustomerLogService;
import com.skplanet.impay.ccs.common.model.Code;
import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.CodeService;
import com.skplanet.impay.ccs.common.service.MessageByLocaleServiceImpl;
import com.skplanet.impay.ccs.common.util.DateUtil;
import com.skplanet.impay.ccs.common.util.ExcelView;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.rpm.model.RpmSearch;
import com.skplanet.impay.ccs.rpm.model.TjurRcpt;
import com.skplanet.impay.ccs.rpm.model.TjurUploadRcpt;
import com.skplanet.impay.ccs.rpm.service.TransferRcptService;

/**
 * 이관 접수 Controller
 * @author Sangjun, Park
 *
 */
@Controller
@RequestMapping("/rpm/trxRcpt")
public class TransferRcptController {
	private final static Logger logger = LoggerFactory.getLogger(TransferRcptController.class);
	
	@Autowired
	private ExcelView excelView;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private MessageByLocaleServiceImpl message;
	
	@Autowired
	private TransferRcptService transferRcptService;
	
	@Autowired
	private IdmsCustomerLogService idmsCustomerLogService;
	
	/**
	 * 이관 접수 화면
	 * @param model Model
	 * @return String
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public String transferRcptView(Model model){
		// 가맹점 코드(PG)
		List<Code> pgCdList = new ArrayList<>();
		pgCdList.add(codeService.selectCode(ComSCd.PG_CLF_CODE_DANAL.value()));
		pgCdList.add(codeService.selectCode(ComSCd.PG_CLF_CODE_MOBIL.value()));
		pgCdList.add(codeService.selectCode(ComSCd.PG_CLF_CODE_SKP.value()));
		pgCdList.add(codeService.selectCode(ComSCd.PG_CLF_CODE_DAWOO.value()));
		// 가맹점 코드에 비트패킹컴퍼니 추가
		Code bitComCode = new Code();
		bitComCode.setCd(ComSCd.PG_CLF_CODE_BIT.value());
		bitComCode.setCdNm(message.getMessage("rpm.tjur.option.bitCom"));
		pgCdList.add(bitComCode);
		
		// 이관 접수 구분 코드
		Code code1 = new Code();
		code1.setCd(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ALLIANCE.value());
		code1.setCdNm(message.getMessage("rpm.tjur.option.alliance"));
		Code code2 = new Code();
		code2.setCd(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ADJUSTMENT.value());
		code2.setCdNm(message.getMessage("rpm.tjur.option.adjust"));
		Code code3 = new Code();
		code3.setCd(ComSCd.IMPAY_BUSINESS_DEPARTMENT_TECHNICAL.value());
		code3.setCdNm(message.getMessage("rpm.tjur.option.tech"));
		
		List<Code> tjur = new ArrayList<Code>();
		tjur.add(code1);	// 제휴
		tjur.add(code2);	// 정산
		tjur.add(code3);	// 기술
		
		model.addAttribute("tjur", tjur);			// 이관접수구분
		model.addAttribute("pgCdList", pgCdList);			// 가맹점 코드(PG)
		model.addAttribute("today", DateUtil.getDate("yyyy.MM.dd", 0));
		
		return "rpm/transferRcpt";
	}
	
	/**
	 * 이관 접수 리스트 조회
	 * @param rpmSearch 접수/처리 조회 조건
	 * @return Page<TjurRcpt>
	 */
	@RequestMapping(value = "/search/tjurRcptList", method = RequestMethod.GET)
	public @ResponseBody Page<TjurRcpt> getTransferRcptList(RpmSearch rpmSearch, @AuthenticationPrincipal CustomUserDetails userInfo){
		Page<TjurRcpt> result = transferRcptService.getTransferRcptListByPaging(rpmSearch);
		// 고객 정보 LOG 수집
		if(result.getTotal() > 0){
			idmsCustomerLogService.printIdmsLog(result.getContent().get(0).getRcptNo(), "X", "CCS0038", "40001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		return result;
	}
	
	/**
	 * 이관 접수 리스트 총건수 조회
	 * @param rpmSearch 접수/처리 조회 조건
	 * @return int
	 */
	@RequestMapping(value = "/search/tjurRcptListCount", method = RequestMethod.GET)
	public @ResponseBody int getTransferRcptListCount(RpmSearch rpmSearch){
		return this.transferRcptService.getTransferRcptListCount(rpmSearch);
	}
	
	/**
	 * 이관 접수 리스트 엑셀 다운로드
	 * @param rpmSearch 접수/처리 조회 조건
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search/tjurRcptList/excelDown", method = RequestMethod.GET)
	public ModelAndView getTradeCompleteListExcelDown(RpmSearch rpmSearch, @AuthenticationPrincipal CustomUserDetails userInfo){
		return new ModelAndView(this.excelView, this.transferRcptService.getTransferRcptListExcelDown(rpmSearch, userInfo));
	}
	
	/**
	 * 업로드 이관 접수건 조회
	 * @param rcptNo 자료 등록 번호(접수 번호)
	 * @return TjurUploadRcpt
	 */
	@RequestMapping(value = "/search/uploadTjurRcpt", method = RequestMethod.GET)
	public @ResponseBody TjurUploadRcpt getUploadTransferRcpt(@RequestParam(value = "rcptNo") String rcptNo){
		return this.transferRcptService.getUploadTransferRcpt(rcptNo);
	}
	
	/**
	 * 업로드 이관 접수건 저장
	 * @param tjurUploadRcpt 이관 접수 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	@RequestMapping(value = "/save/uploadTjurRcpt", method = RequestMethod.POST)
	public @ResponseBody RestResult<String> saveUploadTransferRcpt(TjurUploadRcpt tjurUploadRcpt, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userInfo){
		RestResult<String> result = new RestResult<>();
		if(bindingResult.hasFieldErrors()){
			logger.error("saveUploadTransferRcpt() - {} :: {}", bindingResult.getFieldError().getDefaultMessage(), bindingResult.getFieldError().getField());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
		} else {
			result.setUpdateCnt(this.transferRcptService.saveUploadTransferRcpt(tjurUploadRcpt, userInfo));
			result.setSuccess(true);
		}
		return result;
	}
	
}
