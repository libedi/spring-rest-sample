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

import java.util.List;

/**
 * 통보 유형 Model
 * @author Sangjun, Park
 *
 */
public class NotiTypeModel {
	/** 통보유형ID */
	private String notiTypId;
	/** 통보유형명 */
	private String notiTypNm;
	/** 통보유형코드 */
	private String notiTypCd;
	/** 통보대상플래그 */
	private String notiTrgtFlg;
	/** 발송채널플래그 */
	private String sndChnlFlg;
	/** 사용여부 */
	private String useYn;
	/** 최종변경일시 */
	private String lastChgDt;
	/** 최종변경자 */
	private String lastChgr;
	/** 수신그룹 연락처정보리스트 */
	private List<CntcInfo> cntcInfoList;
	
	public String getNotiTypId() {
		return notiTypId;
	}
	public void setNotiTypId(String notiTypId) {
		this.notiTypId = notiTypId;
	}
	public String getNotiTypNm() {
		return notiTypNm;
	}
	public void setNotiTypNm(String notiTypNm) {
		this.notiTypNm = notiTypNm;
	}
	public String getNotiTypCd() {
		return notiTypCd;
	}
	public void setNotiTypCd(String notiTypCd) {
		this.notiTypCd = notiTypCd;
	}
	public String getNotiTrgtFlg() {
		return notiTrgtFlg;
	}
	public void setNotiTrgtFlg(String notiTrgtFlg) {
		this.notiTrgtFlg = notiTrgtFlg;
	}
	public String getSndChnlFlg() {
		return sndChnlFlg;
	}
	public void setSndChnlFlg(String sndChnlFlg) {
		this.sndChnlFlg = sndChnlFlg;
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
	public List<CntcInfo> getCntcInfoList() {
		return cntcInfoList;
	}
	public void setCntcInfoList(List<CntcInfo> cntcInfoList) {
		this.cntcInfoList = cntcInfoList;
	}
}
