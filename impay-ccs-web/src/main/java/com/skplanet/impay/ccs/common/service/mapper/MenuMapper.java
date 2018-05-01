/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.service.mapper;

import java.util.List;

import com.skplanet.impay.ccs.common.model.MenuAuthGrpModel;
import com.skplanet.impay.ccs.common.model.MenuModel;

/**
 * @author PP58701
 *
 */
public interface MenuMapper {
	/**
	 * 전체 메뉴 목록 조회
	 */
	List<MenuModel> selectMenuTotList(); 
	/**
	 * CCS 메뉴 목록 조회
	 */
	List<MenuModel> selectMenuList(); 
	/**
	 * 메뉴 경로 조회
	 */
	MenuModel selectMenuPath(MenuModel model);
	/**
	 * CCS 사용자 메뉴 목록 조회
	 * 
	 * @return CCS 메뉴 목록
	 */
	List<MenuModel> selectMenuListByUserId(MenuModel model); 
	/**
	 * 메뉴 ID 조회
	 * 
	 * @param model 조회 조건
	 * @return 메뉴 ID
	 */
	MenuModel selectMenuId(MenuModel model);
	/**
	 * 메뉴 권한 그룹 조회
	 */
	List<MenuAuthGrpModel> selectMenuAuthGrp(); 
}
