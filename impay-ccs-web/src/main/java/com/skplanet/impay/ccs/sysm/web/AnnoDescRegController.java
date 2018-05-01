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
import java.util.List;

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
import com.skplanet.impay.ccs.common.model.Code;
import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.CodeService;
import com.skplanet.impay.ccs.common.util.DateUtil;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.sysm.model.AnnoDescReg;
import com.skplanet.impay.ccs.sysm.model.AnnoDescRegSearch;
import com.skplanet.impay.ccs.sysm.service.AnnoDescRegService;

/**
 * 시스템 관리
 * 공지사항등록 Controller
 * @author Junehee, Jang
 *
 */
@Controller
@RequestMapping("/sysm/annoDescRegMng")
public class AnnoDescRegController {

	@Autowired
	private CodeService codeService;
	
	@Autowired
	private AnnoDescRegService annoDescRegSerivce;
	
	/**
	 * 공지사항 관리 화면
	 * @param model Model
	 * @param userInfo 사용자 정보
	 * @return String
	 */
	@RequestMapping(value = "/view", method=RequestMethod.POST)	
	public String payBackRcptView(Model model, @AuthenticationPrincipal CustomUserDetails userInfo){
		String currDate = DateUtil.getCurrentDate();
		currDate = currDate.substring(0,4)+"."+currDate.substring(4,6)+"."+currDate.substring(6,8);
		List<Code> bordClfCd = codeService.selectCodeByUprCd(ComMCd.CALL_CENTER_BORD.value());
		model.addAttribute("bordClfCd", bordClfCd);
		model.addAttribute("currDate", currDate);	// 현재 날짜
		model.addAttribute("userNm", userInfo.getUserNm()); // 유져 이름
		return "/sysm/annoDescReg";
	}
	/**
     * 공지사항 조회
     * @param notiSearch 고객센터 공지사항 조회 조건
     * @return Page<AnnoDescReg> 
     */
    @RequestMapping(value = "/search", method=RequestMethod.POST)
    public @ResponseBody Page<AnnoDescReg> getAnnoDesc(AnnoDescRegSearch notiSearch){
        return annoDescRegSerivce.getNotiBoardList(notiSearch);
    }
	/**
     * 공지사항 상세 내역 조회
     * @param clctBordSeq 고객센터 게시판 순서
     * @return AnnoDescReg
     */
    @RequestMapping(value = "/annoDescInfo/{clctBordSeq}", method=RequestMethod.GET)
    public @ResponseBody AnnoDescReg getAnnoDescInfo(@PathVariable("clctBordSeq") long clctBordSeq) {
        return annoDescRegSerivce.getAnnoDescInfo(clctBordSeq);
    }
    /**
     * 공지사항 등록
     * @param annoDescReg 고객센터 공지사항 정보
     * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
     * @param model Model
     * @return RestResult<String>
     */
    @RequestMapping(value="/add", method=RequestMethod.POST)
	public @ResponseBody RestResult<String> addAnnoDescReg(AnnoDescReg annoDescReg,BindingResult bindingResult, Model model, @AuthenticationPrincipal CustomUserDetails userInfo){
		RestResult<String> result = new RestResult<String>();
		
		if(bindingResult.hasErrors()){
			model.addAttribute("annoDescReg", annoDescReg);
			result.setMessage("error");
			result.setSuccess(false);
		}else{
			result = annoDescRegSerivce.addAnnoDescReg(annoDescReg, userInfo);
		}
		return result;
	}
    /**
     * 공지사항 수정
     * @param annoDescReg 고객센터 공지사항 정보
     * @param bindingResult BindingResult
	 * @param userInfo 사용자 정보
     * @param model Model
     * @return RestResult<String>
     */
    @RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody RestResult<String> updateAnnoDescReg(AnnoDescReg annoDescReg,BindingResult bindingResult, Model model, @AuthenticationPrincipal CustomUserDetails userInfo){
		RestResult<String> result = new RestResult<String>();
		
		if(bindingResult.hasErrors()){
			model.addAttribute("annoDescReg", annoDescReg);
			result.setMessage("error");
			result.setSuccess(false);
		}else{
			result = annoDescRegSerivce.updateAnnoDescReg(annoDescReg, userInfo);
		}
		return result;
	}
}
