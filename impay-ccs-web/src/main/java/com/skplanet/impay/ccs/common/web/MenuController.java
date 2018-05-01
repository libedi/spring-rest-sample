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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.impay.ccs.common.model.MenuModel;
import com.skplanet.impay.ccs.common.model.MenuTotModel;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.MenuService;

/**
 * 메뉴 관리 Controller
 * 
 * @author 최재용
 *
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * 전체 메뉴 조회
	 * 
	 * <p>사이트 분류별 1/2/3depth</p>
	 * 
	 * @param input 조회 조건
	 * @return 사이트 분류별 전체 메뉴
	 */
	@RequestMapping("/findMenuTotList")
	@ResponseBody
	public RestResult<MenuTotModel> findMenuTotList(MenuModel input) {
		RestResult<MenuTotModel> result = new RestResult<MenuTotModel>();
		result.setSuccess(true);
		result.setResult(menuService.getMenuTotList(input));
		return result;
	}
}
