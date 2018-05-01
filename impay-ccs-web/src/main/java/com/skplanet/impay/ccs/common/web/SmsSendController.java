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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.impay.ccs.common.model.NotiTypeModel;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.model.SmsSend;
import com.skplanet.impay.ccs.common.service.SendReqService;
import com.skplanet.impay.ccs.common.service.SmsSendService;

/**
 * SMS 발송 공통 Controller
 * @author Sangjun, Park
 *
 */
@Controller
@RequestMapping("/smsSend")
public class SmsSendController {
	
	private static Logger logger = LoggerFactory.getLogger(EmailController.class);
	
	@Autowired
	private SendReqService sendReqService;
	
	@Autowired
	private SmsSendService smsSendService;
	
	/**
	 * 통보유형목록조회
	 * @return List<NotiTypeModel>
	 */
	@RequestMapping(value = "/data/notitype", method = RequestMethod.GET)
	public @ResponseBody List<NotiTypeModel> getNotiType() {
		List<NotiTypeModel> notiType = sendReqService.getNotiTypeList(new NotiTypeModel());		
		return notiType;
	}
	
	/**
	 * SMS 발송
	 * @param smsSend SMS 발송 정보
	 * @return RestResult<SmsSend>
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public @ResponseBody RestResult<SmsSend> send(SmsSend smsSend){
		RestResult<SmsSend> result = new RestResult<SmsSend>();
		String message = "success";
		if(StringUtils.isEmpty(smsSendService.sendSms(smsSend))){
			message = "fail";
		}
		result.setMessage(message);
		return result;
	}
	
}
