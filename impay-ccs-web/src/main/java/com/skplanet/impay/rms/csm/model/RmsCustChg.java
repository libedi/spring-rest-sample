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
public class RmsCustChg {
	
	private String seqNo = ""; //순번
	private String payrSeq = ""; //이용자번호
	private String mphnNo = ""; //휴대전화번호
	private String regClf = ""; //등록구분
	private String custInfoChgCd = ""; //고객정보변경코드
	private String blkRelsClf = ""; //차단해제구분
	private String stDt = ""; //시작일시
	private String edDt = ""; //종료일시
	private String chgRsnCd = ""; //변경사유코드
	private String rmk = ""; //비고
	private String inptDt = ""; //입력일시
	private String inptId = ""; //입력자ID
	
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
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
	public String getRegClf() {
		return regClf;
	}
	public void setRegClf(String regClf) {
		this.regClf = regClf;
	}
	public String getCustInfoChgCd() {
		return custInfoChgCd;
	}
	public void setCustInfoChgCd(String custInfoChgCd) {
		this.custInfoChgCd = custInfoChgCd;
	}
	public String getBlkRelsClf() {
		return blkRelsClf;
	}
	public void setBlkRelsClf(String blkRelsClf) {
		this.blkRelsClf = blkRelsClf;
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
	public String getChgRsnCd() {
		return chgRsnCd;
	}
	public void setChgRsnCd(String chgRsnCd) {
		this.chgRsnCd = chgRsnCd;
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
	@Override
	public String toString() {
		return "RmsCustChg [seqNo=" + seqNo + ", payrSeq=" + payrSeq + ", mphnNo=" + mphnNo + ", regClf=" + regClf
				+ ", custInfoChgCd=" + custInfoChgCd + ", blkRelsClf=" + blkRelsClf + ", stDt=" + stDt + ", edDt="
				+ edDt + ", chgRsnCd=" + chgRsnCd + ", rmk=" + rmk + ", inptDt=" + inptDt + ", inptId=" + inptId + "]";
	}
}
