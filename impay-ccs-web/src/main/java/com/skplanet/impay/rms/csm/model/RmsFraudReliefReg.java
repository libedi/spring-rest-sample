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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.skplanet.impay.ccs.framework.model.ValidationMarker.Update;

/**
 * 상담 관리 (RMS) > 불량고객 차단/해제 등록 Model
 * @author Sunghee Park
 *
 */
public class RmsFraudReliefReg {
	
	private int idx; //번호
	
	@NotNull(groups = {Update.class})
	@Pattern(groups = {Update.class}, regexp = "^[0-9]*$")
	private String payrSeq = ""; //고객번호
	
	private String fraudClfCd = ""; //프로드분류코드
	
	private String fraudClfNm = ""; //프로드분류명
	
	private String stDt = ""; //시작일시
	
	private String edDt = ""; //종료일시
	
	@NotNull(groups = {Update.class})
	@Size(groups = {Update.class}, min=6, max=6)
	private String relsRsnCd = ""; //해제사유코드
	
	private String relsRsnNm = ""; //해제사유명
	
	@Size(groups = {Update.class}, max=255)
	private String rmk = ""; //비고
	
	private String inptDt = ""; //입력일시
	
	private String inptId = ""; //입력자ID
	
	private String inptNm = ""; //입력자명
	
	private String updtDt = ""; //수정일시
	
	private String updtId = ""; //수정자ID
	
	private String updtNm = ""; //수정자명
	
	private String rowId = ""; // 행ID

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getPayrSeq() {
		return payrSeq;
	}

	public void setPayrSeq(String payrSeq) {
		this.payrSeq = payrSeq;
	}

	public String getFraudClfCd() {
		return fraudClfCd;
	}

	public void setFraudClfCd(String fraudClfCd) {
		this.fraudClfCd = fraudClfCd;
	}

	public String getFraudClfNm() {
		return fraudClfNm;
	}

	public void setFraudClfNm(String fraudClfNm) {
		this.fraudClfNm = fraudClfNm;
	}

	public String getStDt() {
		return stDt;
	}

	public void setStDt(String stDt) {
		this.stDt = stDt;
	}

	public String getEdDt() {
		return edDt;
	}

	public void setEdDt(String edDt) {
		this.edDt = edDt;
	}

	public String getRelsRsnCd() {
		return relsRsnCd;
	}

	public void setRelsRsnCd(String relsRsnCd) {
		this.relsRsnCd = relsRsnCd;
	}

	public String getRelsRsnNm() {
		return relsRsnNm;
	}

	public void setRelsRsnNm(String relsRsnNm) {
		this.relsRsnNm = relsRsnNm;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public String getInptDt() {
		return inptDt;
	}

	public void setInptDt(String inptDt) {
		this.inptDt = inptDt;
	}

	public String getInptId() {
		return inptId;
	}

	public void setInptId(String inptId) {
		this.inptId = inptId;
	}

	public String getInptNm() {
		return inptNm;
	}

	public void setInptNm(String inptNm) {
		this.inptNm = inptNm;
	}

	public String getUpdtDt() {
		return updtDt;
	}

	public void setUpdtDt(String updtDt) {
		this.updtDt = updtDt;
	}

	public String getUpdtId() {
		return updtId;
	}

	public void setUpdtId(String updtId) {
		this.updtId = updtId;
	}

	public String getUpdtNm() {
		return updtNm;
	}

	public void setUpdtNm(String updtNm) {
		this.updtNm = updtNm;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	@Override
	public String toString() {
		return "RmsFraudReliefReg [idx=" + idx + ", payrSeq=" + payrSeq + ", fraudClfCd=" + fraudClfCd + ", fraudClfNm="
				+ fraudClfNm + ", stDt=" + stDt + ", edDt=" + edDt + ", relsRsnCd=" + relsRsnCd + ", relsRsnNm="
				+ relsRsnNm + ", rmk=" + rmk + ", inptDt=" + inptDt + ", inptId=" + inptId + ", inptNm=" + inptNm
				+ ", updtDt=" + updtDt + ", updtId=" + updtId + ", updtNm=" + updtNm + ", rowId=" + rowId + "]";
	}
}
