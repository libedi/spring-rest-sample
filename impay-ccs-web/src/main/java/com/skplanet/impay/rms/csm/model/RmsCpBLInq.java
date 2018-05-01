/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.rms.csm.model;

/**
 * 상담 관리 (RMS) > 청구/수납(누적) Model
 * @author Sunghee Park
 */
public class RmsCpBLInq {
	
	private int idx; //번호
	private String valClf = ""; //차단구분
	private String valClfNm = ""; //차단구분명
	private String val = ""; //적용대상
	private String cpClf = ""; //차단범위
	private String cpClfNm = ""; //차단범위명
	private String cpId = ""; //법인/그룹/가맹점ID
	private String cpNm = ""; //법인/그룹/가맹점명
	private String rmk = ""; //차단사유
	private String useYn = ""; //사용여부
	private String inptDt = ""; //차단일시
	private String inptNm = ""; //차단자
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getValClf() {
		return valClf;
	}
	public void setValClf(String valClf) {
		this.valClf = valClf;
	}
	public String getValClfNm() {
		return valClfNm;
	}
	public void setValClfNm(String valClfNm) {
		this.valClfNm = valClfNm;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public String getCpClf() {
		return cpClf;
	}
	public void setCpClf(String cpClf) {
		this.cpClf = cpClf;
	}
	public String getCpClfNm() {
		return cpClfNm;
	}
	public void setCpClfNm(String cpClfNm) {
		this.cpClfNm = cpClfNm;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getCpNm() {
		return cpNm;
	}
	public void setCpNm(String cpNm) {
		this.cpNm = cpNm;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getInptDt() {
		return inptDt;
	}
	public void setInptDt(String inptDt) {
		this.inptDt = inptDt;
	}
	public String getInptNm() {
		return inptNm;
	}
	public void setInptNm(String inptNm) {
		this.inptNm = inptNm;
	}
	@Override
	public String toString() {
		return "RmsCpBLInq [idx=" + idx + ", valClf=" + valClf + ", valClfNm=" + valClfNm + ", val=" + val + ", cpClf="
				+ cpClf + ", cpClfNm=" + cpClfNm + ", cpId=" + cpId + ", cpNm=" + cpNm + ", rmk=" + rmk + ", useYn="
				+ useYn + ", inptDt=" + inptDt + ", inptNm=" + inptNm + "]";
	}
}
