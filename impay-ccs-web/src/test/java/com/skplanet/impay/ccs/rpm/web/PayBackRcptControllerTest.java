///*
// * Copyright (c) 2013 SK planet.
// * All right reserved.
// *
// * This software is the confidential and proprietary information of SK planet.
// * You shall not disclose such Confidential Information and
// * shall use it only in accordance with the terms of the license agreement
// * you entered into with SK planet.
// */
//package com.skplanet.impay.ccs.rpm.web;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.boot.test.WebIntegrationTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.skplanet.impay.Application;
//import com.skplanet.impay.common.helper.AuthorizedControllerHelper;
//import com.skplanet.impay.ccs.framework.config.MyBatisConfig;
//import com.skplanet.impay.ccs.framework.config.RestTemplateConfig;
//import com.skplanet.impay.ccs.framework.config.SecurityConfig;
//import com.skplanet.impay.ccs.framework.config.WebApplicationConfig;
//import com.skplanet.impay.ccs.framework.config.WebMvcConfig;
//
///**
// * 환불 접수 Controller Test Class
// * @author Junehee, Jang
// *
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = {Application.class, MyBatisConfig.class, RestTemplateConfig.class, SecurityConfig.class, WebApplicationConfig.class, WebMvcConfig.class})
//@WebIntegrationTest
//@ActiveProfiles("local")
//public class PayBackRcptControllerTest {
//	
//	private MockMvc mockMvc;
//	private MockHttpSession session;
//			
//	@Autowired
//	private WebApplicationContext wac;
//	
//	@Before
//	public void setUp() throws Exception{
//		this.mockMvc = AuthorizedControllerHelper.getSecurityAppliedMockMvc(wac);
//		// Mock Request
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		request.setRemoteAddr(MockHttpServletRequest.DEFAULT_REMOTE_ADDR);
//		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//		session = AuthorizedControllerHelper.buildSecuritySession(wac, "test");
//	}
//	
//	/**
//	 * 환불접수 화면 테스트
//	 * @throws Exception
//	 */
//	@Test
//	public void testPayBackRcptView() throws Exception{
//		this.mockMvc.perform(post("/payBackRcpt/view").accept(MediaType.TEXT_HTML).characterEncoding("utf-8").session(session))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(model().attributeExists("notiList"));
//	}
//	/**
//	 * 환불접수 조회 테스트
//	 * @throws Exception
//	 */
//	@Test
//	public void testSearch() throws Exception{
//		this.mockMvc.perform(post("/payBackRcpt/search").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8").session(session)
//				.param("pybkRcptNo", "46")
//				.param("pageParam.pageIndex", "1")
//				.param("pageParam.rowCount", "5")
//				)
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.payBackRcptList").exists())
//				.andExpect(jsonPath("$.payBackRcptList.content").isArray());
//	}
//}
