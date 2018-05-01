/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.framework.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.skplanet.impay.ccs.common.service.MessageByLocaleServiceImpl;
import com.skplanet.impay.ccs.common.util.StringUtil;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.common.model.MenuModel;
import com.skplanet.impay.ccs.common.service.MenuService;

/**
 * @author PP58701
 *
 */
public class MenuInterceptor implements HandlerInterceptor {

	@Autowired
	private MenuService service;
	
	@Value("${x.requested.with}") 
	private String requestWith;
	
	@Value("${xml.http.request}") 
	private String xmlHttpRequest;
	
	@Autowired
	private MessageByLocaleServiceImpl message;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (xmlHttpRequest.equals(StringUtil.defaultString(request.getHeader(requestWith), StringUtil.EMPTY))) {
			return true;
		}
		CustomUserDetails userInfo = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
		List<MenuModel> menu1st = userInfo.getMenu1st();
		List<MenuModel> menu2nd = userInfo.getMenu2nd();
		List<MenuModel> menu3rd = userInfo.getMenu3rd();
		List<MenuModel> menuList = userInfo.getMenuList();
		List<String> menuIdList = userInfo.getMenuIdList();
		request.setAttribute("menu1st", menu1st);
		request.setAttribute("menu2nd", menu2nd);
		request.setAttribute("menu3rd", menu3rd);

		String requestURI = StringUtil.replace(request.getRequestURI(), request.getContextPath(), StringUtil.EMPTY);
		
		if (!"/".equals(requestURI)) {
			
			String mnuId = request.getParameter("mnuId");
			String uprMnuId = request.getParameter("uprMnuId");
			
			if (StringUtils.isEmpty(mnuId) && StringUtils.isEmpty(uprMnuId)) {
				MenuModel menu = service.getMenuId(requestURI);
				if (menu != null) {
					mnuId = menu.getUprMnuId();
					uprMnuId = menu.getMnuId();
				} else {
					return true;
				}
			}
			
			if (!menuIdList.contains(uprMnuId)) {
				response.sendRedirect(request.getContextPath() + "/login/form?errorMsg=ACCESS_DENIED");
			} else {
				request.setAttribute("mnuId", mnuId);
				request.setAttribute("uprMnuId", uprMnuId);
				String path = "";
				for(MenuModel menu : menuList){
					if(uprMnuId.equals(menu.getMnuId())){
						path = menu.getPath();
						break;
					}
				}
				path = StringUtil.isEmpty(path) ? path : StringUtil.replace(path, "?", message.getMessage("common.label.home"));
				request.setAttribute("menuPath", path);
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
}
