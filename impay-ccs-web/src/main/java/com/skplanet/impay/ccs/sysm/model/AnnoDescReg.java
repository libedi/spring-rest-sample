/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.sysm.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.impay.ccs.framework.model.ValidationMarker;

/**
 * 고객센터 공지사항 Model 
 * @author Sangjun, Park
 *
 */
public class AnnoDescReg extends ValidationMarker {
	@NotNull(groups = {Create.class, Update.class})
	@NotEmpty(groups = {Create.class, Update.class})
	private Long clctBordSeq;		// 고객센터 게시판 순서
	private String bordClfCd;		// 게시판 구분 코드
	private String bordClfCdNm;		// 게시판 구분 코드명
	private String titl;			// 제목
	private String titlBldYn;		// 제목 강조 여부(Y,N)
	private String ctnt;			// 내용
	private Long attchFileSeq;		// 첨부파일SEQ
	private String useYn;			// 사용여부
	private String regDt;			// 등록일자
	private String regr;			// 등록자ID
	private String regrNm;			// 등록자명
	private String lastChgDt;		// 최종변경일자
	private String lastChgr;		// 최종변경자ID
	private String lastChgrNm;		// 최종변경자
	private String newYn;			// 신규여부
	
	public String getTitlBldYn() {
		return titlBldYn;
	}
	public void setTitlBldYn(String titlBldYn) {
		this.titlBldYn = titlBldYn;
	}
	public Long getClctBordSeq() {
		return clctBordSeq;
	}
	public void setClctBordSeq(Long clctBordSeq) {
		this.clctBordSeq = clctBordSeq;
	}
	public String getBordClfCd() {
		return bordClfCd;
	}
	public void setBordClfCd(String bordClfCd) {
		this.bordClfCd = bordClfCd;
	}
	public String getBordClfCdNm() {
		return bordClfCdNm;
	}
	public void setBordClfCdNm(String bordClfCdNm) {
		this.bordClfCdNm = bordClfCdNm;
	}
	public String getTitl() {
		return titl;
	}
	public void setTitl(String titl) {
		this.titl = titl;
	}
	public String getCtnt() {
		return ctnt;
	}
	public void setCtnt(String ctnt) {
		this.ctnt = ctnt;
	}
	public Long getAttchFileSeq() {
		return attchFileSeq;
	}
	public void setAttchFileSeq(Long attchFileSeq) {
		this.attchFileSeq = attchFileSeq;
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
	public String getRegrNm() {
		return regrNm;
	}
	public void setRegrNm(String regrNm) {
		this.regrNm = regrNm;
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
	public String getLastChgrNm() {
		return lastChgrNm;
	}
	public void setLastChgrNm(String lastChgrNm) {
		this.lastChgrNm = lastChgrNm;
	}
	public String getNewYn() {
		return newYn;
	}
	public void setNewYn(String newYn) {
		this.newYn = newYn;
	}
	
}
