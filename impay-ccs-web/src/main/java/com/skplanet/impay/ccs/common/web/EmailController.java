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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.impay.ccs.common.model.Email;
import com.skplanet.impay.ccs.common.model.NotiTypeModel;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.EmailService;
import com.skplanet.impay.ccs.common.service.SendReqService;

/**
 * email controller
 * @author jisu park
 *
 */
@Controller
@RequestMapping("/email")
public class EmailController {
	
	private static Logger logger = LoggerFactory.getLogger(EmailController.class);
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SendReqService sendReqService;
	
	
	/**
	 * 샘플
	 * @return String
	 */
	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	public String sample() {
		return "email/emailTest";
	}
	
	/**
	 * 이메일 미리보기
	 * @param model Model
	 * @param email email 정보
	 * @return String
	 */
	@RequestMapping(value = "/preview", method = RequestMethod.GET)
	public String preview(Model model, Email email) {
		email.setContent(email.getContent().replaceAll("(\r\n|\n)", "<br />"));
		model.addAttribute("email", email);
		return "email/preview";
	}

	/**
	 * 통보유형목록조회
	 * @return List<NotiTypeModel>
	 */
	@RequestMapping(value = "/data/notitype", method = RequestMethod.GET)
	@ResponseBody
	public List<NotiTypeModel> getNotiType() {
		List<NotiTypeModel> notiType = sendReqService.getNotiTypeList(new NotiTypeModel());		
		return notiType;
	}
	
	/**
	 * 이메일 발송(DB insert)
	 * @param email email 정보
	 * @return RestResult<String>
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseBody
	public RestResult<String> send(Email email) {
		RestResult<String> result = new RestResult<>();
		emailService.sendEmailDB(email);
		result.setMessage("success");
		return result;
	}
}
