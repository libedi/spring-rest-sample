/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.api;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.skplanet.impay.ccs.common.model.Code;
import com.skplanet.impay.ccs.common.service.CodeService;

/**
 * 공통코드 조회 api
 * @author jisu park
 *
 */
@RestController
public class CodeController {
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CodeController.class);
	
	@Autowired
	private CodeService codeService;
	
	/**
	 * 공통코드조회
	 * @param locale
	 * @param lv - 레벨 (ml: 중분류, sl: 소분류)
	 * @param uprCd - 해당레벨코드의 상위코드
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/api/code/{lv}/{uprCd}", method = RequestMethod.GET)
	public List<Code> getMCodes(Locale locale, @PathVariable("lv") String lv, @PathVariable("uprCd") String uprCd, Model model) {
		List<Code> list = new ArrayList<>();
		if("ml".equals(lv)){
			list = codeService.selectCodeByUprCd(uprCd);
		} else if ("sl".equals(lv)){
			list = codeService.selectSCodeByUprCd(uprCd);
		}
		
		return list;
	}
}

