/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.csm.web;

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
 * 상담관리 Controller Test Class
 * @author Sangjun, Park
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@WebIntegrationTest
@ActiveProfiles("dev")
public class CnslMngControllerTest {
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
	 * 상담관리 화면 테스트
	 * @throws Exception
	 */
	@Test
	public void testCnslView() throws Exception{
		this.mockMvc.perform(post("/cnslMng/view").accept(MediaType.TEXT_HTML).characterEncoding("utf-8"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/jsp/csm/cnslMng.jsp"))
				.andExpect(model().attributeExists("notiList"))
				.andExpect(model().attributeExists("cnslClfCdList"))
				.andExpect(model().attributeExists("cnslTypCdList"))
				.andExpect(model().attributeExists("rcptMthdCdList"))
				.andExpect(model().attributeExists("evntTypCdList"))
				.andExpect(model().attributeExists("rmsBlkReliefCdList"))
				.andExpect(model().attributeExists("rmsRmRelsRsnCdList"))
				.andExpect(model().attributeExists("rmsFraudRelsRsnCdList"))
				.andExpect(model().attributeExists("todayMonth"))
				.andExpect(model().attributeExists("today"))
				.andExpect(model().attributeExists("prevYear"));
	}
	
	/**
	 * 고객정보 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetCustInfo() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/custInfo").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("custMphnNo", "01012345678")
				.param("payMphnId", "123456789")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8));
	}
	
	/**
	 * 거래완료 탭 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetTotalTradeCompleteList() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/totalTrdCmplList").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("strtDt", "2015.06.01")
				.param("endDt", "2015.06.01")
				.param("custMphnNo", "01012345678")
				.param("selectDate", "T")
				.param("pageParam.pageIndex", "1")
				.param("pageParam.rowCount", "5")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.cnslDetailList").exists());
	}
	
	/**
	 * 거래완료 리스트 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetTradeCompleteList() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/trdCmplList").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("strtDt", "2015.06.01")
				.param("endDt", "2015.06.01")
				.param("custMphnNo", "01012345678")
				.param("payMphnId", "123456789")
				.param("selectDate", "T")
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
	 * 거래완료 리스트 카운트 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetTradeCompleteListCount() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/trdCmplListCount").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("strtDt", "2015.06.01")
				.param("endDt", "2015.06.01")
				.param("custMphnNo", "01012345678")
				.param("payMphnId", "123456789")
				.param("selectDate", "T")
				.param("pageParam.pageIndex", "1")
				.param("pageParam.rowCount", "5")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$").exists());
	}
	
	/**
	 * 거래완료 리스트 카운트 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetCnslDetailList() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/cnslDtlList").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("strtDt", "2015.06.01")
				.param("endDt", "2015.06.01")
				.param("custMphnNo", "01012345678")
				.param("payMphnId", "123456789")
				.param("selectDate", "T")
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
	 * 거래완료 단건 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetTradeComplete() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/trdCmpl").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.param("trdNos", "20150731105739049322")
				.param("pageParam.pageIndex", "1")
				.param("pageParam.rowCount", "5")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8));
	}
	
	/**
	 * 거래명세서 발송 테스트
	 * @throws Exception
	 */
	@Test
	public void testCreatePdf() throws Exception{
		this.mockMvc.perform(post("/cnslMng/send/receipt").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("trdNos", "20150601000358035330")
				.param("trdNos", "20150601000357035329")
				.param("trdNos", "20150601000401046854")
				.param("trdNos", "20150601000359028396")
				.param("pageParam.pageIndex", "1")
				.param("pageParam.rowCount", "5")
				.param("custMphnNo", "010-1234-5678")
				.param("email", "test@customer.com")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.success").value(true));
	}
	
	/**
	 * 상담내용, 처리내용 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetCnslCtnt() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/cnslCtnt").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("rcptNo", "201601050025")
				.param("pageParam.pageIndex", "1")
				.param("pageParam.rowCount", "5")
				.param("chgListYn", "Y")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$").exists());
	}
	
	/**
	 * 상담내용 저장 테스트
	 * @throws Exception
	 */
	@Test
	public void testSaveCnslCtnt() throws Exception{
		this.mockMvc.perform(post("/cnslMng/save/cnslCtnt").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.param("cnslTypCd", "TKTK01")
				.param("cnslClfUprCd", "THCM21")
				.param("rcptMthdCd", "RKRK01")
				.param("cnslEvntCd", "TKEV01")
				.param("cnslCtnt", "테스트1<script>alert(\"1\");</script><br/>테스트2")
				.param("mphnNo", "01012345678")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.success").value(true));
	}
	
	/**
	 * 상담내용, 처리내용 수정 테스트
	 * @throws Exception
	 */
	@Test
	public void testUpdateCnslCtnt() throws Exception{
		this.mockMvc.perform(post("/cnslMng/update/cnslCtnt").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.param("rcptNo", "201601050025")
				.param("procCtnt", "테스트처리")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.result").value("201601050025"));
	}
	
	/**
	 * 거래시도 탭 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetTradeFailList() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/trdTryList").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("strtDt", "2015.06.01")
				.param("endDt", "2015.06.01")
				.param("payMphnId", "123456789")
				.param("selectDate", "T")
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
	 * 거래시도 리스트 카운트 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetTradeTryListCount() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/trdTryListCount").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("strtDt", "2015.06.01")
				.param("endDt", "2015.06.01")
				.param("payMphnId", "123456789")
				.param("selectDate", "T")
				.param("pageParam.pageIndex", "1")
				.param("pageParam.rowCount", "5")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$").exists());
	}
	
	/**
	 * 이전 상담내역 리스트 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetCnslChgList() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/cnslChgList").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.param("trdNos", "20150731105739049322")
				.param("pageParam.pageIndex", "2")
				.param("pageParam.rowCount", "5")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.total").exists())
				.andExpect(jsonPath("$.content").isArray());
	}
	
	/**
	 * 결제차단내역 등록화면 폼 데이터 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testPayItcptRegView() throws Exception{
		this.mockMvc.perform(get("/cnslMng/payItcpt/view").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$").isArray());
	}
	
	/**
	 * 결제차단이력 리스트 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetPayItcptList() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/payItcptList").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("strtDt", "2015.06.01")
				.param("endDt", "2016.02.01")
				.param("custMphnNo", "01012345678")
				.param("payMphnId", "123456789")
				.param("payrSeq", "81008423")
				.param("selectDate", "T")
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
	 * 결제차단이력 리스트 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetPayItcpt() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/payItcpt").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("itcptRegSeq", "66")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$").exists());
	}
	
	/**
	 * 결제차단 등록 테스트
	 * @throws Exception
	 */
	@Test
	public void testSavePayItcpt() throws Exception{
		this.mockMvc.perform(post("/cnslMng/save/payItcpt").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("payMphnId", "123456789")
				.param("payrSeq", "81008423")
				.param("itcptReqrClfFlg", "I")
				.param("itcptReqrNm", "요청자")
				.param("itcptClfFlg", "P")
				.param("procClfFlg", "R")
				.param("brthYmd", "1990.01.01")
				.param("procRsn", "차단테스트입니다.")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.result").exists());
	}
	
	/**
	 * 결제차단 해제 테스트
	 * @throws Exception
	 */
	@Test
	public void testUpdatePayItcpt() throws Exception{
		this.mockMvc.perform(post("/cnslMng/update/payItcpt").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("itcptRegSeq", "23")
				.param("procClfFlg", "U")
				.param("procRsn", "차단해제테스트입니다.")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.success").value(true));
	}
	
	/**
	 * 처리내용 이관 테스트
	 * @throws Exception
	 */
	@Test
	public void testTjurProcCtnt() throws Exception{
		this.mockMvc.perform(post("/cnslMng/tjurProc/procCtnt").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("rcptNo", "201601050025")
				.param("tjurClfFlg", "C")
				.param("cnslTjurCtnt", "테스트 상담내용입니다.")
				.param("procTjurCtnt", "이관처리요청 내용입니다.")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.success").value(true));
	}
	
	/**
	 * 사업부 이관 메일 폼 테스트
	 * @throws Exception
	 */
	@Test
	public void testViewTjurEmailForm() throws Exception{
		this.mockMvc.perform(get("/cnslMng/tjurProc/view").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$").isArray());
	}
	
	/**
	 * 사업부 이관 메일 발송 테스트
	 * @throws Exception
	 */
	@Test
	public void testSendTjurProc() throws Exception{
		this.mockMvc.perform(post("/cnslMng/tjurProc/sendEmail").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("title", "사업부 이관 요청 테스트메일입니다.")
				.param("deptCd", "ATIPOP")
				.param("emailAddr", "adjust@impay.com")
				.param("cnslCtnt", "테스트 상담내용")
				.param("reqCtnt", "사업부 정산 이관 요청입니다.")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.success").value(true));
	}
	
	/**
	 * 결과 통보 테스트
	 * @throws Exception
	 */
	@Test
	public void testSendNotiResult() throws Exception{
		this.mockMvc.perform(post("/cnslMng/send/notiRslt").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("rcptNo", "201601050025")
				.param("rsltNotiMthd", "M")
				.param("email", "test@customer.com")
				.param("testYn", "N")
				.param("procCtnt", "결과처리테스트")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.success").value(true));
	}
	
	/**
	 * 결과통보 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetNotiResult() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/notiRslt").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("sndReqSeq", "2016020100000252")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$").exists());
	}
	
	/**
	 * 문서보관함 리스트 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetSmsDocumentList() throws Exception{
		this.mockMvc.perform(get("/cnslMng/search/smsDocuList").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$").isArray());
	}
	
	/**
	 * 상담 시나리오 팝업 호출 테스트
	 * @throws Exception
	 */
	@Test
	public void testCnslScenarioPopup() throws Exception{
		this.mockMvc.perform(get("/cnslMng/openCnslScenarioPopup").accept(MediaType.TEXT_HTML).characterEncoding("utf-8"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/jsp/csm/popup/cnslScenarioPopup.jsp"))
				.andExpect(model().attributeExists("cnslClfCdList"))
				.andExpect(model().attributeExists("cnslScnrList"));
	}
	
	/**
	 * 상담 시나리오 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void testGetCnslScriptContent() throws Exception{
		this.mockMvc.perform(post("/cnslMng/search/cnslScrptList").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.param("srchText", "결제")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$").exists());
	}
	
	/**
	 * 거래명세서 인쇄팝업 호출 테스트
	 * @throws Exception
	 */
	@Test
	public void testOpenReceiptPopup() throws Exception{
		this.mockMvc.perform(get("/cnslMng/openReceiptPrint").accept(MediaType.TEXT_HTML).characterEncoding("utf-8")
				.param("custMphnNo", "01012345678")
				.param("trdNos", "20150731105739049322")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/jsp/csm/popup/receiptPrint.jsp"))
				.andExpect(model().attributeExists("mphnNo"))
				.andExpect(model().attributeExists("currentYear"))
				.andExpect(model().attributeExists("trdList"));
	}

}
