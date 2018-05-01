/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.constants;

/**
 * 메뉴 코드값
 * @author Sangjun, Park
 *
 */
public enum MenuCd {
	/**
	 * 상담관리 화면 메뉴ID
	 */
	COUNSEL_MANAGEMENT_LV2_MENU_ID("CCM001")
	
	;
	
    private final String value;

    MenuCd(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }
    
    public static MenuCd value(String value){
    	switch(value){
    	case "CCM001" : return MenuCd.COUNSEL_MANAGEMENT_LV2_MENU_ID;
    	default: throw new AssertionError("Unknown value: " + value);
    	}
    }
}