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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import com.skplanet.impay.ccs.common.constants.ComMCd;
import com.skplanet.impay.ccs.common.log.idms.service.IdmsCustomerLogService;
import com.skplanet.impay.ccs.common.model.Code;
import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.CodeService;
import com.skplanet.impay.ccs.common.util.DateUtil;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.rpm.model.AddPayWhiteList;
import com.skplanet.impay.ccs.rpm.model.RpmSearch;
import com.skplanet.impay.ccs.rpm.service.AddPayWhiteListMngService;
/**
 * CCS 접수/처리현황관리 
 * - 가산금 W/L 관리 Controller
 * @author Junehee, Jang
 *
 */
@Controller
@RequestMapping("/rpm/addPayWhiteListMng")
public class AddPayWhiteListMngController {
	
	
	private final static Logger logger = LoggerFactory.getLogger(AddPayWhiteListMngController.class);
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private AddPayWhiteListMngService addPayWhiteListMngService;
	
	@Autowired
	private IdmsCustomerLogService idmsCustomerLogService;

	/**
	 * 가산금 W/L 관리 화면
	 * @param model Model
	 * @return String
	 */
	@RequestMapping(value = "/view", method=RequestMethod.POST)	
	public String npayAddWhitrListMngView(Model model, @AuthenticationPrincipal CustomUserDetails userInfo){
		String currDate = DateUtil.getCurrentDate();
		currDate = currDate.substring(0,4)+"."+currDate.substring(4,6)+"."+currDate.substring(6,8);
		model.addAttribute("currDate", currDate);	
		// 등록 구분
		List<Code> regClfCd = codeService.selectCodeByUprCd(ComMCd.NPAYADD_WHITELIST_CLASSFICATION.value());
		model.addAttribute("regClfCd", regClfCd);
		
		model.addAttribute("userNm",userInfo.getUserNm());
		return "rpm/addPayWhiteListMng";
	}
	/**
	 * 가산금 W/L 조회
	 * @param rpmSearch 접수/처리 조회 조건
	 * @return Page<AddPayWhiteList>
	 */
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public @ResponseBody Page<AddPayWhiteList> search(RpmSearch rpmSearch, @AuthenticationPrincipal CustomUserDetails userInfo) {
		
		Page<AddPayWhiteList> result = addPayWhiteListMngService.getAddPayWhiteList(rpmSearch);
		// 고객 정보 LOG 수집
		if(result.getTotal() > 0){
			idmsCustomerLogService.printIdmsLog(result.getContent().get(0).getWlRegNo(), "X", "CCS0001", "10001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		return result;
	}
	/**
	 * 가산금 W/L 상세 정보
	 * @param wlRegNo WL(White List)등록 번호
	 * @param userInfo 사용자 정보
	 * @return AddPayWhiteList
	 */
	@RequestMapping(value = "/info/{wlRegNo}", method=RequestMethod.GET)
	public @ResponseBody AddPayWhiteList getAddPayWhiteListInfo(@PathVariable("wlRegNo") String wlRegNo, @AuthenticationPrincipal CustomUserDetails userInfo) {
		AddPayWhiteList result = addPayWhiteListMngService.getAddPayWhiteListInfo(wlRegNo);
		// 고객 정보 LOG 수집
		if(result!= null){
			idmsCustomerLogService.printIdmsLog(result.getWlRegNo(), "X", "CCS0002", "10001", 1, userInfo);
		}
	    return result; 
	}
	/**
	 * 가산금 W/L 등록
	 * @param addPayWhiteList 미납가산금 W/L 관리 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @param model Model
	 * @return RestResult<String>
	 */
	@RequestMapping(value="/saveRegRsn", method=RequestMethod.POST)
	public @ResponseBody RestResult<String> saveRegRsn(AddPayWhiteList addPayWhiteList,BindingResult bindingResult, Model model,@AuthenticationPrincipal CustomUserDetails userInfo){
		RestResult<String> result = new RestResult<String>();
		if(bindingResult.hasErrors()){
			model.addAttribute("addPayWhiteList", addPayWhiteList);
			result.setMessage("error");
			result.setSuccess(false);
		}else{
			result = addPayWhiteListMngService.addPayAddWhiteList(addPayWhiteList,userInfo);
			// 고객 정보 LOG 수집
			idmsCustomerLogService.printIdmsLog(addPayWhiteList.getPayMphnId(), "X", "CCS0003", "10003", 1, userInfo);
		}
		return result;
	}
	/**
	 * 가산금 W/L 삭제 사유 등록
	 * @param addPayWhiteList 미납가산금 W/L 관리 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @param model Model
	 * @return RestResult<String>
	 */
	@RequestMapping(value="/saveDelRsn", method=RequestMethod.POST)
	public @ResponseBody RestResult<String> saveDelRsn(AddPayWhiteList addPayWhiteList,BindingResult bindingResult, Model model, @AuthenticationPrincipal CustomUserDetails userInfo){
		RestResult<String> result = new RestResult<String>();
		
		if(bindingResult.hasErrors()){
			model.addAttribute("addPayWhiteList", addPayWhiteList);
			result.setMessage("error");
			result.setSuccess(false);
		}else{
			result = addPayWhiteListMngService.updateAddPayDelWhiteList(addPayWhiteList, userInfo);
		}
		return result;
	}
}
