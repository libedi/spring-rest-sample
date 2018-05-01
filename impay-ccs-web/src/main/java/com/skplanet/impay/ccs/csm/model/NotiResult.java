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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.impay.ccs.framework.model.ValidationMarker;

/**
 * 결과통보 Model
 * @author Sangjun, Park
 *
 */
public class NotiResult extends ValidationMarker {
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String rcptNo;			// 접수번호
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String rsltNotiMthd;	// 결과통보방법 (S : SMS, M : 이메일)
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String procCtnt;		// 처리방법
	
	@Pattern(regexp = "\\b\\d{3}?\\d{3,4}?\\d{4}\\b")
	private String mphnNo;			// 고객전화번호
	
	@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
	private String email;			// 고객 이메일
	private String title;			// 이메일제목
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String testYn;			// 테스트 발송여부
	
	public String getRcptNo() {
		return rcptNo;
	}
	public void setRcptNo(String rcptNo) {
		this.rcptNo = rcptNo;
	}
	public String getRsltNotiMthd() {
		return rsltNotiMthd;
	}
	public void setRsltNotiMthd(String rsltNotiMthd) {
		this.rsltNotiMthd = rsltNotiMthd;
	}
	public String getProcCtnt() {
		return procCtnt;
	}
	public void setProcCtnt(String procCtnt) {
		this.procCtnt = procCtnt;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTestYn() {
		return testYn;
	}
	public void setTestYn(String testYn) {
		this.testYn = testYn;
	}
	
}
