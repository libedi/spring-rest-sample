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
 * 코드 데이터의 중분류 코드 값을 정의 한다.
 *
 * @author 
 */
public enum ComMCd {
	ADJUSTMENT_INTERVAL_LARGE_CODE("APLC00"),
	ADJUSTMENT_INTERVAL_MIDDLE_CODE("APMC00"),
	ADJUSTMENT_INTERVAL_SMALL_CODE("APSC00"),
	READJUSTMENT_INTERVAL_MIDDLE_CODE("RAMC00"),
	CONTENT_PROVIDER_RESPONSE("TSCR00"),//가맹점 응대
	PROMOTION_CLASSFICATION("PTAM00"),
	COMMUNICATIONS_CORPORATION_CODE("CKCC00"), // 이통사 (SKT, KT, LGU+) 
	COMMUNICATIONS_CORPORATION_MVNO("CKMV00"), // 이통사 MVNO(CJH)
	COMMUNICATIONS_CORPORATION_ALL("CKAL00"),// 이통사 전체 
	REVERSE_SALES_PARTNERSHIP_COOPERATION("CKVS00"), //역판매 제휴사 (KG모빌 / 다날)
	CONSULTATION_RECEIPT_CLASSFICATION("RKRK00"), //상담 접수 구분
	
	ITS_ETC("TSET00"), 					//ITS 기타
	ITS_OPEN("TSOP00"),					//ITS 개통
	CONTRACT_JUDGMENT("ST1000"),		//계약심사
	OPEN("ST2000"),						//개통
	SERVICE_STATUS("ST3000"),			//서비스 상태
	SERVICE_STOP("ST8000"),				//서비스 중지
	SERVICE_RELEASE("ST9000"),			//서비스 해지
	COUNSEL_TYPE("TKTK00"),				//상담유형
	EVENT_TYPE("TKEV00"),				//이벤트유형
	NPAYADD_WHITELIST_CLASSFICATION("NWNW00"), // 미납가산금 W/L 구분
	CALL_CENTER_BORD("BDCS00"),	// 고객센터 게시판
	
	PAYGATE_CLASSFICATION("PGPG00"),			//PG사 구분
	RE_SALES_COMPANY_CLASSFICATION("PGRS00")	// 재판매사 구분

	
	
	;

    private final String value;

    ComMCd(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }
}