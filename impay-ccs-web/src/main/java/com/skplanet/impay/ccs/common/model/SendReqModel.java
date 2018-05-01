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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.skplanet.impay.ccs.framework.model.ValidationMarker;

/**
 * 발송 요청 Model
 * 
 * @author Sangjun, Park
 *
 */
public class SendReqModel extends ValidationMarker {
	/** 발송요청순번 */
	private String sndReqSeq;
	/** 통보유형ID */
	@NotNull(groups = {Create.class,Update.class})
	@Size(groups = {Create.class,Update.class}, min=6, max=6)
	private String notiTypId;
	/** 법인ID */
	private String entpId;
	/** 발송제목 */
	private String sndTitl;
	/** 발송내용 */
	private String sndCtnt;
	/** 발송예약여부 */
	private String sndResvYn;
	/** 발송예약일시 */
	private String sndResvDt;
	/** 발송여부 */
	private String sndYn;
	/** 발송일시 */
	private String sndDt;
	/** 발송취소여부 */
	private String sndCnclYn;
	/** SMS 발송문구 */
	private String smsSndWord;
	/** 등록일시 */
	private String regDt;
	/** 등록자 */
	private String regr; 
	/** 최종변경일시 */
	private String lastChgDt;
	/** 최종변경자 */
	private String lastChgr;
	/** 개별발송여부 - Y : 개별발송(테스트, 고객), N : 그룹발송 */
	private String idvdSndYn;
	/** 개별발송 휴대폰 번호 */
	@Pattern(groups = {Create.class, Update.class}, regexp = "^[0-9]*$")
	private String idvdMphnNo;
	/** 개별발송 이메일 */
	@Pattern(groups = {Create.class, Update.class}, regexp="^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")
	private String idvdEmail;
	/** 첨부파일 순번 */
	private Long attchFileSeq;
	/** 발송 채널(메일:M ,SMS:S, 메일+SMS:A) M*/
	private String sndChnlFlg;
	
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
	public String getEntpId() {
		return entpId;
	}
	public void setEntpId(String entpId) {
		this.entpId = entpId;
	}
	public String getSndTitl() {
		return sndTitl;
	}
	public void setSndTitl(String sndTitl) {
		this.sndTitl = sndTitl;
	}
	public String getSndCtnt() {
		return sndCtnt;
	}
	public void setSndCtnt(String sndCtnt) {
		this.sndCtnt = sndCtnt;
	}
	public String getSndResvYn() {
		return sndResvYn;
	}
	public void setSndResvYn(String sndResvYn) {
		this.sndResvYn = sndResvYn;
	}
	public String getSndResvDt() {
		return sndResvDt;
	}
	public void setSndResvDt(String sndResvDt) {
		this.sndResvDt = sndResvDt;
	}
	public String getSndYn() {
		return sndYn;
	}
	public void setSndYn(String sndYn) {
		this.sndYn = sndYn;
	}
	public String getSndDt() {
		return sndDt;
	}
	public void setSndDt(String sndDt) {
		this.sndDt = sndDt;
	}
	public String getSndCnclYn() {
		return sndCnclYn;
	}
	public void setSndCnclYn(String sndCnclYn) {
		this.sndCnclYn = sndCnclYn;
	}
	public String getSmsSndWord() {
		return smsSndWord;
	}
	public void setSmsSndWord(String smsSndWord) {
		this.smsSndWord = smsSndWord;
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
	public String getIdvdSndYn() {
		return idvdSndYn;
	}
	public void setIdvdSndYn(String idvdSndYn) {
		this.idvdSndYn = idvdSndYn;
	}
	public String getIdvdMphnNo() {
		return idvdMphnNo;
	}
	public void setIdvdMphnNo(String idvdMphnNo) {
		this.idvdMphnNo = idvdMphnNo;
	}
	public String getIdvdEmail() {
		return idvdEmail;
	}
	public void setIdvdEmail(String idvdEmail) {
		this.idvdEmail = idvdEmail;
	}
	public Long getAttchFileSeq() {
		return attchFileSeq;
	}
	public void setAttchFileSeq(Long attchFileSeq) {
		this.attchFileSeq = attchFileSeq;
	}
	public String getSndChnlFlg() {
		return sndChnlFlg;
	}
	public void setSndChnlFlg(String sndChnlFlg) {
		this.sndChnlFlg = sndChnlFlg;
	}
	
}
