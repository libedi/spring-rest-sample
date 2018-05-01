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

import java.io.File;
import java.util.List;

/**
 * email model
 * @author jisu park
 *
 */
public class Email {
	
	/** 메일발송(DB저장시) */
	private String title = "";			//제목
	private String content = "";		//내용
	private String notiTypId = "";		//통보유형id(수신그룹의 의미로 사용)
	private String idvdSendYn = "";		//개별발송여부
	private String receipient = "";		//수신자
	private String sncCtnt = ""; 		//내용(DB)
	private Long attchFileSeq ; 	//파일 Seq
	
	
	/** 메일발송 */
	private String to = "";
	private String from = "";
	private String subject = "";
	private String message = "";
	private List<File> attachFile;
	
	public Long getAttchFileSeq() {
		return attchFileSeq;
	}
	public void setAttchFileSeq(Long attchFileSeq) {
		this.attchFileSeq = attchFileSeq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReceipient() {
		return receipient;
	}
	public void setReceipient(String receipient) {
		this.receipient = receipient;
	}
	public String getNotiTypId() {
		return notiTypId;
	}
	public void setNotiTypId(String notiTypId) {
		this.notiTypId = notiTypId;
	}
	public String getIdvdSendYn() {
		return idvdSendYn;
	}
	public void setIdvdSendYn(String idvdSendYn) {
		this.idvdSendYn = idvdSendYn;
	}
	public String getSncCtnt() {
		return sncCtnt;
	}
	public void setSncCtnt(String sncCtnt) {
		this.sncCtnt = sncCtnt;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<File> getAttachFile() {
		return attachFile;
	}
	public void setAttachFile(List<File> attachFile) {
		this.attachFile = attachFile;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
}
