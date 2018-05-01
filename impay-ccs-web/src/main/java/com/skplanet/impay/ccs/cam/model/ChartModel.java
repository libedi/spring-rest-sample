/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.cam.model;


/**
 * 클레임 분석관리 Chart Model
 * @author Junehee,Jang
 *
 */
public class ChartModel {
	
	private String chartDates;
	
	// TAB-1 상담유형
	private int tktk01;
	private int tktk02;
	private int tktk03;
	private int tktk04;
	
	// TAB-2 이벤트
	private int tkev01;
	private int tkev02;
	private int tkev03;
	
	
	// TAP-3 이관구분
	private int cpCnt;
	private int atipbzCnt;
	private int atipitCnt;
	private int atipopCnt;
	
	
	public int getCpCnt() {
		return cpCnt;
	}
	public void setCpCnt(int cpCnt) {
		this.cpCnt = cpCnt;
	}
	public int getAtipbzCnt() {
		return atipbzCnt;
	}
	public void setAtipbzCnt(int atipbzCnt) {
		this.atipbzCnt = atipbzCnt;
	}
	public int getAtipitCnt() {
		return atipitCnt;
	}
	public void setAtipitCnt(int atipitCnt) {
		this.atipitCnt = atipitCnt;
	}
	public int getAtipopCnt() {
		return atipopCnt;
	}
	public void setAtipopCnt(int atipopCnt) {
		this.atipopCnt = atipopCnt;
	}
	public String getChartDates() {
		return chartDates;
	}
	public void setChartDates(String chartDates) {
		this.chartDates = chartDates;
	}
	public int getTktk01() {
		return tktk01;
	}
	public void setTktk01(int tktk01) {
		this.tktk01 = tktk01;
	}
	public int getTktk02() {
		return tktk02;
	}
	public void setTktk02(int tktk02) {
		this.tktk02 = tktk02;
	}
	public int getTktk03() {
		return tktk03;
	}
	public void setTktk03(int tktk03) {
		this.tktk03 = tktk03;
	}
	public int getTktk04() {
		return tktk04;
	}
	public void setTktk04(int tktk04) {
		this.tktk04 = tktk04;
	}
	public int getTkev01() {
		return tkev01;
	}
	public void setTkev01(int tkev01) {
		this.tkev01 = tkev01;
	}
	public int getTkev02() {
		return tkev02;
	}
	public void setTkev02(int tkev02) {
		this.tkev02 = tkev02;
	}
	public int getTkev03() {
		return tkev03;
	}
	public void setTkev03(int tkev03) {
		this.tkev03 = tkev03;
	}
}
