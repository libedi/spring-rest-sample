/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.sysm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.sysm.model.SmsDocStrgMng;
import com.skplanet.impay.ccs.sysm.service.SmsDocStrgMngService;


/**
 * 시스템 관리
 * SMS 문서보관함 관리 Controller
 * @author Junehee, Jang
 */
@Controller
@RequestMapping("/sysm/smsDocStrgMng")
public class SmsDocStrgMngController {

	@Autowired
	private SmsDocStrgMngService smsDocStrgMngService;
	
	/**
	 * SMS 문서보관함 관리 화면
	 * @return String
	 */
	@RequestMapping(value = "/view", method=RequestMethod.POST)	
	public String payBackRcptView(){
		return "sysm/smsDocStrgMng";
	}
	/**
	 * SMS 문서보관함 조회
	 * @return Page<SmsDocStrgMng>
	 */
	@RequestMapping(value = "/getList", method=RequestMethod.POST)
	public @ResponseBody Page<SmsDocStrgMng> getList(){
	    return smsDocStrgMngService.selectSmsDocList();
	}
	/**
	 * 특정 SMS 문구 조회
	 * @param charStrgNo 문자 보관 번호
	 * @return SmsDocStrgMng
	 */
	@RequestMapping(value = "/getSmsWord/{charStrgNo}", method = RequestMethod.GET)
    public @ResponseBody SmsDocStrgMng getCpAnnoInfo(@PathVariable("charStrgNo") String charStrgNo) {
        return smsDocStrgMngService.selectSmsWord(charStrgNo);
	}
	/**
	 * SMS 문구 등록
	 * @param smsDocStrgMng SMS 문서 보관함 관리 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @param model Model
	 * @return RestResult<String>
	 */
	@RequestMapping(value="/addSmsWord", method=RequestMethod.POST)
	public @ResponseBody RestResult<String> addSmsWord(SmsDocStrgMng smsDocStrgMng,BindingResult bindingResult, Model model, @AuthenticationPrincipal CustomUserDetails userInfo){
		RestResult<String> result = new RestResult<String>();
		
		if(bindingResult.hasErrors()){
			model.addAttribute("smsDocStrgMng", smsDocStrgMng);
			result.setMessage("error");
			result.setSuccess(false);
		}else{
			result = smsDocStrgMngService.addSmsWord(smsDocStrgMng, userInfo);
		}
		return result;
	}
	/**
	 * SMS 문구 수정
	 * @param smsDocStrgMng SMS 문서 보관함 관리 정보
	 * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
	 * @param model Model
	 * @return RestResult<String>
	 */
	@RequestMapping(value="/updateSmsWord", method=RequestMethod.POST)
	public @ResponseBody RestResult<String> updateSmsWord(SmsDocStrgMng smsDocStrgMng,BindingResult bindingResult, Model model, @AuthenticationPrincipal CustomUserDetails userInfo){
		RestResult<String> result = new RestResult<String>();
		if(bindingResult.hasErrors()){
			model.addAttribute("smsDocStrgMng", smsDocStrgMng);
			result.setMessage("error");
			result.setSuccess(false);
		}else{
			result = smsDocStrgMngService.updateSmsWord(smsDocStrgMng, userInfo);
		}
		return result;
	}
	/**
	 * SMS 문구 삭제
	 * @param smsDocStrgMng SMS 문서 보관함 관리 정보
	 * @param bindingResult BindingResult
	 * @return RestResult<String>
	 */
	@RequestMapping(value = "/deleteSmsWord/{charStrgNo}", method = RequestMethod.GET)
    public @ResponseBody RestResult<String> deleteSmsWord(SmsDocStrgMng smsDocStrgMng ,BindingResult bindingResult){
		RestResult<String> result = new RestResult<String>();
		if(bindingResult.hasErrors()){
			result.setMessage("error");
			result.setSuccess(false);
		}else{
			result = smsDocStrgMngService.deleteSmsWord(smsDocStrgMng);
		}
		return result;
	}
}
