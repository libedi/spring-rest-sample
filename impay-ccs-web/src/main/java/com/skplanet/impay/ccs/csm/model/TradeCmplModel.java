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

import java.util.List;

import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.model.PayMphnInfo;

/**
 * 거래완료 Model
 * @author Sangjun, Park
 *
 */
public class TradeCmplModel {
	private PayMphnInfo payMphnInfo;
	private List<PayMphnInfo> mphnList;
	private Page<TradeModel> tradeList;
	private Page<CnslDetail> cnslDetailList;

	public PayMphnInfo getPayMphnInfo() {
		return payMphnInfo;
	}
	public void setPayMphnInfo(PayMphnInfo payMphnInfo) {
		this.payMphnInfo = payMphnInfo;
	}
	public List<PayMphnInfo> getMphnList() {
		return mphnList;
	}
	public void setMphnList(List<PayMphnInfo> mphnList) {
		this.mphnList = mphnList;
	}
	
	public Page<TradeModel> getTradeList() {
		return tradeList;
	}
	public void setTradeList(Page<TradeModel> tradeList) {
		this.tradeList = tradeList;
	}
	public Page<CnslDetail> getCnslDetailList() {
		return cnslDetailList;
	}
	public void setCnslDetailList(Page<CnslDetail> cnslDetailList) {
		this.cnslDetailList = cnslDetailList;
	}
}
