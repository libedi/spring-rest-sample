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

import com.skplanet.impay.ccs.framework.model.ValidationMarker;

/**
 * 법인정보 Model
 * @author Sangjun, Park
 *
 */
public class EntpModel extends ValidationMarker {
	private String entpId;
	private String entpNm;
	private String uprEntpId;
	private String sclClfCd;
	private String prsdtNm;
	private String repSiteNm;
	private String repUrl;
	private String repTelNo;
	private String faxNo;
	private String bizrRegNo;
	private String zipCd;
	private String addr;
	private String addrDtl;
	private String bizCndi;
	private String itm;
	private String slsChrgr;
	private String frstCntrcDd;
	private String adjLmtAmt;
	private String trdYn;
	private String trdFnshDd;
	private String pgClfFlg;
	private String regDt;
	private String regr;
	private String lastChgDt;
	private String lastChgr;
	private String bizCtgrUprCd;
	private String bizCtgrLwrCd;
	private String agntDfltFee;
	
	public String getEntpId() {
		return entpId;
	}
	public void setEntpId(String entpId) {
		this.entpId = entpId;
	}
	public String getEntpNm() {
		return entpNm;
	}
	public void setEntpNm(String entpNm) {
		this.entpNm = entpNm;
	}
	public String getUprEntpId() {
		return uprEntpId;
	}
	public void setUprEntpId(String uprEntpId) {
		this.uprEntpId = uprEntpId;
	}
	public String getSclClfCd() {
		return sclClfCd;
	}
	public void setSclClfCd(String sclClfCd) {
		this.sclClfCd = sclClfCd;
	}
	public String getPrsdtNm() {
		return prsdtNm;
	}
	public void setPrsdtNm(String prsdtNm) {
		this.prsdtNm = prsdtNm;
	}
	public String getRepSiteNm() {
		return repSiteNm;
	}
	public void setRepSiteNm(String repSiteNm) {
		this.repSiteNm = repSiteNm;
	}
	public String getRepUrl() {
		return repUrl;
	}
	public void setRepUrl(String repUrl) {
		this.repUrl = repUrl;
	}
	public String getRepTelNo() {
		return repTelNo;
	}
	public void setRepTelNo(String repTelNo) {
		this.repTelNo = repTelNo;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getBizrRegNo() {
		return bizrRegNo;
	}
	public void setBizrRegNo(String bizrRegNo) {
		this.bizrRegNo = bizrRegNo;
	}
	public String getZipCd() {
		return zipCd;
	}
	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAddrDtl() {
		return addrDtl;
	}
	public void setAddrDtl(String addrDtl) {
		this.addrDtl = addrDtl;
	}
	public String getBizCndi() {
		return bizCndi;
	}
	public void setBizCndi(String bizCndi) {
		this.bizCndi = bizCndi;
	}
	public String getItm() {
		return itm;
	}
	public void setItm(String itm) {
		this.itm = itm;
	}
	public String getSlsChrgr() {
		return slsChrgr;
	}
	public void setSlsChrgr(String slsChrgr) {
		this.slsChrgr = slsChrgr;
	}
	public String getFrstCntrcDd() {
		return frstCntrcDd;
	}
	public void setFrstCntrcDd(String frstCntrcDd) {
		this.frstCntrcDd = frstCntrcDd;
	}
	public String getAdjLmtAmt() {
		return adjLmtAmt;
	}
	public void setAdjLmtAmt(String adjLmtAmt) {
		this.adjLmtAmt = adjLmtAmt;
	}
	public String getTrdYn() {
		return trdYn;
	}
	public void setTrdYn(String trdYn) {
		this.trdYn = trdYn;
	}
	public String getTrdFnshDd() {
		return trdFnshDd;
	}
	public void setTrdFnshDd(String trdFnshDd) {
		this.trdFnshDd = trdFnshDd;
	}
	public String getPgClfFlg() {
		return pgClfFlg;
	}
	public void setPgClfFlg(String pgClfFlg) {
		this.pgClfFlg = pgClfFlg;
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
	public String getBizCtgrUprCd() {
		return bizCtgrUprCd;
	}
	public void setBizCtgrUprCd(String bizCtgrUprCd) {
		this.bizCtgrUprCd = bizCtgrUprCd;
	}
	public String getBizCtgrLwrCd() {
		return bizCtgrLwrCd;
	}
	public void setBizCtgrLwrCd(String bizCtgrLwrCd) {
		this.bizCtgrLwrCd = bizCtgrLwrCd;
	}
	public String getAgntDfltFee() {
		return agntDfltFee;
	}
	public void setAgntDfltFee(String agntDfltFee) {
		this.agntDfltFee = agntDfltFee;
	}
	
}
