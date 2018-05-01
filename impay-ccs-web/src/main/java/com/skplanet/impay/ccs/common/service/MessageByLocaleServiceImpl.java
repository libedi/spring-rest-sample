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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.skplanet.impay.ccs.common.service.inf.MessageByLocaleService;

/**
 * 메시지 국제화 ServiceImpl
 * 
 * @author 최재용
 *
 */
@Service
public class MessageByLocaleServiceImpl implements MessageByLocaleService {

	@Autowired
	private MessageSource messageSource;

    /**
     * 메세지 출력
     * 
     * @param id 메세지 코드
     * @return 메세지
     */
	@Override
	public String getMessage(String id) {
		return this.getMessage(id, null);
	}

	/**
     * 메세지 출력
     * 
     * @param id 메세지 코드
     * @param obj 파라미터
     * @return 메세지
     */
	@Override
	public String getMessage(String id, Object[] obj) {
		return messageSource.getMessage(id, obj, LocaleContextHolder.getLocale());
	}
}