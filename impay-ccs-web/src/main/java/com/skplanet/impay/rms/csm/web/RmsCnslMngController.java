/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.rms.csm.web;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.csm.model.CnslSearch;
import com.skplanet.impay.ccs.framework.exception.BusinessException;
import com.skplanet.impay.ccs.framework.model.ValidationMarker.Create;
import com.skplanet.impay.ccs.framework.model.ValidationMarker.Update;
import com.skplanet.impay.rms.csm.model.RmsChrgRcptAcc;
import com.skplanet.impay.rms.csm.model.RmsChrgRcptMon;
import com.skplanet.impay.rms.csm.model.RmsCpBLInq;
import com.skplanet.impay.rms.csm.model.RmsDftNum;
import com.skplanet.impay.rms.csm.model.RmsFraudReliefReg;
import com.skplanet.impay.rms.csm.model.RmsRmBlkReliefReg;
import com.skplanet.impay.rms.csm.model.RmsRmChangeHis;
import com.skplanet.impay.rms.csm.service.RmsCnslMngService;
import com.skplanet.impay.rms.util.RmsMessageUtil;

/**
 * 상담 관리 (RMS) Controller
 * @author Sunghee Park
 *
 */
@Controller
@RequestMapping("/rmsCnslMng")
public class RmsCnslMngController {
	
	private final static Logger logger = LoggerFactory.getLogger(RmsCnslMngController.class);
	
	@Autowired
	private RmsMessageUtil rmsMessageUtil;
	
	@Autowired
	private RmsCnslMngService rmsCnslMngService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/**
	 * 청구/수납(누적) 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	@RequestMapping(value = "/search/chrgRcptAccList", method = RequestMethod.GET)
	public @ResponseBody List<RmsChrgRcptAcc> getChrgRcptAccList(CnslSearch cnslSearch) {
		return rmsCnslMngService.getChrgRcptAccList(cnslSearch);
	}
	
	/**
	 * 청구/수납(월별) 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	@RequestMapping(value = "/search/chrgRcptMonList", method = RequestMethod.GET)
	public @ResponseBody List<RmsChrgRcptMon> getChrgRcptMonList(CnslSearch cnslSearch) {
		return rmsCnslMngService.getChrgRcptMonList(cnslSearch);
	}
	
	/**
	 * 수납 회차별 미납횟수 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	@RequestMapping(value = "/search/npayCntList", method = RequestMethod.GET)
	public @ResponseBody List<RmsDftNum> getNpayCntList(CnslSearch cnslSearch) {
		return rmsCnslMngService.getNpayCntList(cnslSearch);
	}
	
	/**
	 * 미납금액 상세 조회
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/search/npayAmtDetail", method = RequestMethod.GET)
	public @ResponseBody RmsDftNum getNpayAmtDetail(RmsDftNum params) {
		return rmsCnslMngService.getNpayAmtDetail(params);
	}
	
	/**
	 * RM 차단/해제 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	@RequestMapping(value = "/search/rmBlkReliefList", method = RequestMethod.GET)
	public @ResponseBody List<RmsRmBlkReliefReg> getRmBlkReliefList(CnslSearch cnslSearch) {
		return rmsCnslMngService.getRmBlkReliefList(cnslSearch);
	}
	
	/**
	 * RM 해제
	 * @param param
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/save/rmRelief", method = RequestMethod.POST)
	public @ResponseBody RestResult<String> saveRmRelief(@Validated(Create.class) RmsRmBlkReliefReg param, BindingResult bindingResult) {
		RestResult<String> result = new RestResult<String>();
		if (bindingResult.hasFieldErrors()) {
			logger.error(bindingResult.getFieldError().getDefaultMessage());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(rmsMessageUtil.getMessage("common.error.message"));
		} else {
			rmsCnslMngService.relieveRm(param);
			result.setSuccess(true);
			result.setMessage("success");
		}
		return result;
	}
	
	/**
	 * RM 차단(해제취소)
	 * @param param
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/save/rmBlk", method = RequestMethod.POST)
	public @ResponseBody RestResult<String> saveRmBlk(@Validated(Update.class) RmsRmBlkReliefReg param, BindingResult bindingResult) {
		RestResult<String> result = new RestResult<String>();
		if (bindingResult.hasFieldErrors()) {
			logger.error(bindingResult.getFieldError().getDefaultMessage());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(rmsMessageUtil.getMessage("common.error.message"));
		} else {
			rmsCnslMngService.blockRm(param);
			result.setSuccess(true);
			result.setMessage("success");
		}
		return result;
	}
	
	/**
	 * 불량고객 차단 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	@RequestMapping(value = "/search/fraudBlkList", method = RequestMethod.GET)
	public @ResponseBody List<RmsFraudReliefReg> getFraudBlkList(CnslSearch cnslSearch) {
		return rmsCnslMngService.getFraudBlkList(cnslSearch);
	}
	
	/**
	 * 불량고객 해제
	 * @param param
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/save/fraudRelief", method = RequestMethod.POST)
	public @ResponseBody RestResult<String> saveFraudRelief(@Validated(Update.class) RmsFraudReliefReg param, BindingResult bindingResult) {
		RestResult<String> result = new RestResult<String>();
		if (bindingResult.hasFieldErrors()) {
			logger.error(bindingResult.getFieldError().getDefaultMessage());
			result.setFieldError(bindingResult.getFieldErrors());
			result.setSuccess(false);
			result.setMessage(rmsMessageUtil.getMessage("common.error.message"));
		} else {
			rmsCnslMngService.relieveFraud(param);
			result.setSuccess(true);
			result.setMessage("success");
		}
		return result;
	}
	
	/**
	 * 가맹점B/L 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	@RequestMapping(value = "/search/cpBLInqList", method = RequestMethod.GET)
	public @ResponseBody List<RmsCpBLInq> getCpBLInqList(CnslSearch cnslSearch) {
		return rmsCnslMngService.getCpBLInqList(cnslSearch);
	}
	
	/**
	 * RM 변경이력 목록 조회
	 * @param cnslSearch
	 * @return
	 */
	@RequestMapping(value = "/search/rmChangeHisList", method = RequestMethod.GET)
	public @ResponseBody List<RmsRmChangeHis> getRmChangeHisList(CnslSearch cnslSearch) {
		return rmsCnslMngService.getRmChangeHisList(cnslSearch);
	}
	
	@ExceptionHandler(BusinessException.class)
	public @ResponseBody RestResult<String> exception(Exception e) {
		logger.error(e.toString());
		RestResult<String> result = new RestResult<>();
		result.setSuccess(false);
		result.setMessage(e.getMessage());
		return result;
	}
	
	@ExceptionHandler({SQLException.class, UncategorizedDataAccessException.class, DataAccessException.class})
	public @ResponseBody RestResult<String> sqlException(Exception e) {
		logger.error(e.toString(), e);
		
		String message = rmsMessageUtil.getMessage("rms.message.exception.sql");
		
		for (Throwable t = e; t != null; t = t.getCause()) {
			if (t instanceof SQLException) {
				SQLException sqlException = (SQLException)t;
				int errorCode = sqlException.getErrorCode();
				if (errorCode == 12899) {
					String errorMessage = sqlException.getMessage();
					if (errorMessage.indexOf("\"RMK\"") >= 0) {
						message = rmsMessageUtil.getMessage("rms.message.exception.sql.overSize.rmk");
						break;
					}
				}
			}
		}
		
		RestResult<String> result = new RestResult<>();
		result.setSuccess(false);
		result.setMessage(message);
		return result;
	}
}
