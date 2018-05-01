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
public enum RmsComCd {

	BLK_RELIEF_CD("RMBR00"),
	RM_RELS_RSN_CD("RMRR00"),
	FRAUD_RELS_RSN_CD("RMFR00"),
	MST_Y("Y"),
	WHITE_LIST("RMBRWR")
	;
	
	private final String value;
	
	RmsComCd(String value){
		this.value = value;
	}
	
	public String value(){
		return value;
	}

}