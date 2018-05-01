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
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.model.SendReqModel;
import com.skplanet.impay.ccs.common.service.SendReqService;

/**
 * Email, SMS 그룹/개별 발송 요청 Controller
 * @author Sangjun, Park
 *
 */
@Controller
@RequestMapping("/sendReq")
public class SendReqController {
	private static Logger logger = LoggerFactory.getLogger(SendReqController.class);
	
	@Autowired
	private SendReqService sendReqService;
	
	/**
	 * 발송 요청
	 * @param sendReqModel 발송 요청 정보
	 * @param bindingResult BindingResult
	 * @return RestResult<SendReqModel>
	 */
	@RequestMapping("/send")
	public @ResponseBody RestResult<SendReqModel> sendRequest(@Validated(SendReqModel.class) SendReqModel sendReqModel, BindingResult bindingResult){
		RestResult<SendReqModel> result = new RestResult<SendReqModel>();
		if(bindingResult.hasErrors()){
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage("error");
		} else {
			this.sendReqService.sendRequest(sendReqModel);
			result.setResult(sendReqModel);
			result.setSuccess(true);
			result.setMessage("success");
		}
		return result;
	}
}
