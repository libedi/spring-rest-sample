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

/**
 * @author PP58701
 *
 */
public class MenuModel implements Serializable{
	
	private static final long serialVersionUID = -3370705791667652047L;
	
	/** 로그인ID */
	private String userId;
	/** 슈퍼어드민 여부 */
	private String mstYn;
	/** 메뉴 ID : 대분류(2) + 중분류(2) + 메뉴(2) */
	private String mnuId;
	/** 자식 메뉴 ID */
	private String uprMnuId;
	/** 메뉴 레벨 ( 대메뉴:1, 중메뉴:2, 메뉴:3 ) */
	private String mnuLv;
	/** 메뉴 실행 여부(Y,N) */
	private String mnuExeYn;
	/** 메뉴명 */
	private String mnuNm;
	/** 조회 기능 여부 (Y,N) */
	private String inqryFuncYn;
	/** 생성 기능 여부 (Y,N) */
	private String crtFuncYn;
	/** 저장 기능 여부 (Y,N) */
	private String savFuncYn;
	/** 삭제 기능 여부 (Y,N) */
	private String delFuncYn;
	/** 인쇄 기능 여부 (Y,N) */
	private String prtFuncYn;
	/** 업로드 기능 여부 (Y,N) */
	private String upldFuncYn;
	/** 다운로드 기능 여부 (Y,N) */
	private String dnFuncYn;
	/** 기타 기능1 여부 (Y,N) */
	private String etcFunc1Yn;
	/** 기타 기능2 여부 (Y,N) */
	private String etcFunc2Yn;
	/** 기타 기능3 여부 (Y,N) */
	private String etcFunc3Yn;
	/** 사용 여부 (Y,N) */
	private String useYn;
	/** 최종 변경 일시 */
	private String lastChgDt;
	/** 최종 변경자 */
	private String lastChgr;
	/** 메뉴 URL */
	private String mnuUrl;
	/** 메뉴 정렬 */
	private String sortSeq;
	/** 메뉴 Path */
	private String path;
	/** 하위 메뉴건수 */
	private String leaf;
	/** 사이트 분류 */
	private String siteClfFlg;
	/** 1:IDMS,2:CP */
	private String searchTp;
	
	public String getMnuId() {
		return mnuId;
	}
	public void setMnuId(String mnuId) {
		this.mnuId = mnuId;
	}
	public String getUprMnuId() {
		return uprMnuId;
	}
	public void setUprMnuId(String uprMnuId) {
		this.uprMnuId = uprMnuId;
	}
	public String getMnuLv() {
		return mnuLv;
	}
	public void setMnuLv(String mnuLv) {
		this.mnuLv = mnuLv;
	}
	public String getMnuExeYn() {
		return mnuExeYn;
	}
	public void setMnuExeYn(String mnuExeYn) {
		this.mnuExeYn = mnuExeYn;
	}
	public String getMnuNm() {
		return mnuNm;
	}
	public void setMnuNm(String mnuNm) {
		this.mnuNm = mnuNm;
	}
	public String getInqryFuncYn() {
		return inqryFuncYn;
	}
	public void setInqryFuncYn(String inqryFuncYn) {
		this.inqryFuncYn = inqryFuncYn;
	}
	public String getCrtFuncYn() {
		return crtFuncYn;
	}
	public void setCrtFuncYn(String crtFuncYn) {
		this.crtFuncYn = crtFuncYn;
	}
	public String getSavFuncYn() {
		return savFuncYn;
	}
	public void setSavFuncYn(String savFuncYn) {
		this.savFuncYn = savFuncYn;
	}
	public String getDelFuncYn() {
		return delFuncYn;
	}
	public void setDelFuncYn(String delFuncYn) {
		this.delFuncYn = delFuncYn;
	}
	public String getPrtFuncYn() {
		return prtFuncYn;
	}
	public void setPrtFuncYn(String prtFuncYn) {
		this.prtFuncYn = prtFuncYn;
	}
	public String getUpldFuncYn() {
		return upldFuncYn;
	}
	public void setUpldFuncYn(String upldFuncYn) {
		this.upldFuncYn = upldFuncYn;
	}
	public String getDnFuncYn() {
		return dnFuncYn;
	}
	public void setDnFuncYn(String dnFuncYn) {
		this.dnFuncYn = dnFuncYn;
	}
	public String getEtcFunc1Yn() {
		return etcFunc1Yn;
	}
	public void setEtcFunc1Yn(String etcFunc1Yn) {
		this.etcFunc1Yn = etcFunc1Yn;
	}
	public String getEtcFunc2Yn() {
		return etcFunc2Yn;
	}
	public void setEtcFunc2Yn(String etcFunc2Yn) {
		this.etcFunc2Yn = etcFunc2Yn;
	}
	public String getEtcFunc3Yn() {
		return etcFunc3Yn;
	}
	public void setEtcFunc3Yn(String etcFunc3Yn) {
		this.etcFunc3Yn = etcFunc3Yn;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
	public String getMnuUrl() {
		return mnuUrl;
	}
	public void setMnuUrl(String mnuUrl) {
		this.mnuUrl = mnuUrl;
	}
	public String getSortSeq() {
		return sortSeq;
	}
	public void setSortSeq(String sortSeq) {
		this.sortSeq = sortSeq;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getLeaf() {
		return leaf;
	}
	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
	public String getSiteClfFlg() {
		return siteClfFlg;
	}
	public void setSiteClfFlg(String siteClfFlg) {
		this.siteClfFlg = siteClfFlg;
	}
	public String getSearchTp() {
		return searchTp;
	}
	public void setSearchTp(String searchTp) {
		this.searchTp = searchTp;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMstYn() {
		return mstYn;
	}
	public void setMstYn(String mstYn) {
		this.mstYn = mstYn;
	}	
}
