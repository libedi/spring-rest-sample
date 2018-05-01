/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.service.mapper;

import org.apache.ibatis.session.ResultHandler;

import com.skplanet.impay.ccs.common.model.CpList;
import com.skplanet.impay.ccs.common.model.CpSearch;

/**
 * 가맹점 조회 Mapper
 * @author Sangjun, Park
 *
 */
public interface CpSearchMapper {

	/**
	 * 가맹점 리스트 조회 (페이징처리)
	 * @param cpSearch
	 * @param resultHandler
	 */
	void selectCpList(CpSearch cpSearch, ResultHandler<CpList> resultHandler);

	/**
	 * 가맹점 리스트 카운트 조회
	 * @param cpSearch
	 * @return
	 */
	int selectCpCount(CpSearch cpSearch);

}
