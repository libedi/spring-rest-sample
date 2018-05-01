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
 * 발송 결과 Model
 * @author Sangjun, Park
 *
 */
public class SendResult {
	private String sndReqSeq;
	private String notiTypId;
	private String cntcSeq;
	private String sndProcYn;
	private String lastChgDt;
	private String lastChgr;
	public String getSndReqSeq() {
		return sndReqSeq;
	}
	public void setSndReqSeq(String sndReqSeq) {
		this.sndReqSeq = sndReqSeq;
	}
	public String getNotiTypId() {
		return notiTypId;
	}
	public void setNotiTypId(String notiTypId) {
		this.notiTypId = notiTypId;
	}
	public String getCntcSeq() {
		return cntcSeq;
	}
	public void setCntcSeq(String cntcSeq) {
		this.cntcSeq = cntcSeq;
	}
	public String getSndProcYn() {
		return sndProcYn;
	}
	public void setSndProcYn(String sndProcYn) {
		this.sndProcYn = sndProcYn;
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
