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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.impay.ccs.common.constants.ComLCd;
import com.skplanet.impay.ccs.common.model.Code;
import com.skplanet.impay.ccs.common.model.CpList;
import com.skplanet.impay.ccs.common.model.CpSearch;
import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.service.CodeService;
import com.skplanet.impay.ccs.common.service.CpSearchService;

/**
 * 가맹점 조회 Controller
 * @author Sangjun, Park
 *
 */
@Controller
@RequestMapping("/common/cp")
public class CpSearchController {
	@Autowired 
	private CpSearchService cpSearchService;
	
	@Autowired
	private CodeService codeService;
	
	/**
	 * 가맹점 조회 폼
	 * @param model Model
	 * @return List<Code>
	 */
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public @ResponseBody List<Code> list(Model model) {
		List<Code> statCd = codeService.selectSCodeByUprCd(ComLCd.CONTENT_PROVIDER_STATUS.value()); //가맹점상태
		model.addAttribute("statCdCount" , statCd.size());
		return statCd;
	}
	
	/**
	 * 가맹점 조회
	 * @param cpSearch 가맹점 조회 정보
	 * @return Page<CpList>
	 */
	@RequestMapping(value = "/search" , method = RequestMethod.POST)
	public @ResponseBody Page<CpList> search(CpSearch cpSearch) {
		return cpSearchService.selectCpList(cpSearch);
	}
}
