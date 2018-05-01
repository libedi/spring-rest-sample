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

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 공통 Controller
 * @author Sangjun, Park
 *
 */
@Controller
@RequestMapping("/common")
public class CommonController {
	private final static Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	/**
	 * 
	 * @param locale Locale
	 * @param model Model
	 * @param url url
	 * @return String
	 */
	@RequestMapping(value = "/openLayer", method = RequestMethod.GET)
	public String openLayer(Locale locale, Model model, @RequestParam(value="url") String url) {
		return url;
	}
}
