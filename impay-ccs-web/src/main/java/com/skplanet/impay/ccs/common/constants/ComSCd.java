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
 * 코드 데이터의 소분류 코드 값을 정의 한다.
 *
 * @author 
 */
public enum ComSCd {
	/**
	 * ZZZZZZ / 999999 : 미정의
	 */
	UNDECIDED_CODE1("ZZZZZZ"),
	UNDECIDED_CODE2("999999"),
	
	COMMUNICATIONS_CORPORATION_CODE_SK("CKCCSK"), 
	COMMUNICATIONS_CORPORATION_CODE_KT("CKCCKT"),
	COMMUNICATIONS_CORPORATION_CODE_LG("CKCCLG"),
	COMMUNICATIONS_CORPORATION_MVNO_CJ("CKMVCJ"),	
	
	BUSINESS_LICENSE("DKCNBR"),		// 사업자등록증
	COPY_OF_BACKBOOK("DKCNAC"),		// 통장사본
	CONTRACT("DKCNCN"),				// 계약서
	APPLICATION_FOR_ACCOUNT_DEPOSIT("DKCNDR"),	//입금의뢰신청서
	SEAL_CERTIFICATION("DKCNSN"),		//인감증명서
	/**
	 * 상담구분코드 : 제휴문의
	 */
	INQUIRE_COOPERATION("THCM26"),
	/**
	 * 첨부파일구분 : 일반
	 */
	ATTACH_FILE_GENERAL("FTNRNR"),
	/**
	 * 첨부파일구분 : 게시판
	 */
	ATTACH_FILE_BOARD("FTBSBS"),
	/**
	 * 관리자 유형 코드 : CCS - 클레임 분석 접수자(콜센터 상담원)
	 */
	ADMIN_TYP_CODE("ATIPCS"),
	/**
	 * 이관접수구분 코드 : 사업부 - 제휴
	 */
	IMPAY_BUSINESS_DEPARTMENT_ALLIANCE("ATIPBZ"),
	/**
	 * 이관접수구분 코드 : 사업부 - 정산
	 */
	IMPAY_BUSINESS_DEPARTMENT_ADJUSTMENT("ATIPOP"),
	/**
	 * 이관접수구분 코드 : 사업부 - 기술
	 */
	IMPAY_BUSINESS_DEPARTMENT_TECHNICAL("ATIPIT"),
	/**
	 * 가맹점 코드 : 다날
	 */
	PG_CLF_CODE_DANAL("PGPGDN"),
	/**
	 * 가맹점 코드 : 모빌
	 */
	PG_CLF_CODE_MOBIL("PGPGMB"),
	/**
	 * 가맹점 코드 : SKP
	 */
	PG_CLF_CODE_SKP("PGPGIP"),
	/**
	 * 가맹점 코드 : 다우기술
	 */
	PG_CLF_CODE_DAWOO("PGRSDW"),
	/**
	 * 가맹점 코드 : 비프패킹컴퍼니
	 */
	PG_CLF_CODE_BIT("PGBITP"),

	/** TT0000 하위 2 lv 중 제외 CODE 값*/
	AUTO_TRANSACTION_TYPE_INFORMATION("TTAPIF"), 	// 자동결제 정보변경
	AUTO_TRANSACTION_TYPE_CERTIFICATION("TTAPPQ"), 	// 자동결제 인증전용
	AUTO_TRANSACTION_TYPE_PAYMENT("TTAPPR"), 		// 자동결제 결제전용
	TRANSACTION_TYPE_COMPLEX_PAYMENT("TTCMCM"), 	// 복합결제
	;
	
    private final String value;

    ComSCd(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }
    
    public static ComSCd value(String value){
    	switch(value){
    	case "ZZZZZZ" : return ComSCd.UNDECIDED_CODE1;
    	case "CKCCSK" : return ComSCd.COMMUNICATIONS_CORPORATION_CODE_SK;
    	case "CKCCKT" : return ComSCd.COMMUNICATIONS_CORPORATION_CODE_KT;
    	case "CKCCLG" : return ComSCd.COMMUNICATIONS_CORPORATION_CODE_LG;
    	case "CKMVCJ" : return ComSCd.COMMUNICATIONS_CORPORATION_MVNO_CJ;
    	case "DKCNBR" : return ComSCd.BUSINESS_LICENSE;
    	case "DKCNAC" : return ComSCd.COPY_OF_BACKBOOK;
    	case "DKCNCN" : return ComSCd.CONTRACT;
    	case "DKCNDR" : return ComSCd.APPLICATION_FOR_ACCOUNT_DEPOSIT;
    	case "DKCNSN" : return ComSCd.SEAL_CERTIFICATION;
    	case "THCM26" : return ComSCd.INQUIRE_COOPERATION;
    	case "FTNRNR" : return ComSCd.ATTACH_FILE_GENERAL;
    	case "FTBSBS" : return ComSCd.ATTACH_FILE_BOARD;
    	case "ATIPBZ" : return ComSCd.IMPAY_BUSINESS_DEPARTMENT_ALLIANCE;
    	case "ATIPOP" : return ComSCd.IMPAY_BUSINESS_DEPARTMENT_ADJUSTMENT;
    	case "ATIPIT" : return ComSCd.IMPAY_BUSINESS_DEPARTMENT_TECHNICAL;
    	case "PGPGDN" : return ComSCd.PG_CLF_CODE_DANAL;
    	case "PGPGMB" : return ComSCd.PG_CLF_CODE_MOBIL;
    	case "PGPGIP" : return ComSCd.PG_CLF_CODE_SKP;
    	case "PGRSDW" : return ComSCd.PG_CLF_CODE_DAWOO;
    	case "PGBITP" : return ComSCd.PG_CLF_CODE_BIT;
    	default: throw new AssertionError("Unknown value: " + value);
    	}
    }
}