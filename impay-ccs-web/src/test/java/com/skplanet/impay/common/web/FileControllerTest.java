/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.common.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.impay.Application;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;

/**
 * 파일 Controller Test Class
 * @author Sangjun, Park
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class FileControllerTest {
	final String USER_ID = "test";
	final String USER_PW = "1234";
	final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
	
	private MockMvc mockMvc;
			
	@Autowired
	private WebApplicationContext wac;
	
	@Before
	public void setUp() throws Exception{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).alwaysDo(MockMvcResultHandlers.print()).alwaysExpect(status().isOk()).build();
		CustomUserDetails user = new CustomUserDetails();
		user.setUserId(USER_ID);
		user.setPassword(USER_PW);
		TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);
		SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
	}
	
	@Test
	public void testCreatePdf() throws Exception{
		this.mockMvc.perform(post("/file/createPdf").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("viewFileName", "test.ftl")
				.param("downloadFileName", "테스트PDF3")
				.param("htmlDataMap.testdata", "테스트데이터")
				.param("useHtml", "true")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.success").value(true));
				
	}
}
