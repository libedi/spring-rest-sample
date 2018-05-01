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

import java.util.List;

/**
 * 상담 시나리오 Model
 * @author Sangjun, Park
 *
 */
public class CnslScrpt {
	private String cnslClfUprCd;
	private String cnslClfUprCdNm;
	private String cnslClfLwrCd;
	private String cnslClfLwrCdNm;
	private String cnslCrspScrptCtnt;
	private String cnslCrspScrptQstn;
	
	private List<CnslScrpt> cnslScrptList;
	
	public String getCnslClfUprCd() {
		return cnslClfUprCd;
	}
	public void setCnslClfUprCd(String cnslClfUprCd) {
		this.cnslClfUprCd = cnslClfUprCd;
	}
	public String getCnslClfUprCdNm() {
		return cnslClfUprCdNm;
	}
	public void setCnslClfUprCdNm(String cnslClfUprCdNm) {
		this.cnslClfUprCdNm = cnslClfUprCdNm;
	}
	public String getCnslClfLwrCd() {
		return cnslClfLwrCd;
	}
	public void setCnslClfLwrCd(String cnslClfLwrCd) {
		this.cnslClfLwrCd = cnslClfLwrCd;
	}
	public String getCnslClfLwrCdNm() {
		return cnslClfLwrCdNm;
	}
	public void setCnslClfLwrCdNm(String cnslClfLwrCdNm) {
		this.cnslClfLwrCdNm = cnslClfLwrCdNm;
	}
	public String getCnslCrspScrptCtnt() {
		return cnslCrspScrptCtnt;
	}
	public void setCnslCrspScrptCtnt(String cnslCrspScrptCtnt) {
		this.cnslCrspScrptCtnt = cnslCrspScrptCtnt;
	}
	public String getCnslCrspScrptQstn() {
		return cnslCrspScrptQstn;
	}
	public void setCnslCrspScrptQstn(String cnslCrspScrptQstn) {
		this.cnslCrspScrptQstn = cnslCrspScrptQstn;
	}
	public List<CnslScrpt> getCnslScrptList() {
		return cnslScrptList;
	}
	public void setCnslScrptList(List<CnslScrpt> cnslScrptList) {
		this.cnslScrptList = cnslScrptList;
	}
	
}
