/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.impay.ccs.common.model.MenuAuthGrpModel;
import com.skplanet.impay.ccs.common.model.MenuModel;
import com.skplanet.impay.ccs.common.model.MenuTotModel;
import com.skplanet.impay.ccs.common.service.mapper.MenuMapper;
import com.skplanet.impay.ccs.common.util.StringUtil;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;

/**
 * 메뉴 관리 Service
 * 
 * @author 최재용
 *
 */
@Service
@Transactional
public class MenuService {

	@Autowired 
	private MenuMapper menuMapper;
	
	@Autowired
	private MessageByLocaleServiceImpl message;
	
	/**
	 * 전체 메뉴 조회
	 * 
	 * @return 메뉴 목록
	 */
	public List<MenuModel> getMenuList() {
		return menuMapper.selectMenuList();
	}
	
	/**
	 * 사용자 메뉴 조회
	 * 
	 * @return 메뉴 목록
	 */
	public List<MenuModel> getMenuListByUserId(MenuModel model) {
		return menuMapper.selectMenuListByUserId(model);
	}
	
	/**
	 * 메뉴 네비게이션 조회
	 * 
	 * @param uprMnuId 상위메뉴
	 * @return 메뉴 네비게이션
	 */
	public String getMenuPath(String uprMnuId) {
		MenuModel model = new MenuModel();
		model.setUprMnuId(uprMnuId);
		model = menuMapper.selectMenuPath(model);
		String path = model.getPath();
		return StringUtil.isEmpty(path) ? path : StringUtil.replace(path, "?", message.getMessage("sum.label.home"));
	}
	
	/**
	 * 메뉴 정보 조회
	 * 
	 * @param requestURI 메뉴경로
	 * @return 메뉴 정보
	 */
	public MenuModel getMenuId(String requestURI) {
		MenuModel model = new MenuModel();
		model.setMnuUrl(requestURI);
		return menuMapper.selectMenuId(model);
	
	}
	
	/**
	 * 메뉴 그룹 조회
	 * 
	 * @return 메뉴 그룹 목록
	 */
	public List<MenuAuthGrpModel> findMenuAuthGrp() {
		return menuMapper.selectMenuAuthGrp();
	}
	

	/**
	 * 전체 메뉴 조회
	 * 
	 * <p>사이트 분류별 1/2/3depth</p>
	 * 
	 * @param input 조회 조건
	 * @return 사이트 분류별 전체 메뉴
	 */
	public MenuTotModel getMenuTotList(MenuModel input) {
		MenuTotModel model = new MenuTotModel();
		List<MenuModel> bos_menu1st = new ArrayList<MenuModel>();
		List<MenuModel> bos_menu2nd = new ArrayList<MenuModel>();
		List<MenuModel> bos_menu3rd = new ArrayList<MenuModel>();
		
		List<MenuModel> ccs_menu1st = new ArrayList<MenuModel>();
		List<MenuModel> ccs_menu2nd = new ArrayList<MenuModel>();
		List<MenuModel> ccs_menu3rd = new ArrayList<MenuModel>();
		
		List<MenuModel> cms_menu1st = new ArrayList<MenuModel>();
		List<MenuModel> cms_menu2nd = new ArrayList<MenuModel>();
		List<MenuModel> cms_menu3rd = new ArrayList<MenuModel>();
		
		List<MenuModel> menuList = menuMapper.selectMenuTotList();
		
		for (MenuModel menu : menuList) {
			if ("1".equals(input.getSearchTp())) {
				if ("B".equals(menu.getSiteClfFlg())) {
					if ("1".equals(menu.getMnuLv()) ) {
						bos_menu1st.add(menu);
					} else if ("2".equals(menu.getMnuLv())) {
						bos_menu2nd.add(menu);
					} else if ("3".equals(menu.getMnuLv())) {
						bos_menu3rd.add(menu);
					}
				} else if ("C".equals(menu.getSiteClfFlg())) {
					if ("1".equals(menu.getMnuLv()) ) {
						ccs_menu1st.add(menu);
					} else if ("2".equals(menu.getMnuLv())) {
						ccs_menu2nd.add(menu);
					} else if ("3".equals(menu.getMnuLv())) {
						ccs_menu3rd.add(menu);
					}
				}
			}
			if ("M".equals(menu.getSiteClfFlg())) {
				if ("1".equals(menu.getMnuLv()) ) {
					cms_menu1st.add(menu);
				} else if ("2".equals(menu.getMnuLv())) {
					cms_menu2nd.add(menu);
				} else if ("3".equals(menu.getMnuLv())) {
					cms_menu3rd.add(menu);
				}
			}
		}
		
		if ("1".equals(input.getSearchTp())) {
			model.setBos_menu1st(bos_menu1st);
			model.setBos_menu2nd(bos_menu2nd);
			model.setBos_menu3rd(bos_menu3rd);
			
			model.setCcs_menu1st(ccs_menu1st);
			model.setCcs_menu2nd(ccs_menu2nd);
			model.setCcs_menu3rd(ccs_menu3rd);
		}
		
		model.setCms_menu1st(cms_menu1st);
		model.setCms_menu2nd(cms_menu2nd);
		model.setCms_menu3rd(cms_menu3rd);
		
		model.setMenuAuthGrp(findMenuAuthGrp());
		return model;
	}
	
	/**
	 * 사용자 메뉴 조회 
	 * @param userDetails
	 */
	public void getMemu(CustomUserDetails userDetails){
		MenuModel input = new MenuModel();
		input.setUserId(userDetails.getUserId());
		input.setMstYn(userDetails.getMstYn());
		
		List<MenuModel> menu1st = new ArrayList<MenuModel>();
		List<MenuModel> menu2nd = new ArrayList<MenuModel>();
		List<MenuModel> menu3rd = new ArrayList<MenuModel>();		
		List<MenuModel> menuList = menuMapper.selectMenuListByUserId(input);
		List<String> menuIdList = new ArrayList<String>();
		
		for (MenuModel menu : menuList) {
			if ("1".equals(menu.getMnuLv()) ) {
				menu1st.add(menu);
			} else if ("2".equals(menu.getMnuLv()) ) {
				menu2nd.add(menu);
			} else if ("3".equals(menu.getMnuLv()) ) {
				menu3rd.add(menu);
			}
			menuIdList.add(menu.getMnuId());
		}
		userDetails.setMenu1st(menu1st);
		userDetails.setMenu2nd(menu2nd);
		userDetails.setMenu3rd(menu3rd);
		userDetails.setMenuList(menuList);
		userDetails.setMenuIdList(menuIdList);
	}
}
