/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.sysm.service.mapper;

import org.apache.ibatis.session.ResultHandler;

import com.skplanet.impay.ccs.sysm.model.AnnoDescReg;
import com.skplanet.impay.ccs.sysm.model.AnnoDescRegSearch;

public interface AnnoDescRegMapper {

	/**
	 * 고객센터 공지사항 리스트 조회
	 * @param notiSearch
	 * @return
	 */
	void getNotiBoardList(AnnoDescRegSearch notiSearch, ResultHandler<AnnoDescReg> resultHandler);

	/**
	 * 고객센터 공지사항 카운트 조회
	 * @param notiSearch
	 * @return
	 */
	int getNotiBoardListCount(AnnoDescRegSearch notiSearch);

	AnnoDescReg selectAnnoDescInfo(long clctBordSeq);
	// 등록
	void insertAnnoDesc(AnnoDescReg annoDescReg);
	// 수정
	void updateAnnoDesc(AnnoDescReg annoDescReg);

}
