/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.rms.constants;

/**
 * 코드 데이터
 * @author Dongsang Wi
 */
public enum RmsComEnum {

	RELIEF_CLF_CD_PAYR_SEQ("D"),
	RELIEF_CLF_CD_MPHN_NO("P"),
	CUST_CHG_REG_CLF_RM("R"),
	CUST_CHG_REG_CLF_FRAUD("F"),
	BLK_RELS_CLF_BLOCK("B"),
	BLK_RELS_CLF_RELIEF("R"),
	MIN_TIME("00:00:00"),
	MAX_TIME("23:59:59"),
	DATETIME_FORMAT("yyyy.MM.dd HH:mm:ss")
	;
	
	private final String value;
	
	RmsComEnum(String value){
		this.value = value;
	}
	
	public String value(){
		return value;
	}

}