/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.rpm.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
 * 이관접수 Controller test class
 * @author Sangjun, Park
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@WebIntegrationTest
@ActiveProfiles("dev")
public class TransferRcptControllerTest {
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
	 * 이관 접수 화면 테스트
	 * @throws Exception
	 */
	@Test
	public void testTransferRcptView() throws Exception{
		this.mockMvc.perform(post("/rpm/trxRcpt/view").accept(MediaType.TEXT_HTML).characterEncoding("utf-8"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/jsp/rpm/transferRcpt.jsp"))
				.andExpect(model().attributeExists("adjEntp"))
				.andExpect(model().attributeExists("tjur"))
				.andExpect(model().attributeExists("today"));
	}
	
	/**
	 * 이관 접수 리스트 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetTransferRcptList() throws Exception{
		this.mockMvc.perform(get("/rpm/trxRcpt/search/tjurRcptList").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("stDate", "2016.01.01")
				.param("endDate", "2016.03.17")
				.param("tjurClfFlg", "C")
				.param("procStat", "N")
				.param("pgId", "")
				.param("searchPhone", "")
				.param("pageParam.pageIndex", "1")
				.param("pageParam.rowCount", "5")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.total").exists())
				.andExpect(jsonPath("$.content").isArray());
	}
	
	/**
	 * 이관 접수 리스트 총건수 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetTransferRcptListCount() throws Exception{
		this.mockMvc.perform(get("/rpm/trxRcpt/search/tjurRcptListCount").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("stDate", "2016.01.01")
				.param("endDate", "2016.03.17")
				.param("tjurClfFlg", "C")
				.param("procStat", "N")
				.param("pgId", "")
				.param("searchPhone", "")
				.param("pageParam.pageIndex", "1")
				.param("pageParam.rowCount", "5")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$").exists());
	}
}
