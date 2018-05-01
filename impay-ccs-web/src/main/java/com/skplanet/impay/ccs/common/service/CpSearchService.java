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

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.impay.ccs.common.model.CpList;
import com.skplanet.impay.ccs.common.model.CpSearch;
import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.service.mapper.CodeNameMapper;
import com.skplanet.impay.ccs.common.service.mapper.CpSearchMapper;

/**
 * 가맹점 조회 Service
 * @author Sangjun, Park
 *
 */
@Service
public class CpSearchService {
	@Autowired 
    private CpSearchMapper cpSearchMapper;
    
	@Autowired
	private CodeNameMapper codeNameMapper;

	/**
	 * 가맹점 리스트 조회
	 * @param cpSearch 가맹점 조회 정보
	 * @return Page<CpList>
	 */
	public Page<CpList> selectCpList(CpSearch cpSearch) {
		final List<CpList> list = new ArrayList<CpList>();
		cpSearchMapper.selectCpList(cpSearch, new ResultHandler<CpList>(){
			@Override
			public void handleResult(ResultContext<? extends CpList> context) {
				CpList obj = context.getResultObject();
				obj.setStatNm(codeNameMapper.selectCodeName(obj.getStatCd()));
				list.add(obj);
			}
		});
		int totalCount = cpSearchMapper.selectCpCount(cpSearch);
		
		return new Page<CpList>(totalCount, list);
	}

}
