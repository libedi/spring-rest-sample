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
 * 상담 관리 (RMS) > RM 변경이력 Model
 * @author Sunghee Park
 *
 */
public class RmsRmChangeHis {
	
	private int idx; //번호
	private String payrSeq = ""; //고객번호
	private String mphnNo = ""; //휴대폰번호
	private String custInfoChgCd = ""; //처리업무코드
	private String custInfoChgNm = ""; //처리업무명
	private String blkRelsClf = ""; //처리상태코드
	private String blkRelsClfNm = ""; //처리상태명
	private String chgRsnCd = ""; //변경사유코드
	private String chgRsnNm = ""; //변경사유명
	private String rmk = ""; //비고
	private String inptDt = ""; //입력일시
	private String inptNm = ""; //입력자
	
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
	public String getCustInfoChgCd() {
		return custInfoChgCd;
	}
	public void setCustInfoChgCd(String custInfoChgCd) {
		this.custInfoChgCd = custInfoChgCd;
	}
	public String getCustInfoChgNm() {
		return custInfoChgNm;
	}
	public void setCustInfoChgNm(String custInfoChgNm) {
		this.custInfoChgNm = custInfoChgNm;
	}
	public String getBlkRelsClf() {
		return blkRelsClf;
	}
	public void setBlkRelsClf(String blkRelsClf) {
		this.blkRelsClf = blkRelsClf;
	}
	public String getBlkRelsClfNm() {
		return blkRelsClfNm;
	}
	public void setBlkRelsClfNm(String blkRelsClfNm) {
		this.blkRelsClfNm = blkRelsClfNm;
	}
	public String getChgRsnCd() {
		return chgRsnCd;
	}
	public void setChgRsnCd(String chgRsnCd) {
		this.chgRsnCd = chgRsnCd;
	}
	public String getChgRsnNm() {
		return chgRsnNm;
	}
	public void setChgRsnNm(String chgRsnNm) {
		this.chgRsnNm = chgRsnNm;
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
	public String getInptNm() {
		return inptNm;
	}
	public void setInptNm(String inptNm) {
		this.inptNm = inptNm;
	}
	@Override
	public String toString() {
		return "RmsRmChangeHis [idx=" + idx + ", payrSeq=" + payrSeq + ", mphnNo=" + mphnNo + ", custInfoChgCd="
				+ custInfoChgCd + ", custInfoChgNm=" + custInfoChgNm + ", blkRelsClf=" + blkRelsClf + ", blkRelsClfNm="
				+ blkRelsClfNm + ", chgRsnCd=" + chgRsnCd + ", chgRsnNm=" + chgRsnNm + ", rmk=" + rmk + ", inptDt="
				+ inptDt + ", inptNm=" + inptNm + "]";
	}
}
