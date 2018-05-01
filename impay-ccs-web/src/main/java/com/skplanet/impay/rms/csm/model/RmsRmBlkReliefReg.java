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

import com.skplanet.impay.ccs.framework.model.ValidationMarker.Create;
import com.skplanet.impay.ccs.framework.model.ValidationMarker.Update;

/**
 * 상담 관리 (RMS) > 차단/해제 등록 Model
 * @author Sunghee Park
 *
 */
public class RmsRmBlkReliefReg {
	
	private int idx; //번호
	
	@NotNull(groups = {Create.class, Update.class})
	@Pattern(groups = {Create.class, Update.class}, regexp = "^[0-9]*$")
	private String payrSeq = ""; //고객번호
	
	@Pattern(groups = {Create.class, Update.class}, regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$")
	private String mphnNo = ""; //휴대폰번호
	
	@NotNull(groups = {Create.class, Update.class})
	@Size(groups = {Create.class, Update.class}, min=6, max=6)
	private String blkReliefCd = ""; //차단해제코드
	
	private String blkReliefNm = ""; //차단해제명
	
	@NotNull(groups = {Create.class, Update.class})
	@Size(groups = {Create.class, Update.class}, min=1, max=1)
	private String applyStatus = ""; //적용상태
	
	private String applyStatusNm = ""; //적용상태명
	
	private String stDt = ""; //시작일시
	
	@NotNull(groups = {Create.class})
	@Pattern(groups = {Create.class, Update.class}, regexp = "^((19|20|99)\\d\\d)([.])(0[1-9]|1[012])([.])(0[1-9]|[12][0-9]|3[01])$")
	private String edDt = ""; //종료일시
	
	@NotNull(groups = {Create.class, Update.class})
	@Size(groups = {Create.class, Update.class}, min=1, max=1)
	private String reliefClfCd = ""; //구제구분자코드
	
	private String reliefClfNm = ""; //구제구분자명
	
	@NotNull(groups = {Create.class})
	@Size(groups = {Create.class}, min=6, max=6)
	private String relsRsnCd = ""; //해제사유코드
	
	private String relsRsnNm = ""; //해제사유명
	
	@Size(groups = {Create.class, Update.class}, max=255)
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

	public String getMphnNo() {
		return mphnNo;
	}

	public void setMphnNo(String mphnNo) {
		this.mphnNo = mphnNo;
	}

	public String getBlkReliefCd() {
		return blkReliefCd;
	}

	public void setBlkReliefCd(String blkReliefCd) {
		this.blkReliefCd = blkReliefCd;
	}

	public String getBlkReliefNm() {
		return blkReliefNm;
	}

	public void setBlkReliefNm(String blkReliefNm) {
		this.blkReliefNm = blkReliefNm;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getApplyStatusNm() {
		return applyStatusNm;
	}

	public void setApplyStatusNm(String applyStatusNm) {
		this.applyStatusNm = applyStatusNm;
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
	
	public String getReliefClfCd() {
		return reliefClfCd;
	}

	public void setReliefClfCd(String reliefClfCd) {
		this.reliefClfCd = reliefClfCd;
	}

	public String getReliefClfNm() {
		return reliefClfNm;
	}

	public void setReliefClfNm(String reliefClfNm) {
		this.reliefClfNm = reliefClfNm;
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
		return "RmsRmBlkReliefReg [idx=" + idx + ", payrSeq=" + payrSeq + ", mphnNo=" + mphnNo + ", blkReliefCd="
				+ blkReliefCd + ", blkReliefNm=" + blkReliefNm + ", applyStatus=" + applyStatus + ", applyStatusNm="
				+ applyStatusNm + ", stDt=" + stDt + ", edDt=" + edDt + ", reliefClfCd=" + reliefClfCd
				+ ", reliefClfNm=" + reliefClfNm + ", relsRsnCd=" + relsRsnCd + ", relsRsnNm=" + relsRsnNm + ", rmk="
				+ rmk + ", inptDt=" + inptDt + ", inptId=" + inptId + ", inptNm=" + inptNm + ", updtDt=" + updtDt
				+ ", updtId=" + updtId + ", updtNm=" + updtNm + ", rowId=" + rowId + "]";
	}
}
