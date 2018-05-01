/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.common.sms.model;

public class SendSmsRequest {

	public SendSmsRequest() {

	}

	public SendSmsRequest(String reqId, String receiver, String payload) {
		this.reqId = reqId;
		this.receiver = receiver;
		this.payload = payload;
	}

	private String reqId;
	private String receiver;
	private String payload;

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Override
	public String toString() {
		return "SendSmsRequest{" +
		"reqId='" + reqId + '\'' +
		", receiver='" + receiver + '\'' +
		", payload='" + payload + '\'' +
		'}';
	}
}
