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

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.impay.ccs.framework.model.ValidationMarker;

/**
 * SMS 발송 Model
 * @author Sangjun, Park
 *
 */
public class SmsSend extends ValidationMarker {
	/** 제목 */
	private String title;
	/** 휴대폰 번호 */
	@NotEmpty
	@Pattern(regexp="^[0-9]*$")
	private String mphnNo;
	/** 발송내용 */
	private String sndCtnt;
	/** 개별발송여부 */
	private String idvdSendYn;
	/** 통보유형ID */
	@NotEmpty
	@Size(min=6, max=6)
	private String notiTypId;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMphnNo() {
		return mphnNo;
	}
	public void setMphnNo(String mphnNo) {
		this.mphnNo = mphnNo;
	}
	public String getSndCtnt() {
		return sndCtnt;
	}
	public void setSndCtnt(String sndCtnt) {
		this.sndCtnt = sndCtnt;
	}
	public String getIdvdSendYn() {
		return idvdSendYn;
	}
	public void setIdvdSendYn(String idvdSendYn) {
		this.idvdSendYn = idvdSendYn;
	}
	public String getNotiTypId() {
		return notiTypId;
	}
	public void setNotiTypId(String notiTypId) {
		this.notiTypId = notiTypId;
	}
	
}
