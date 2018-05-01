/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.rpm.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.impay.ccs.framework.model.ValidationMarker;

/**
 * 이관 접수 Model
 * @author Sangjun, Park
 *
 */
public class TjurUploadRcpt extends ValidationMarker{
	@NotNull(groups = Update.class)
	@NotEmpty(groups = Update.class)
	private String dataRegNo;
	private String dataClfCd;
	private String pgClfCd;
	private String upldTyp;
	private String upldDt;
	private String commcClfCd;
	private String payMphnId;
	private String aprvDd;
	private String payAmt;
	private String cpNm;
	private String godsNm;
	private String cnslClfCd;
	private String cnslClfCdNm;
	private String cnslTypCd;
	private String cnslTypCdNm;
	private String rcptMthdCd;
	private String rcptMthdCdNm;
	private String cnslCtnt;
	private String procCtnt;
	private String procStat;
	private String procRslt;
	private String regr;
	private String lastChgDt;
	private String lastChgr;
	
	public String getDataRegNo() {
		return dataRegNo;
	}
	public void setDataRegNo(String dataRegNo) {
		this.dataRegNo = dataRegNo;
	}
	public String getDataClfCd() {
		return dataClfCd;
	}
	public void setDataClfCd(String dataClfCd) {
		this.dataClfCd = dataClfCd;
	}
	public String getPgClfCd() {
		return pgClfCd;
	}
	public void setPgClfCd(String pgClfCd) {
		this.pgClfCd = pgClfCd;
	}
	public String getUpldTyp() {
		return upldTyp;
	}
	public void setUpldTyp(String upldTyp) {
		this.upldTyp = upldTyp;
	}
	public String getUpldDt() {
		return upldDt;
	}
	public void setUpldDt(String upldDt) {
		this.upldDt = upldDt;
	}
	public String getCommcClfCd() {
		return commcClfCd;
	}
	public void setCommcClfCd(String commcClfCd) {
		this.commcClfCd = commcClfCd;
	}
	public String getPayMphnId() {
		return payMphnId;
	}
	public void setPayMphnId(String payMphnId) {
		this.payMphnId = payMphnId;
	}
	public String getAprvDd() {
		return aprvDd;
	}
	public void setAprvDd(String aprvDd) {
		this.aprvDd = aprvDd;
	}
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getCpNm() {
		return cpNm;
	}
	public void setCpNm(String cpNm) {
		this.cpNm = cpNm;
	}
	public String getGodsNm() {
		return godsNm;
	}
	public void setGodsNm(String godsNm) {
		this.godsNm = godsNm;
	}
	public String getCnslClfCd() {
		return cnslClfCd;
	}
	public void setCnslClfCd(String cnslClfCd) {
		this.cnslClfCd = cnslClfCd;
	}
	public String getCnslClfCdNm() {
		return cnslClfCdNm;
	}
	public void setCnslClfCdNm(String cnslClfCdNm) {
		this.cnslClfCdNm = cnslClfCdNm;
	}
	public String getCnslTypCd() {
		return cnslTypCd;
	}
	public void setCnslTypCd(String cnslTypCd) {
		this.cnslTypCd = cnslTypCd;
	}
	public String getCnslTypCdNm() {
		return cnslTypCdNm;
	}
	public void setCnslTypCdNm(String cnslTypCdNm) {
		this.cnslTypCdNm = cnslTypCdNm;
	}
	public String getRcptMthdCd() {
		return rcptMthdCd;
	}
	public void setRcptMthdCd(String rcptMthdCd) {
		this.rcptMthdCd = rcptMthdCd;
	}
	public String getRcptMthdCdNm() {
		return rcptMthdCdNm;
	}
	public void setRcptMthdCdNm(String rcptMthdCdNm) {
		this.rcptMthdCdNm = rcptMthdCdNm;
	}
	public String getCnslCtnt() {
		return cnslCtnt;
	}
	public void setCnslCtnt(String cnslCtnt) {
		this.cnslCtnt = cnslCtnt;
	}
	public String getProcCtnt() {
		return procCtnt;
	}
	public void setProcCtnt(String procCtnt) {
		this.procCtnt = procCtnt;
	}
	public String getProcStat() {
		return procStat;
	}
	public void setProcStat(String procStat) {
		this.procStat = procStat;
	}
	public String getProcRslt() {
		return procRslt;
	}
	public void setProcRslt(String procRslt) {
		this.procRslt = procRslt;
	}
	public String getRegr() {
		return regr;
	}
	public void setRegr(String regr) {
		this.regr = regr;
	}
	public String getLastChgDt() {
		return lastChgDt;
	}
	public void setLastChgDt(String lastChgDt) {
		this.lastChgDt = lastChgDt;
	}
	public String getLastChgr() {
		return lastChgr;
	}
	public void setLastChgr(String lastChgr) {
		this.lastChgr = lastChgr;
	}
	
}
