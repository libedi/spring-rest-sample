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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.impay.ccs.common.model.Code;
import com.skplanet.impay.ccs.common.service.mapper.CodeMapper;

/**
 * 
 * @author Sangjun, Park
 *
 */
@Service
public class CodeService {
		
	private static Logger logger = LoggerFactory.getLogger(CodeService.class);
	
	@Autowired 
	private CodeMapper codeMapper;
	
	/**
	 * 공통코드조회
	 * @param uprCd 상위코드
	 * @return List<Code>
	 */
	public List<Code> selectCodeByUprCd(String uprCd) {
		
		return codeMapper.selectCodeByUprCd(uprCd);
	}
	
	/**
	 * 공통코드조회
	 * @param uprCd 상위코드
	 * @return List<Code>
	 */
	public List<Code> selectSCodeByUprCd(String uprCd) {
		return codeMapper.selectSCodeByUprCd(uprCd);
	}
	
	/**
	 * 공통코드조회
	 * @param cd 코드
	 * @return Code
	 */
	public Code selectCode(String cd){
		return codeMapper.selectCode(cd);
	}
}