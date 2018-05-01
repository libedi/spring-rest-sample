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
 * 코드
 * @author jisu park
 *
 */
public class Code {
	
	private String cd;			//코드
	private String uprCd;		//상위코드
	private int lv;				//레벨
	private String lClf;		//대분류
	private String mClf;		//중분류
	private String sClf;		//소분류
	private String cdNm;		//코드명
	private String prepWord1;	//예비문구
	private String useYn;
	
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getUprCd() {
		return uprCd;
	}
	public void setUprCd(String uprCd) {
		this.uprCd = uprCd;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public String getlClf() {
		return lClf;
	}
	public void setlClf(String lClf) {
		this.lClf = lClf;
	}
	public String getsClf() {
		return sClf;
	}
	public void setsClf(String sClf) {
		this.sClf = sClf;
	}
	public String getmClf() {
		return mClf;
	}
	public void setmClf(String mClf) {
		this.mClf = mClf;
	}
	public String getCdNm() {
		return cdNm;
	}
	public void setCdNm(String cdNm) {
		this.cdNm = cdNm;
	}
	public String getPrepWord1() {
		return prepWord1;
	}
	public void setPrepWord1(String prepWord1) {
		this.prepWord1 = prepWord1;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}	
}