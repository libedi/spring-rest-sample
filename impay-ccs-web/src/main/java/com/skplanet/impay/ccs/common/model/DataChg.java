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
 * 데이터 변경이력
 * @author jisu park
 *
 */
public class DataChg {
	
	private int idx = 0;
	private String chgDd = "";					//변경일자
	private int chgHisSeq = 0;					//변경이력 순번
	private String tblNm = "";					//테이블명
	private String chgClf = "";					//변경구분
	private String chgKey1 = "";				//변경 KEY1
	private String chgKey2 = "";				//변경 KEY2
	private String chgKey3 = "";				//변경 KEY3
	private String chgKey4 = "";				//변경 KEY4
	private String chgKey5 = "";				//변경 KEY5
	private String chgDtl = "";					//변경내역
	private String regDt = "";					//등록일시
	private String regPrgm = "";				//등록 프로그램
	private String chgr = "";
	
	public String getChgDd() {
		return chgDd;
	}
	public void setChgDd(String chgDd) {
		this.chgDd = chgDd;
	}
	public String getTblNm() {
		return tblNm;
	}
	public void setTblNm(String tblNm) {
		this.tblNm = tblNm;
	}
	public String getChgClf() {
		return chgClf;
	}
	public void setChgClf(String chgClf) {
		this.chgClf = chgClf;
	}
	public String getChgKey1() {
		return chgKey1;
	}
	public void setChgKey1(String chgKey1) {
		this.chgKey1 = chgKey1;
	}
	public String getChgKey2() {
		return chgKey2;
	}
	public void setChgKey2(String chgKey2) {
		this.chgKey2 = chgKey2;
	}
	public String getChgKey3() {
		return chgKey3;
	}
	public void setChgKey3(String chgKey3) {
		this.chgKey3 = chgKey3;
	}
	public String getChgKey4() {
		return chgKey4;
	}
	public void setChgKey4(String chgKey4) {
		this.chgKey4 = chgKey4;
	}
	public String getChgKey5() {
		return chgKey5;
	}
	public void setChgKey5(String chgKey5) {
		this.chgKey5 = chgKey5;
	}
	public String getChgDtl() {
		return chgDtl;
	}
	public void setChgDtl(String chgDtl) {
		this.chgDtl = chgDtl;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegPrgm() {
		return regPrgm;
	}
	public void setRegPrgm(String regPrgm) {
		this.regPrgm = regPrgm;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getChgr() {
		return chgr;
	}
	public void setChgr(String chgr) {
		this.chgr = chgr;
	}
	public int getChgHisSeq() {
		return chgHisSeq;
	}
	public void setChgHisSeq(int chgHisSeq) {
		this.chgHisSeq = chgHisSeq;
	}
	
}
