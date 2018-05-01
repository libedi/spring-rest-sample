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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.impay.ccs.common.constants.ComLCd;
import com.skplanet.impay.ccs.common.constants.ComMCd;
import com.skplanet.impay.ccs.common.log.idms.service.IdmsCustomerLogService;
import com.skplanet.impay.ccs.common.model.Code;
import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.CodeService;
import com.skplanet.impay.ccs.csm.model.CnslSearch;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.rpm.model.PayBackRcpt;
import com.skplanet.impay.ccs.rpm.model.PayBackSearch;
import com.skplanet.impay.ccs.rpm.service.PayBackRcptService;

/**
 * CCS 접수/처리현황관리 
 * - 환불접수 Controller
 * @author Junehee, Jang
 *
 */
@Controller
@RequestMapping("/rpm/payBackRcpt")
public class PayBackRcptController {
	
	private final static Logger logger = LoggerFactory.getLogger(PayBackRcptController.class);
	
	@Autowired
	private PayBackRcptService payBackRcptService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private IdmsCustomerLogService idmsCustomerLogService;
	
	/**
	 * 환불접수 화면
	 * @param model Model
	 * @return String
	 */
	@RequestMapping(value = "/view", method=RequestMethod.POST)	
	public String payBackRcptView(Model model){
		
		// 이통사
		List<Code> commcClf = new ArrayList<Code>();
		commcClf.addAll(codeService.selectCodeByUprCd(ComMCd.COMMUNICATIONS_CORPORATION_CODE.value()));
		commcClf.addAll(codeService.selectCodeByUprCd(ComMCd.COMMUNICATIONS_CORPORATION_MVNO.value()));

		// 환불 유형 
		List<Code> pay = codeService.selectSCodeByUprCd(ComLCd.PAYBACK_TYPE.value());
		
		model.addAttribute("commcClf", commcClf);
		model.addAttribute("pay", pay);
		return "rpm/payBackRcpt";
	}
	/**
	 * 환불 접수 목록 조회
	 * @param payBackSearch 환불 접수 조회 조건
	 * @param userInfo 사용자 정보
	 * @return Page<PayBackRcpt>
	 */
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public @ResponseBody Page<PayBackRcpt> search(PayBackSearch payBackSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		Page<PayBackRcpt> result =	payBackRcptService.getPayBackRcptList(payBackSearch);
		// 고객 정보 LOG 수집
		if(result.getTotal() > 0 ){
			idmsCustomerLogService.printIdmsLog(result.getContent().get(0).getPybkRcptNo(), result.getContent().get(0).getPayrNm(), "CCS0004", "10001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo); 
		}
		return result; 
	}
	/**
	 * 환불 접수 상세 조회
	 * @param pybkRcptNo 환불 접수 번호
	 * @param userInfo 사용자 정보
	 * @return PayBackRcpt
	 */
	@RequestMapping(value = "/getPayBackRcptInfo/{pybkRcptNo}", method=RequestMethod.GET)
   public @ResponseBody PayBackRcpt getPayBackRcptInfo(@PathVariable("pybkRcptNo") String pybkRcptNo, @AuthenticationPrincipal CustomUserDetails userInfo) {
		PayBackRcpt result = payBackRcptService.getPayBackRcptInfo(pybkRcptNo); 
		// 고객 정보 LOG 수집
		if(result != null){
			idmsCustomerLogService.printIdmsLog(result.getPybkRcptNo(), result.getPayrNm(), "CCS0005", "10001", 1, userInfo); 
		}

       return result;
	}
	 /**
     * 환불 접수 등록
     * @param payBackRcpt 환불 접수 번호
     * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
     * @param model Model
     * @return RestResult<String>
     */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public @ResponseBody RestResult<String> addPayBackRcpt(PayBackRcpt payBackRcpt,BindingResult bindingResult, Model model, @AuthenticationPrincipal CustomUserDetails userInfo){
		RestResult<String> result = new RestResult<String>();
		
		if(bindingResult.hasErrors()){
			model.addAttribute("payBackRcpt", payBackRcpt);
			result.setMessage("error");
			result.setSuccess(false);
		}else{
			result = payBackRcptService.addPayBackRcpt(payBackRcpt, userInfo);
		}
		return result;
	}
	/**
	 * 환불 접수 
	 * CHECK 된 ROW 삭제여부 Y 수정  
	 * @param cnslSearch 상담관리 조회 조건
	 * @param bindingResult BindingResult
	 * @return RestResult<String>
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody RestResult<String> checkedDel(CnslSearch cnslSearch,BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userInfo){
		RestResult<String> result = new RestResult<String>();

		if(bindingResult.hasErrors()){
			result.setMessage("error");
			result.setSuccess(false);
		}else{
			result = payBackRcptService.deletePayBackRcpt(cnslSearch,userInfo);
		}
		return result;
	}
}
	
