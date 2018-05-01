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
 * 가맹점목록 Model
 * @author jisu park
 */
public class CpList {

	private int idx;	//쿼리 row idx
	private String cpCd 		= "";	//가맹점 코드
	private String paySvcNm 	= "";	//결제 서비스명
	private String mngEntpNm 	= ""; 	//관리법인 명
	private String cpGrpNm	 	= ""; 	//그룹명
	private String payGodsCd	= "";	//결제상품코드
	private String payGodsNm 	= "";	//결제상품명
	private String openDd 		= "";	//개통일자
	private String adjMthdCd	= "";	//정산방법코드
	private String adjMthdNm 	= "";	//정산 방법 명
	private String statCd       = "";   //가맹점 상태
	private String statNm 		= "";	//상태 코드 명
	private String mngEntpName  = "";
	private String entpId		= "";	//관리법인 ID
	private String entpNm		= "";	//법인명
	
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	public String getMngEntpNm() {
		return mngEntpNm;
	}
	public void setMngEntpNm(String mngEntpNm) {
		this.mngEntpNm = mngEntpNm;
	}
	public String getCpGrpNm() {
		return cpGrpNm;
	}
	public void setCpGrpNm(String cpGrpNm) {
		this.cpGrpNm = cpGrpNm;
	}
	public String getPayGodsNm() {
		return payGodsNm;
	}
	public void setPayGodsNm(String payGodsNm) {
		this.payGodsNm = payGodsNm;
	}
	public String getStatNm() {
		return statNm;
	}
	public void setStatNm(String statNm) {
		this.statNm = statNm;
	}
	public String getOpenDd() {
		return openDd;
	}
	public void setOpenDd(String openDd) {
		this.openDd = openDd;
	}
	public String getAdjMthdNm() {
		return adjMthdNm;
	}
	public void setAdjMthdNm(String adjMthdNm) {
		this.adjMthdNm = adjMthdNm;
	}
	public String getPaySvcNm() {
		return paySvcNm;
	}
	public void setPaySvcNm(String paySvcNm) {
		this.paySvcNm = paySvcNm;
	}
	public String getCpCd() {
		return cpCd;
	}
	public void setCpCd(String cpCd) {
		this.cpCd = cpCd;
	}
	public String getStatCd() {
		return statCd;
	}
	public void setStatCd(String statCd) {
		this.statCd = statCd;
	}

	public String getPayGodsCd() {
		return payGodsCd;
	}

	public void setPayGodsCd(String payGodsCd) {
		this.payGodsCd = payGodsCd;
	}

	public String getAdjMthdCd() {
		return adjMthdCd;
	}

	public void setAdjMthdCd(String adjMthdCd) {
		this.adjMthdCd = adjMthdCd;
	}

	public String getMngEntpName() {
		return mngEntpName;
	}

	public void setMngEntpName(String mngEntpName) {
		this.mngEntpName = mngEntpName;
	}

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
	
}