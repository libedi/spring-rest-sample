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
 * 코드 데이터의 상위 코드 값을 정의 한다.
 *
 * @author Jibeom Jung
 */
public enum ComLCd {
    ADJUSTMENT_METHOD("AM0000"),
    TRANSACTION_TYPE("TT0000"),
    ENTERPRISE_RELATION_TYPE("CV0000"),
	CONTENT_PROVIDER_STATUS("ST0000"),
	CONTENT_PROVIDER_PAYMENT_GOODS("IG0000"),
	CONTENT_PROVIDER_CATEGORY("KT0000"),
	CONTENT_PROVIDER_INTERACT_METHOD("CI0000"),
	PAYMENT_RESULT_NOTIFY_METHOD("RN0000"),
	BANK_CODE("BC0000"),
	COMMUNICATION_CORPORATION_CLASSIFICATION("CK0000"),// 이통사 구분
	CONTENT_PROVIDER_SERVICE_CLASSIFICATION("CN0000"),// 가맹점서비스 구분
	CONTRACT_CLASSIFICATION("CC0000"),
	SCALE_CLASSIFICATION("SC0000"),
//	CONTENTS_PROVIDER_SERVICE_CODE("CN0000") 
	TROUBLE_CLASSIFICATION_CODE("TO0000"),	
	CONTRACT_ATTACH_DOCUMENT("DK0000"),	
	DAY("WD0000"),
	COUNSEL_CLASSFICATION_UPPER("TH0000"),	// 상담구분상위
	COUNSEL_CLASSFICATION_LOWER("TL0000"),	// 상담구분하위
	COUNSEL_TYPE("TK0000"),	// 상담유형
	RECEIPT_TYPE("RK0000"),	// 접수유형
	PAYBACK_TYPE("RT0000")	// 환불유형
	;

    private final String value;

    ComLCd(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }
}
