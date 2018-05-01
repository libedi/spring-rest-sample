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

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.impay.ccs.framework.model.ValidationMarker;

/**
 * 사용자 - 연락처
 * @author jisu park
 *
 */
public class CntcInfo extends ValidationMarker implements Serializable{
	
	private static final long serialVersionUID = -8106553544768853063L;
	
	@NotNull(groups = Update.class)
	@NotEmpty(groups = Update.class)
	private int cntcSeq;				// 연락처 순번
	private String entpId;				// 법인코드
	private String chrgWrkClfCd;		// 담당업무 구분코드
	private String chrgrNm;				// 담당자명
	private String deptNm;				// 부서명
	private String telNo;				// 전화번호
	
	@NotNull(groups = Update.class)
	@NotEmpty(groups = Update.class)
	@Pattern(groups = Update.class, regexp = "\\b\\d{3}?\\d{3,4}?\\d{4}\\b")
	private String mphnNo;				// 핸드폰번호
	
	@NotNull(groups = Update.class)
	@NotEmpty(groups = Update.class)
	@Pattern(groups = Update.class, regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
	private String email;				// email
	private String notiTrgtYn;			// 통보 대상 여부
	private String useYn;				// 사용 여부	
    private String regDt;				// 등록 일시 
    private String regr;				// 등록자 
    private String lastChgDt;			// 최종 변경 일시
    private String lastChgr;			// 최종 변경자
    
    private String telNo1;
    private String telNo2;
    private String telNo3;
    private String mphnNo1;
    private String mphnNo2;
    private String mphnNo3;
    
	public int getCntcSeq() {
		return cntcSeq;
	}
	public void setCntcSeq(int cntcSeq) {
		this.cntcSeq = cntcSeq;
	}
	public String getEntpId() {
		return entpId;
	}
	public void setEntpId(String entpId) {
		this.entpId = entpId;
	}
	public String getChrgWrkClfCd() {
		return chrgWrkClfCd;
	}
	public void setChrgWrkClfCd(String chrgWrkClfCd) {
		this.chrgWrkClfCd = chrgWrkClfCd;
	}
	public String getChrgrNm() {
		return chrgrNm;
	}
	public void setChrgrNm(String chrgrNm) {
		this.chrgrNm = chrgrNm;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getMphnNo() {
		return mphnNo;
	}
	public void setMphnNo(String mphnNo) {
		this.mphnNo = mphnNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNotiTrgtYn() {
		return notiTrgtYn;
	}
	public void setNotiTrgtYn(String notiTrgtYn) {
		this.notiTrgtYn = notiTrgtYn;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
	public String getTelNo1() {
		return telNo1;
	}
	public void setTelNo1(String telNo1) {
		this.telNo1 = telNo1;
	}
	public String getTelNo2() {
		return telNo2;
	}
	public void setTelNo2(String telNo2) {
		this.telNo2 = telNo2;
	}
	public String getTelNo3() {
		return telNo3;
	}
	public void setTelNo3(String telNo3) {
		this.telNo3 = telNo3;
	}
	public String getMphnNo1() {
		return mphnNo1;
	}
	public void setMphnNo1(String mphnNo1) {
		this.mphnNo1 = mphnNo1;
	}
	public String getMphnNo2() {
		return mphnNo2;
	}
	public void setMphnNo2(String mphnNo2) {
		this.mphnNo2 = mphnNo2;
	}
	public String getMphnNo3() {
		return mphnNo3;
	}
	public void setMphnNo3(String mphnNo3) {
		this.mphnNo3 = mphnNo3;
	}
	
}