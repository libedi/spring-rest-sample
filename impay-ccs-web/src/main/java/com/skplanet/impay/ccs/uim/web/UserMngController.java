/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.uim.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.impay.ccs.common.log.idms.service.IdmsCustomerLogService;
import com.skplanet.impay.ccs.common.model.CntcInfo;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.MessageByLocaleServiceImpl;
import com.skplanet.impay.ccs.framework.model.ValidationMarker.Update;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.uim.model.UserMng;
import com.skplanet.impay.ccs.uim.model.UserMngPwd;
import com.skplanet.impay.ccs.uim.service.UserMngService;

/**
 * 사용자 정보 관리 Controller
 * @author Sangjun, Park
 *
 */
@Controller
@RequestMapping("/userMng")
public class UserMngController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserMngController.class);
	
	@Autowired
	private UserMngService userMngService;
	
	@Autowired
	private MessageByLocaleServiceImpl message;
	
	@Autowired
	private IdmsCustomerLogService idmsCustomerLogService;
	
	/**
	 * 사용자정보 관리 화면
	 * @param model Model 
	 * @param userInfo 사용자 정보
	 * @return String
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public String getUserMngInfo(Model model, @AuthenticationPrincipal CustomUserDetails userInfo){
		// 사용자 정보 LOG 수집
		idmsCustomerLogService.printIdmsLog(userInfo.getUserId(), userInfo.getUserNm(), "CCS0035", "10001", 1, userInfo);
		model.addAttribute("userMng", this.userMngService.getUserMngInfo(userInfo));
		return "uim/userMng";
	}
	
	/**
	 * 사용자 정보 수정
	 * @param userMng 사용자 관리 정보
	 * @param cntcInfo 사용자 - 연락처 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @return RestResult<UserMng>
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody RestResult<UserMng> updateUserMngInfo(@Validated(Update.class) UserMng userMng, CntcInfo cntcInfo, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userInfo){
		
		RestResult<UserMng> result = new RestResult<>();
		if(bindingResult.hasErrors()){
			logger.error("updateUserMngInfo() - {} :: {}", bindingResult.getFieldError().getDefaultMessage(), bindingResult.getFieldError().getField());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
		}else{
			// 사용자 정보 LOG 수집
			idmsCustomerLogService.printIdmsLog(userInfo.getUserId(), userInfo.getUserNm(), "CCS0036", "10001", 1, userInfo);
			userMng.setCntcInfo(cntcInfo);
			// 결과값 반환
			result.setResult(this.userMngService.updateUserMngInfo(userMng, userInfo));
			result.setSuccess(true);
			result.setMessage("success");
		}
		return result;
	}
	
	/**
	 * 사용자 비밀번호 수정
	 * @param userMngPwd 비밀번호 관리 정보
	 * @param bindingResult BindingResult
	 * @return RestResult<Integer>
	 */
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	public @ResponseBody RestResult<Integer> updatePassword(@Validated(Update.class) UserMngPwd userMngPwd, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userInfo){
		RestResult<Integer> result = new RestResult<>();
		if(bindingResult.hasErrors()){
			logger.error("updatePassword() - {} :: {}", bindingResult.getFieldError().getDefaultMessage(), bindingResult.getFieldError().getField());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
		} else {
			// 사용자 정보 LOG 수집
			idmsCustomerLogService.printIdmsLog(userInfo.getUserId(), userInfo.getUserNm(), "CCS0036", "10001", 1, userInfo);
			result = this.userMngService.updatePwd(userMngPwd);
		}
		return result;
	}
}
