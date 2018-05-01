/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.cam.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skplanet.impay.ccs.cam.model.ClaimAnalysisMng;
import com.skplanet.impay.ccs.cam.model.ClmAnlsCase;
import com.skplanet.impay.ccs.cam.model.ClmAnlsTjurRcpt;
import com.skplanet.impay.ccs.cam.model.ClmAnlsType;
import com.skplanet.impay.ccs.cam.service.ClaimAnalysisMngService;
import com.skplanet.impay.ccs.common.constants.ComLCd;
import com.skplanet.impay.ccs.common.constants.ComMCd;
import com.skplanet.impay.ccs.common.constants.ComSCd;
import com.skplanet.impay.ccs.common.log.idms.service.IdmsCustomerLogService;
import com.skplanet.impay.ccs.common.model.Code;
import com.skplanet.impay.ccs.common.service.CodeService;
import com.skplanet.impay.ccs.common.service.CommonService;
import com.skplanet.impay.ccs.common.service.UserService;
import com.skplanet.impay.ccs.common.util.DateUtil;
import com.skplanet.impay.ccs.common.util.ExcelDownloadFile;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.uim.model.UserMng;


/**
 * 클레임 분석관리 Controller
 * @author Junehee, Jang
 *
 */
@Controller
@RequestMapping("/cam/claimAnslMng")
public class ClaimAnalysisMngController {

	private final static Logger logger = LoggerFactory.getLogger(ClaimAnalysisMngController.class);
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private ClaimAnalysisMngService claimAnalysisMngService; 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ExcelDownloadFile excelView;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IdmsCustomerLogService idmsCustomerLogService;
	
	/**
	 * 클레임 분석 화면 
	 * @param model Model
	 * @return String
	 */
	@RequestMapping(value = "/view", method=RequestMethod.POST)	
	public String claimAnslMngView(Model model){
		
		String currDate = DateUtil.getCurrentDate();
		currDate = currDate.substring(0,4)+"."+currDate.substring(4,6)+"."+currDate.substring(6,8);
		model.addAttribute("currDate", currDate);	// 현재 날짜
		
		List<UserMng> ccsUser = userService.getUserTypClfInfo(ComSCd.ADMIN_TYP_CODE.value());
		
		List<Code> commcClf = new ArrayList<>();
		commcClf.add(codeService.selectCode(ComSCd.UNDECIDED_CODE1.value()));
		commcClf.addAll(codeService.selectCodeByUprCd(ComMCd.COMMUNICATIONS_CORPORATION_CODE.value()));
		commcClf.addAll(codeService.selectCodeByUprCd(ComMCd.COMMUNICATIONS_CORPORATION_MVNO.value()));
		commcClf.addAll(codeService.selectCodeByUprCd(ComMCd.REVERSE_SALES_PARTNERSHIP_COOPERATION.value()));
		
		
		List<Code> cnslClfCdList = codeService.selectSCodeByUprCd(ComLCd.COUNSEL_CLASSFICATION_UPPER.value());
		List<Code> cnslTypCdList = codeService.selectCodeByUprCd(ComMCd.COUNSEL_TYPE.value());
		List<Code> rcptMthdCdList = codeService.selectSCodeByUprCd(ComLCd.RECEIPT_TYPE.value());
		List<Code> eventTyp = new ArrayList<>();
		eventTyp.add(codeService.selectCode(ComSCd.UNDECIDED_CODE1.value()));
		eventTyp.addAll(codeService.selectCodeByUprCd(ComMCd.EVENT_TYPE.value()));
		
		// 가맹점 코드(PG)
		List<Code> pgCdList = new ArrayList<>();
		pgCdList.add(codeService.selectCode(ComSCd.PG_CLF_CODE_DANAL.value()));
		pgCdList.add(codeService.selectCode(ComSCd.PG_CLF_CODE_MOBIL.value()));
		pgCdList.add(codeService.selectCode(ComSCd.PG_CLF_CODE_SKP.value()));
		pgCdList.add(codeService.selectCode(ComSCd.PG_CLF_CODE_DAWOO.value()));
		
		Code code1 = new Code();
		code1.setCd(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ALLIANCE.value());
		code1.setCdNm(messageSource.getMessage("cam.alliance", null, Locale.KOREAN));
		Code code2 = new Code();
		code2.setCd(ComSCd.IMPAY_BUSINESS_DEPARTMENT_ADJUSTMENT.value());
		code2.setCdNm(messageSource.getMessage("cam.adjustment", null, Locale.KOREAN));
		Code code3 = new Code();
		code3.setCd(ComSCd.IMPAY_BUSINESS_DEPARTMENT_TECHNICAL.value());
		code3.setCdNm(messageSource.getMessage("cam.it", null, Locale.KOREAN));
		List<Code> tjur = new ArrayList<Code>();
		tjur.add(code1);
		tjur.add(code2);
		tjur.add(code3);
		
		// 상담 결제 구분 코드
		List<Code> cnslPayClf = codeService.selectSCodeByUprCd(ComLCd.TRANSACTION_TYPE.value());
		for(int i = 0 ; i< cnslPayClf.size(); i++){
			if(StringUtils.equals(cnslPayClf.get(i).getCd(), ComSCd.AUTO_TRANSACTION_TYPE_INFORMATION.value())){
				cnslPayClf.remove(cnslPayClf.get(i));
			}
			if(StringUtils.equals(cnslPayClf.get(i).getCd(), ComSCd.AUTO_TRANSACTION_TYPE_CERTIFICATION.value())){
				cnslPayClf.remove(cnslPayClf.get(i));
			}
			if(StringUtils.equals(cnslPayClf.get(i).getCd(), ComSCd.AUTO_TRANSACTION_TYPE_PAYMENT.value())){
				cnslPayClf.remove(cnslPayClf.get(i));	
			}
			if(StringUtils.equals(cnslPayClf.get(i).getCd(), ComSCd.TRANSACTION_TYPE_COMPLEX_PAYMENT.value())){
				cnslPayClf.remove(cnslPayClf.get(i));
			}
		}
		
		model.addAttribute("admTypCd", ComSCd.IMPAY_BUSINESS_DEPARTMENT_ALLIANCE.value()); // 관리자유형코드 : ATIPBZ   
		model.addAttribute("ccsUser", ccsUser);					// tab1		-접수자 (콜센터 직원 리스트)
		model.addAttribute("cnslClfCdList", cnslClfCdList);		// tab1		-상담구분
		model.addAttribute("cnslTypCdList", cnslTypCdList);		// tab1/2	-상담유형
		model.addAttribute("rcptMthdCdList", rcptMthdCdList);	// tab1/2	-접수유형
		model.addAttribute("commcClf", commcClf);				// tab1/2	-이통사
		model.addAttribute("cnslPayClf", cnslPayClf);			// tab1/2	-상담 결제 구분
		model.addAttribute("eventTyp", eventTyp);				// tab2		-이벤트
		model.addAttribute("tjur", tjur);						// tab3		-이관접수구분
		model.addAttribute("pgCdList", pgCdList);				// tab3		-가맹점 코드(PG) PG사/재판매사 
		return "/cam/claimAnalysisMng";
	}
	//TAB1
	/**
	 * TAB-1
	 * 건별 조회
	 * @param clmAnlsCase 클레임 분석 - 건별 조회 정보
	 * @param userInfo 사용자 정보
	 * @return ClaimAnalysisMng
	 */
	@RequestMapping(value = "/searchCase", method=RequestMethod.POST)
    public @ResponseBody ClaimAnalysisMng getClmAnlsCaseSearch(ClmAnlsCase clmAnlsCase, @AuthenticationPrincipal CustomUserDetails userInfo) {
		ClaimAnalysisMng result = claimAnalysisMngService.getClmAnslCaseList(clmAnlsCase); 
		// 고객 정보 LOG 수집
		if(result.getTotal() > 0){
			idmsCustomerLogService.printIdmsLog(result.getClmAnlsCaseList().get(0).getRcptNo(), "X", "CCS0007", "40001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
        return result;
    }
	/**
	 * 건별 엑셀 다운로드
	 * @param clmAnlsCase 클레임 분석 - 건별 조회 정보
	 * @param userInfo 사용자 정보
	 * @return ModelAndView
	 */
	@RequestMapping(value="/excelDown/case", method=RequestMethod.GET)
	public ModelAndView clmAnslCaseListExcelDown(ClmAnlsCase clmAnlsCase, @AuthenticationPrincipal CustomUserDetails userInfo) {
		String title = messageSource.getMessage("cam.excel.case.title", null, Locale.KOREAN);
		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("excelName", title);		// Excel File Name 설정
   		map.put("sheetName", title);		// sheet name 설정
   		map.put("excelList", claimAnalysisMngService.clmAnslCaseListExcelDown(clmAnlsCase, userInfo));
		return new ModelAndView(this.excelView, map);
	}
	/**
	 * 건별 엑셀 다운로드 건수
	 * @param clmAnlsCase 클레임 분석 - 건별 조회 정보
	 * @param map
	 * @param request
	 * @param response
	 * @return int
	 * @throws Exception
	 */
	@RequestMapping(value = "/excelDown/case/count", method = RequestMethod.GET)
	public @ResponseBody int clmAnslCaseListExcelDownCount(ClmAnlsCase clmAnlsCase, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
   		int count = claimAnalysisMngService.getCaseExcelDownCount(clmAnlsCase);
   		return count;
   	}
	
	//TAB2
	/**
	 * TAB-2
	 * 유형별 조회
	 * @param clmAnlsType 클레임 분석 - 유형별 정보
	 * @return ClaimAnalysisMng
	 */
	@RequestMapping(value = "/searchType", method=RequestMethod.POST)
    public @ResponseBody ClaimAnalysisMng getClmAnlsTypeSearch(ClmAnlsType clmAnlsType){
        return claimAnalysisMngService.getClmAnslTypeList(clmAnlsType);
    }
	/**
	 * 유형별 엑셀 다운로드
	 * @param clmAnlsType 클레임 분석 - 유형별 정보
	 * @return ModelAndView
	 */
	@RequestMapping(value="/excelDown/type", method=RequestMethod.GET)
	public ModelAndView clmAnslTypeListExcelDown(ClmAnlsType clmAnlsType){
		String title =  messageSource.getMessage("cam.excel.type.title", null, Locale.KOREAN);
		Map<String, Object> map = new HashMap<String, Object>();
   		
   		map.put("excelName", title);		// Excel File Name 설정
   		map.put("sheetName", title);		// sheet name 설정
   		map.put("excelList", claimAnalysisMngService.clmAnslTypeListExcelDown(clmAnlsType));
		return new ModelAndView(this.excelView, map);
	}
	/**
	 * 유형별 엑셀 다운로드 건수
	 * @param clmAnlsType 클레임 분석 - 유형별 정보
	 * @param map
	 * @param request
	 * @param response
	 * @return int
	 * @throws Exception
	 */
	@RequestMapping(value = "/excelDown/type/count", method = RequestMethod.GET)
   	public @ResponseBody int clmAnslTypeListExcelDownCount(ClmAnlsType clmAnlsType, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {   		
   		int count = claimAnalysisMngService.getTypeExcelDownCount(clmAnlsType);
   		return count;
   	}
	//TAB3
	/**
	 * TAB-3
	 * 이관접수별 조회
	 * @param clmAnlsType 클레임 분석 - 이관접수별 정보
	 * @return ClaimAnalysisMng
	 */
	@RequestMapping(value = "/searchTjur", method=RequestMethod.POST)
    public @ResponseBody ClaimAnalysisMng getClmAnlsTjurSearch(ClmAnlsTjurRcpt clmAnlsTjur){
        return claimAnalysisMngService.getClmAnslTjurList(clmAnlsTjur);
    }
	/**
	 * 이관접수별 엑셀 다운로드
	 * @param clmAnlsType 클레임 분석 - 이관접수별 정보
	 * @return ModelAndView
	 */
	@RequestMapping(value="/excelDown/tjur", method=RequestMethod.GET)
	public ModelAndView clmAnslTjurListExcelDown(ClmAnlsTjurRcpt clmAnlsTjur){
		String title =  messageSource.getMessage("cam.excel.tjur.title", null, Locale.KOREAN);
		Map<String, Object> map = new HashMap<String, Object>();
   		
   		map.put("excelName", title);		// Excel File Name 설정
   		map.put("sheetName", title);		// sheet name 설정
   		map.put("excelList", claimAnalysisMngService.clmAnslTjurListExcelDown(clmAnlsTjur));
		return new ModelAndView(this.excelView, map);
	}
	/**
	 * 이관접수별 엑셀 다운로드 건수
	 * @param clmAnlsType 클레임 분석 - 이관접수별 정보
	 * @param map
	 * @param request
	 * @param response
	 * @return int
	 * @throws Exception
	 */
	@RequestMapping(value = "/excelDown/tjur/count", method = RequestMethod.GET)
   	public @ResponseBody int clmAnslTjurListExcelDownCount(ClmAnlsTjurRcpt clmAnlsTjur, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {   		
   		int count = claimAnalysisMngService.getTjurExcelDownCount(clmAnlsTjur);
   		return count;
   	}
}
