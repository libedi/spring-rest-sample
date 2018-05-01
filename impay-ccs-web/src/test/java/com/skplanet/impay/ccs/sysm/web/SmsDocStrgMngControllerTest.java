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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
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

import com.skplanet.impay.Application;
import com.skplanet.impay.ccs.common.service.MenuService;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;




@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@WebIntegrationTest
@ActiveProfiles("local")
public class SmsDocStrgMngControllerTest {
	final String USER_SEQ = "1";
	final String USER_ID = "test";
	final String USER_PW = "1234";
	final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
	
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private MenuService menuService;
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).alwaysDo(MockMvcResultHandlers.print()).alwaysExpect(status().isOk()).build();
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
	 * SMS 문서보관함 관리 화면 테스트
	 * @throws Exception
	 */
	@Test
	public void testPayBackRcptView() throws Exception{
		this.mockMvc.perform(post("/sysm/smsDocStrgMng/view").accept(MediaType.TEXT_HTML).characterEncoding("utf-8"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("/WEB-INF/jsp/sysm/smsDocStrgMng.jsp"));
	}
	/**
	 * SMS 문서보관함 조회
	 * @throws Exception
	 */
	@Test
	public void testGetList() throws Exception{
		this.mockMvc.perform(post("/sysm/smsDocStrgMng/getList").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.total").exists())
				.andExpect(jsonPath("$.content").isArray());
	}
	/**
	 * SMS 문서보관함 조회
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testSelectSmsWord() throws Exception{
		this.mockMvc.perform(get("/sysm/smsDocStrgMng/getSmsWord/1").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("charStrgNo", "1")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8));
	}
	
	@Test
	@Ignore
	public void testAddSmsWord() throws Exception{
		this.mockMvc.perform(get("/sysm/smsDocStrgMng/addSmsWord").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("charStrgNo", "74")
				.param("charCtnt", "[SK Planet IMPay] division 테스트 313123 dddd ☎ 1566")
				.param("regDt", "2016-03-11 오전 9:58:03")
				.param("regr", "홍길동")
				
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.result").exists());
	}
	
	
	
	
	
	
	
	
	
}
