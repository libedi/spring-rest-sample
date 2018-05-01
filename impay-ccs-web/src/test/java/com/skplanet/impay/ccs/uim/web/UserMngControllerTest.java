/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.uim.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import com.skplanet.impay.Application;
import com.skplanet.impay.ccs.common.service.MenuService;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;

/**
 * 사용자 관리 Controller Test Class
 * @author Sangjun, Park
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@WebIntegrationTest
@ActiveProfiles("dev")
public class UserMngControllerTest {
	private final String USER_SEQ = "1";
	private final String USER_ID = "test";
	private final String USER_PW = "1234";
	private final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
	
	private MockMvc mockMvc;
	
	@Autowired
	private XssEscapeServletFilter xssFilter;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private MenuService menuService;
	
	@Before
	public void setUp() throws Exception{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(xssFilter).alwaysDo(MockMvcResultHandlers.print()).alwaysExpect(status().isOk()).build();
		CustomUserDetails user = new CustomUserDetails();
		user.setUserSeq(USER_SEQ);
		user.setUserId(USER_ID);
		user.setPassword(USER_PW);
		user.setMstYn("Y");
		menuService.getMemu(user);
		TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);
		SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
	}
	
	/**
	 * 사용자정보 관리 화면 test
	 * @throws Exception
	 */
	@Test
	public void testGetUserInfo() throws Exception{
		this.mockMvc.perform(post("/userMng/view").accept(MediaType.TEXT_HTML).characterEncoding("utf-8"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("/WEB-INF/jsp/uim/userMng.jsp"))
			.andExpect(model().attributeExists("userMng"));
	}
	
	/**
	 * 사용자 정보 수정 test
	 * @throws Exception
	 */
	@Test
	public void testUpdateUserMngInfo() throws Exception{
		this.mockMvc.perform(post("/userMng/update").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("mphnNo", "010-1234-5678")
				.param("email", "test@impay.com")
				.param("deptNm", "impay 개발팀")
				.param("userSeq", "002663")
				.param("cntcSeq", "6575")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.success").value(true));
	}
	
	/**
	 * 사용자 비밀번호 수정 test
	 * @throws Exception
	 */
	@Test
	public void testUpdatePassword() throws Exception{
		this.mockMvc.perform(post("/userMng/updatePwd").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("pwd", "1234")
				.param("newPwd", "test0102!")
				.param("newPwd2", "test0102!")
				.param("userSeq", "1")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.success").value(true));
	}
	
}
