/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.model;

/**
 * 이통사 원가
 * @author jisu park
 *
 */
public class CommcCost {
	private String commcCostId = "";			//이통사 원가ID
	private String commcClf = "";				//이통사 구분
	private String costClfCd = "";				//원가 구분 코드
	private String costCdNm = "";				//원가 코드 명
	private float commcFee = 0f;				//이통사 수수료율
	private String feeVatYn = "";				//수수료 부가세 여부
	private double smsUprc = 0;					//SMS단가
	private String useYn = "";					//사용여부
	private String rmk = "";					//비고
	
	public String getCommcCostId() {
		return commcCostId;
	}
	public void setCommcCostId(String commcCostId) {
		this.commcCostId = commcCostId;
	}
	public String getCommcClf() {
		return commcClf;
	}
	public void setCommcClf(String commcClf) {
		this.commcClf = commcClf;
	}
	public String getCostClfCd() {
		return costClfCd;
	}
	public void setCostClfCd(String costClfCd) {
		this.costClfCd = costClfCd;
	}
	public String getCostCdNm() {
		return costCdNm;
	}
	public void setCostCdNm(String costCdNm) {
		this.costCdNm = costCdNm;
	}
	public float getCommcFee() {
		return commcFee;
	}
	public void setCommcFee(float commcFee) {
		this.commcFee = commcFee;
	}
	public String getFeeVatYn() {
		return feeVatYn;
	}
	public void setFeeVatYn(String feeVatYn) {
		this.feeVatYn = feeVatYn;
	}
	public double getSmsUprc() {
		return smsUprc;
	}
	public void setSmsUprc(double smsUprc) {
		this.smsUprc = smsUprc;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
}
