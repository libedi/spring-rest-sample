/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * String 유틸
 * @author KHJIN
 * @since 2015.10
 */
public class CustomStringUtils {
	public static String replaceUsernameForAsterik(String target) {	
		return target.substring(0, target.length()-2) + "**";
	}
	
	public static String replaceIpForAsterik(String target) {
		String[] tempStr = target.split("[.]", 0);
		if(tempStr.length == 4) {
			target = tempStr[0] + "." + tempStr[1] + ".*." + tempStr[3]; 
		} 
		return target;
	}
	
	/**
	 * 전화번호 가운데번호 별표변환 
	 * @param telNo
	 * @return 암호화된 전화번호 : 010****1234
	 */
	public static String replaceTelNoForAsterik(String telNo) {
		String encodedTelNo = telNo;
		String[] telNos = CommonUtil.telToArray(telNo);
		if(telNos != null && telNos.length == 3){
			encodedTelNo = telNos[0] + "****" + telNos[2];
		}
		return encodedTelNo;
	}
	
	/**
	 * [Mandatory] 스트링(',' 포함)을 ArrayList로 변환
	 * @param    [Optional]설명
	 * @return   ArrayList
	 * @warning  [Optional]함수의 제약사항이나 주의해야 할 점
	 * @execption      [Mandatory]throw하는 exception들에 대한 설명
	 * @see      [Optional]관련 정보(관련 함수, 관련 모듈)
	 */
	public static ArrayList<String> convertStringToArrayList(String value){
		
		// 결과값
		ArrayList<String> result = null;
		
		// 스트링을 ','로 split
		String[] valueArr = value.split(",");
		
		// String배열을 리스트로
		result = new ArrayList<String>(Arrays.asList(valueArr));
		
		// 리턴
		return result;
	}
	
	/**
	 * [Mandatory] Null 또는 공백여부 체크
	 *
	 * @param    [Optional]설명
	 * @return   boolean
	 * @warning  [Optional]함수의 제약사항이나 주의해야 할 점
	 * @execption      [Mandatory]throw하는 exception들에 대한 설명
	 * @see      [Optional]관련 정보(관련 함수, 관련 모듈)
	 */
	public static boolean isNull(String value){

		boolean result = false;
		
		if(value == null || value.equals("") || value.equals("null")){
			
			result = true;
			
		}
		
		return result;
		
	}
	
	/**
	 * [Mandatory] Method의 핵심 기능 및 사용된 알고리즘을 완전한 문장으로 기술한다.
	 *
	 * @param    [Optional]설명
	 * @return   boolean
	 * @warning  [Optional]함수의 제약사항이나 주의해야 할 점
	 * @execption      [Mandatory]throw하는 exception들에 대한 설명
	 * @see      [Optional]관련 정보(관련 함수, 관련 모듈)
	 */
	public static String nvl(String value, String defaultValue){
		
		String result = "";
		
		if(isNull(value)){
			
			result = defaultValue;
					
		}else{
			
			result = value;
		}
		
		return result;
		
	}
	
	public static String nvl(String value){
		
		String result = "";
		
		result = nvl(value, "");
		
		return result;
		
	}
	
	/**
	 * [Mandatory] 화폐 표시(3자리마다 콤마)
	 *
	 * @param    [Optional]설명
	 * @return   String
	 * @warning  [Optional]함수의 제약사항이나 주의해야 할 점
	 * @execption      [Mandatory]throw하는 exception들에 대한 설명
	 * @see      [Optional]관련 정보(관련 함수, 관련 모듈)
	 */
	public static String formatMoney(String value){
		
		String result = "";
		
		NumberFormat df = new DecimalFormat("#,##0"); 
		
		result = df.format(Long.valueOf(nvl(value, "0")).longValue());
		
		return result;
	}
	
	public static String formatMoney(long value){ 
		
		String result = "";
		
		NumberFormat df = new DecimalFormat("#,##0");
		
		result = df.format(value);
		
		return result;
	}
	
}