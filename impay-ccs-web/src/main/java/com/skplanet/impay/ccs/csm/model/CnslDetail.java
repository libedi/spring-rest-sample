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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.framework.model.ValidationMarker;

/**
 * 상담 내역 Model
 * @author Sangjun, Park
 *
 */
public class CnslDetail extends ValidationMarker{

	@NotNull(groups = Update.class)
	@NotEmpty(groups = Update.class)
	private String rcptNo;			// 접수번호
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	@Size(min=6, max=6)
	private String cnslTypCd;		// 상담유형코드
	
	private String cnslTypNm;		// 상담유형코드명
	
	private String rcptClf;			// 접수구분(이용자:P, 가맹점:C)
	
	private String payMphnId;		// 결제폰ID
	
	private String entpId;			// 법인ID
	
	private String cpCd;			// 가맹점코드
	
	private String commcClf;		// 이통사 구분
	
	private String commcClfNm;		// 이통사명
	
	private String payCndiCd;		// 결제조건코드(일반, 회수, 자동, 직접, 배치)
	
	private String payCndiNm;		// 결제조건명
	
	private String trdDd;			// 거래일자
	
	private String cnclDd;			// 취소일자
	
	private String cnclYn;			// 취소여부
	
	@NotNull(groups = Create.class)
	@Size(min=6, max=6)
	private String cnslClfUprCd;	// 상담 구분 상위 코드
	
	private String cnslClfUprNm;	// 상담 구분 상위 코드명
	
	private String cnslClfLwrCd;	// 상담 구분 하위 코드
	
	private String cnslClfLwrNm;	// 상담 구분 하위 코드명
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	@Size(min=6, max=6)
	private String rcptMthdCd;		// 접수방법코드
	
	private String rcptMthdNm;		// 접수방법명
	
	@NotNull(groups = Create.class)
	@NotEmpty(groups = Create.class)
	private String cnslCtnt;		// 상담내용
	
	private String procCtnt;		// 처리내용
	
	private String procYn;			// 처리여부
	
	private String procYnNm;		// 처리여부명
	
	private String procDd;			// 처리일자
	
	private String procDt;			// 처리일자
	
	private String pybkRcptNo;		// 환불접수번호
	
	private String wlRegNo;			// WL 등록번호
	
	private String dataRegNo;		// 자료등록번호
	
	private String regDt;			// 접수일자
	
	private String regr;			// 상담자
	
	private String lastChgDt;		// 최종변경일시
	
	private String lastChgr;		// 변경자
	
	private String chgYn;			// 변경여부
	
	@NotNull(groups = Create.class)
	@Size(min=6, max=6)
	private String cnslEvntCd;		// 이벤트 코드
	
	private String cnslEvntNm;		// 이벤트명
	
	private String custTypFlg;		// 고객유형
	
	private String custTypFlgNm;	// 고객유형

	private Integer seq;			// 상담이력 SEQ
	private String rnum;			// 순서
	private String cnslTjurCtnt;	// 이관내용
	private String cnslTjurCd;		// 이관구분코드
	private String cnslTjurNm;		// 이관구분명
	private String tjurProcDt;		// 이관처리일
	private String rsltNotiDt;		// 결과통보일시
	private String rsltNotiMthd;	// 결과통보방법 (S : SMS, M : 이메일)
	private String paySvcNm;		// 가맹점명
	private String sndReqSeq;		// 발송요청번호
	private String mphnNo;			// 휴대폰번호
	private String payrSeq;			// 고객번호
	private TradeModel tradeModel;	// 상담건 거래정보
	private Page<CnslDetail> cnslChgList;	// 상담내역 변경이력 리스트
	private String tjurYn;			// 이관여부
	
	public String getRcptNo() {
		return rcptNo;
	}
	public void setRcptNo(String rcptNo) {
		this.rcptNo = rcptNo;
	}
	public String getCnslTypCd() {
		return cnslTypCd;
	}
	public void setCnslTypCd(String cnslTypCd) {
		this.cnslTypCd = cnslTypCd;
	}
	public String getCnslTypNm() {
		return cnslTypNm;
	}
	public void setCnslTypNm(String cnslTypNm) {
		this.cnslTypNm = cnslTypNm;
	}
	public String getRcptClf() {
		return rcptClf;
	}
	public void setRcptClf(String rcptClf) {
		this.rcptClf = rcptClf;
	}
	public String getPayMphnId() {
		return payMphnId;
	}
	public void setPayMphnId(String payMphnId) {
		this.payMphnId = payMphnId;
	}
	public String getEntpId() {
		return entpId;
	}
	public void setEntpId(String entpId) {
		this.entpId = entpId;
	}
	public String getCpCd() {
		return cpCd;
	}
	public void setCpCd(String cpCd) {
		this.cpCd = cpCd;
	}
	public String getCommcClf() {
		return commcClf;
	}
	public void setCommcClf(String commcClf) {
		this.commcClf = commcClf;
	}
	public String getCommcClfNm() {
		return commcClfNm;
	}
	public void setCommcClfNm(String commcClfNm) {
		this.commcClfNm = commcClfNm;
	}
	public String getPayCndiCd() {
		return payCndiCd;
	}
	public void setPayCndiCd(String payCndiCd) {
		this.payCndiCd = payCndiCd;
	}
	public String getPayCndiNm() {
		return payCndiNm;
	}
	public void setPayCndiNm(String payCndiNm) {
		this.payCndiNm = payCndiNm;
	}
	public String getTrdDd() {
		return trdDd;
	}
	public void setTrdDd(String trdDd) {
		this.trdDd = trdDd;
	}
	public String getCnclDd() {
		return cnclDd;
	}
	public void setCnclDd(String cnclDd) {
		this.cnclDd = cnclDd;
	}
	public String getCnclYn() {
		return cnclYn;
	}
	public void setCnclYn(String cnclYn) {
		this.cnclYn = cnclYn;
	}
	public String getCnslClfUprCd() {
		return cnslClfUprCd;
	}
	public void setCnslClfUprCd(String cnslClfUprCd) {
		this.cnslClfUprCd = cnslClfUprCd;
	}
	public String getCnslClfUprNm() {
		return cnslClfUprNm;
	}
	public void setCnslClfUprNm(String cnslClfUprNm) {
		this.cnslClfUprNm = cnslClfUprNm;
	}
	public String getCnslClfLwrCd() {
		return cnslClfLwrCd;
	}
	public void setCnslClfLwrCd(String cnslClfLwrCd) {
		this.cnslClfLwrCd = cnslClfLwrCd;
	}
	public String getCnslClfLwrNm() {
		return cnslClfLwrNm;
	}
	public void setCnslClfLwrNm(String cnslClfLwrNm) {
		this.cnslClfLwrNm = cnslClfLwrNm;
	}
	public String getRcptMthdCd() {
		return rcptMthdCd;
	}
	public void setRcptMthdCd(String rcptMthdCd) {
		this.rcptMthdCd = rcptMthdCd;
	}
	public String getRcptMthdNm() {
		return rcptMthdNm;
	}
	public void setRcptMthdNm(String rcptMthdNm) {
		this.rcptMthdNm = rcptMthdNm;
	}
	public String getCnslCtnt() {
		return cnslCtnt;
	}
	public void setCnslCtnt(String cnslCtnt) {
		this.cnslCtnt = cnslCtnt;
	}
	public String getProcCtnt() {
		return procCtnt;
	}
	public void setProcCtnt(String procCtnt) {
		this.procCtnt = procCtnt;
	}
	public String getProcYn() {
		return procYn;
	}
	public void setProcYn(String procYn) {
		this.procYn = procYn;
	}
	public String getProcYnNm() {
		return procYnNm;
	}
	public void setProcYnNm(String procYnNm) {
		this.procYnNm = procYnNm;
	}
	public String getProcDd() {
		return procDd;
	}
	public void setProcDd(String procDd) {
		this.procDd = procDd;
	}
	public String getProcDt() {
		return procDt;
	}
	public void setProcDt(String procDt) {
		this.procDt = procDt;
	}
	public String getPybkRcptNo() {
		return pybkRcptNo;
	}
	public void setPybkRcptNo(String pybkRcptNo) {
		this.pybkRcptNo = pybkRcptNo;
	}
	public String getWlRegNo() {
		return wlRegNo;
	}
	public void setWlRegNo(String wlRegNo) {
		this.wlRegNo = wlRegNo;
	}
	public String getDataRegNo() {
		return dataRegNo;
	}
	public void setDataRegNo(String dataRegNo) {
		this.dataRegNo = dataRegNo;
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
	public String getChgYn() {
		return chgYn;
	}
	public void setChgYn(String chgYn) {
		this.chgYn = chgYn;
	}
	public String getCnslEvntCd() {
		return cnslEvntCd;
	}
	public void setCnslEvntCd(String cnslEvntCd) {
		this.cnslEvntCd = cnslEvntCd;
	}
	public String getCnslEvntNm() {
		return cnslEvntNm;
	}
	public void setCnslEvntNm(String cnslEvntNm) {
		this.cnslEvntNm = cnslEvntNm;
	}
	public String getCustTypFlg() {
		return custTypFlg;
	}
	public void setCustTypFlg(String custTypFlg) {
		this.custTypFlg = custTypFlg;
	}
	public String getCustTypFlgNm() {
		return custTypFlgNm;
	}
	public void setCustTypFlgNm(String custTypFlgNm) {
		this.custTypFlgNm = custTypFlgNm;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getCnslTjurCtnt() {
		return cnslTjurCtnt;
	}
	public void setCnslTjurCtnt(String cnslTjurCtnt) {
		this.cnslTjurCtnt = cnslTjurCtnt;
	}
	public String getCnslTjurCd() {
		return cnslTjurCd;
	}
	public void setCnslTjurCd(String cnslTjurCd) {
		this.cnslTjurCd = cnslTjurCd;
	}
	public String getCnslTjurNm() {
		return cnslTjurNm;
	}
	public void setCnslTjurNm(String cnslTjurNm) {
		this.cnslTjurNm = cnslTjurNm;
	}
	public String getTjurProcDt() {
		return tjurProcDt;
	}
	public void setTjurProcDt(String tjurProcDt) {
		this.tjurProcDt = tjurProcDt;
	}
	public String getRsltNotiDt() {
		return rsltNotiDt;
	}
	public void setRsltNotiDt(String rsltNotiDt) {
		this.rsltNotiDt = rsltNotiDt;
	}
	public String getRsltNotiMthd() {
		return rsltNotiMthd;
	}
	public void setRsltNotiMthd(String rsltNotiMthd) {
		this.rsltNotiMthd = rsltNotiMthd;
	}
	public String getPaySvcNm() {
		return paySvcNm;
	}
	public void setPaySvcNm(String paySvcNm) {
		this.paySvcNm = paySvcNm;
	}
	public String getSndReqSeq() {
		return sndReqSeq;
	}
	public void setSndReqSeq(String sndReqSeq) {
		this.sndReqSeq = sndReqSeq;
	}
	public String getMphnNo() {
		return mphnNo;
	}
	public void setMphnNo(String mphnNo) {
		this.mphnNo = mphnNo;
	}
	public String getPayrSeq() {
		return payrSeq;
	}
	public void setPayrSeq(String payrSeq) {
		this.payrSeq = payrSeq;
	}
	public TradeModel getTradeModel() {
		return tradeModel;
	}
	public void setTradeModel(TradeModel tradeModel) {
		this.tradeModel = tradeModel;
	}
	public Page<CnslDetail> getCnslChgList() {
		return cnslChgList;
	}
	public void setCnslChgList(Page<CnslDetail> cnslChgList) {
		this.cnslChgList = cnslChgList;
	}
	public String getTjurYn() {
		return tjurYn;
	}
	public void setTjurYn(String tjurYn) {
		this.tjurYn = tjurYn;
	}
	
}
