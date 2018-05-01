/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.sysm.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhncorp.lucy.security.xss.XssPreventer;
import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.mapper.CodeNameMapper;
import com.skplanet.impay.ccs.common.service.mapper.UserMapper;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.sysm.model.AnnoDescReg;
import com.skplanet.impay.ccs.sysm.model.AnnoDescRegSearch;
import com.skplanet.impay.ccs.sysm.service.mapper.AnnoDescRegMapper;

/**
 * 고객센터 공지사항 Service
 * @author JuneHee, Jang
 *
 */
@Service
public class AnnoDescRegService {
	
	private static final Logger logger = LoggerFactory.getLogger(AnnoDescRegService.class);
	
	@Autowired
	private AnnoDescRegMapper annoDescRegMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CodeNameMapper codeNameMapper; 
	
	/**
	 * 공지사항 조회
	 * @param notiModel 고객센터 공지사항 조회 조건
	 * @return Page<AnnoDescReg>
	 */
	public Page<AnnoDescReg> getNotiBoardList(AnnoDescRegSearch notiSearch) {
		final List<AnnoDescReg> list = new ArrayList<AnnoDescReg>();
		this.annoDescRegMapper.getNotiBoardList(notiSearch, new ResultHandler<AnnoDescReg>(){
			@Override
			public void handleResult(ResultContext<? extends AnnoDescReg> context) {
				AnnoDescReg obj = context.getResultObject();
				obj.setBordClfCd(codeNameMapper.selectCodeName(obj.getBordClfCd()));
				obj.setRegrNm(userMapper.selectUserNm(obj.getRegr()));
				obj.setLastChgrNm(userMapper.selectUserNm(obj.getLastChgr()));
				list.add(obj);
			}
		});
		int count = this.annoDescRegMapper.getNotiBoardListCount(notiSearch);
		return new Page<AnnoDescReg>(count, list);
	}
	/**
	 * 공지사항 상세 내역 조회
	 * @param clctBordSeq 고객센터 게시판 순서
	 * @return AnnoDescReg
	 */
	public AnnoDescReg getAnnoDescInfo(long clctBordSeq) {
		AnnoDescReg notiModel = annoDescRegMapper.selectAnnoDescInfo(clctBordSeq);
		notiModel.setBordClfCdNm(codeNameMapper.selectCodeName(notiModel.getBordClfCd()));
		notiModel.setCtnt(XssPreventer.unescape(notiModel.getCtnt()));
		return notiModel;
	}
	/**
	 * 공지사항 등록
	 * @param annoDescReg 고객센터 공지사항 정보
	 * @param userInfo  사용자 정보
	 * @return RestResult<String>
	 */
	public RestResult<String> addAnnoDescReg(AnnoDescReg annoDescReg, CustomUserDetails userInfo){
		RestResult<String> result = new RestResult<String>();
		
		if(StringUtils.isEmpty(annoDescReg.getTitlBldYn())){
			annoDescReg.setTitlBldYn("N");
		}
		
		annoDescReg.setRegr(userInfo.getUserNm());
		annoDescReg.setLastChgr(userInfo.getUserNm());
		
		annoDescRegMapper.insertAnnoDesc(annoDescReg);
		result.setSuccess(true);
		result.setMessage("success");
		
		return result;
	}
	/**
	 * 공지사항 등록 수정
	 * @param annoDescReg 고객센터 공지사항 정보
	 * @param userInfo 사용자 정보
	 * @return RestResult<String>
	 */
	public RestResult<String> updateAnnoDescReg(AnnoDescReg annoDescReg, CustomUserDetails userInfo) {
		RestResult<String> result = new RestResult<String>();
		
		if(StringUtils.isEmpty(annoDescReg.getTitlBldYn())){
			annoDescReg.setTitlBldYn("N");
		}
		
		annoDescReg.setRegr(userInfo.getUserNm());
		annoDescReg.setLastChgr(userInfo.getUserNm());
		
		annoDescRegMapper.updateAnnoDesc(annoDescReg);
		result.setSuccess(true);
		result.setMessage("success");
		return result;
	}
}
