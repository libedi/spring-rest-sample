/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.service.inf;

/**
 * 메시지 국제화 Service
 * 
 * @author 최재용
 *
 */
public interface MessageByLocaleService {

    /**
     * 메세지 출력
     * 
     * @param id 메세지 코드
     * @return 메세지
     */
    public String getMessage(String id);
    
    /**
     * 메세지 출력
     * 
     * @param id 메세지 코드
     * @param obj 파라미터
     * @return 메세지
     */
    public String getMessage(String id, Object[] obj);
}