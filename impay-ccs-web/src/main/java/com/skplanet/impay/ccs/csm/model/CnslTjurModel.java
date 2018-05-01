/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.csm.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.impay.ccs.framework.model.ValidationMarker;

/**
 * 이관접수 Model
 * @author Sangjun, Park
 *
 */
public class CnslTjurModel extends ValidationMarker {
	@NotNull(groups = {Create.class, Update.class})
	@NotEmpty(groups = {Create.class, Update.class})
	private String rcptNo;			// 접수번호
	private int seq;				// 이력 시퀀스
	private String entpId;			// 법인ID
	private String cpCd;			// 가맹점코드
	private String deptCd;			// 부서코드
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String tjurClfFlg;		// 이관 구분 플래그 (가맹점 : C, 사업부 : B)
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String cnslTjurCtnt;	// 상담 이관 내용
	
	@NotNull(groups = {Create.class, Update.class})
	@NotEmpty(groups = {Create.class, Update.class})
	private String procTjurCtnt;	// 처리 이관 내용
	private String tjurProcYn;		// 이관 처리 여부 (Y,N)
	private String procDt;			// 처리 일시
	private String tjurProc;		// 이관 처리자
	private String regDt;			// 등록 일시
	private String regr;			// 등록자
	
	public String getRcptNo() {
		return rcptNo;
	}
	public void setRcptNo(String rcptNo) {
		this.rcptNo = rcptNo;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getEntpId() {
		return entpId;
	}
	public void setEntpId(String entpId) {
		this.entpId = entpId;
	}
	public String getCpCd() {
		return cpCd;
	}
	public void setCpCd(String cpCd) {
		this.cpCd = cpCd;
	}
	public String getDeptCd() {
		return deptCd;
	}
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}
	public String getTjurClfFlg() {
		return tjurClfFlg;
	}
	public void setTjurClfFlg(String tjurClfFlg) {
		this.tjurClfFlg = tjurClfFlg;
	}
	public String getCnslTjurCtnt() {
		return cnslTjurCtnt;
	}
	public void setCnslTjurCtnt(String cnslTjurCtnt) {
		this.cnslTjurCtnt = cnslTjurCtnt;
	}
	public String getProcTjurCtnt() {
		return procTjurCtnt;
	}
	public void setProcTjurCtnt(String procTjurCtnt) {
		this.procTjurCtnt = procTjurCtnt;
	}
	public String getTjurProcYn() {
		return tjurProcYn;
	}
	public void setTjurProcYn(String tjurProcYn) {
		this.tjurProcYn = tjurProcYn;
	}
	public String getProcDt() {
		return procDt;
	}
	public void setProcDt(String procDt) {
		this.procDt = procDt;
	}
	public String getTjurProc() {
		return tjurProc;
	}
	public void setTjurProc(String tjurProc) {
		this.tjurProc = tjurProc;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegr() {
		return regr;
	}
	public void setRegr(String regr) {
		this.regr = regr;
	}
	
}
