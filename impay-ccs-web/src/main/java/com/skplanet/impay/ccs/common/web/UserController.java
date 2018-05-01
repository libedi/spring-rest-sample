/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.impay.ccs.common.model.PayMphnInfo;
import com.skplanet.impay.ccs.common.model.PayrInfo;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.UserService;
import com.skplanet.impay.ccs.framework.model.ValidationMarker.Retrieve;

/**
 * 사용자 정보 공통 Controller
 * @author Sangjun, Park
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 결제폰 정보 조회
	 * @param payMphnInfo 결제폰 정보
	 * @param bindingResult BindingResult
	 * @return RestResult<PayMphnInfo>
	 */
	@RequestMapping(value = "/search/payMphnInfo", method = RequestMethod.POST)
	public @ResponseBody RestResult<PayMphnInfo> getPayMphnInfo(@Validated(Retrieve.class) PayMphnInfo payMphnInfo, BindingResult bindingResult){
		RestResult<PayMphnInfo> result = new RestResult<>();
		if(bindingResult.hasFieldErrors()){
			logger.error(bindingResult.getFieldError().getDefaultMessage());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage("fail");
		} else {
			PayMphnInfo retInfo = this.userService.getPayMphnInfo(payMphnInfo);
			if(retInfo != null){
				result.setSuccess(true);
				result.setMessage("success");
				result.setResult(retInfo);
				logger.debug("Search Success.");
			} else {
				result.setSuccess(true);
				result.setMessage("No PayMphnInfo");
				logger.debug("No PayMphnInfo.");
			}
		}
		return result;
	}
	
	/**
	 * 이용자 정보 조회
	 * @param payrSeq 이용자 순번
	 * @return RestResult<PayrInfo>
	 */
	@RequestMapping(value = "/search/payrInfo", method = RequestMethod.POST)
	public @ResponseBody RestResult<PayrInfo> getPayMphnInfo(@RequestParam("payrSeq") String payrSeq){
		RestResult<PayrInfo> result = new RestResult<>();
		PayrInfo returnModel = this.userService.getPayrInfo(payrSeq);
		if(returnModel != null){
			result.setSuccess(true);
			result.setMessage("success");
			result.setResult(returnModel);
			logger.debug("Search Success.");
		} else {
			result.setSuccess(false);
			result.setMessage("No PayrInfo");
			logger.debug("No PayrInfo.");
		}
		return result;
	}
}
