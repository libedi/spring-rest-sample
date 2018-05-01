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

import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

/**
 * PDF 생성을 위한 Model
 * @author Sangjun, Park
 *
 */
public class PdfModel {
	/** 생성될 파일명 */
	private String downloadFileName;
	/**
	 * 템플릿 파일명 : XHTML 형식에 맞게 작성되어야 함.
	 */
	private String viewFileName;
	/** HTML 템플릿에 매핑될 데이터 맵 */
	private Map<String, Object> htmlDataMap;
	/** 텍스트모드 사용시 PDF에 쓰여질 문자열 */
	private String textStr;
	
	/** 페이지크기 : PageSize 상수사용 */
	private Rectangle pageSize;
	private float marginLeft;
	private float marginRight;
	private float marginTop;
	private float marginBottom;
	private float fontSize;
	
	/** HTML 템플릿 사용유무 (default : true) */
	private boolean useHtml;
	
	/** CSS 파일명 */
	private String cssFileName;
	
	/**
	 * Constructor
	 */
	public PdfModel() {
		htmlDataMap = new HashMap<String, Object>();
		this.pageSize = PageSize.A4;
		this.marginLeft = 25f;
		this.marginRight = 25f;
		this.marginTop = 50f;
		this.marginBottom = 50f;
		this.useHtml = true;
		this.fontSize = Font.DEFAULTSIZE;
	}
	
	/**
	 * Constructor
	 * @param viewFileName HTML 템플릿으로 사용될 파일명
	 */
	public PdfModel(String viewFileName) {
		this.viewFileName = viewFileName;
		htmlDataMap = new HashMap<String, Object>();
		this.pageSize = PageSize.A4;
		this.marginLeft = 25f;
		this.marginRight = 25f;
		this.marginTop = 50f;
		this.marginBottom = 50f;
		this.useHtml = true;
		this.fontSize = Font.DEFAULTSIZE;
	}
	
	/**
	 * Constructor
	 * @param downloadFileName 다운로드될 파일명(확장자 제외)
	 * @param viewFileName HTML 템플릿으로 사용될 파일명
	 */
	public PdfModel(String downloadFileName, String viewFileName) {
		this.downloadFileName = downloadFileName;
		this.viewFileName = viewFileName;
		htmlDataMap = new HashMap<String, Object>();
		this.pageSize = PageSize.A4;
		this.marginLeft = 25f;
		this.marginRight = 25f;
		this.marginTop = 50f;
		this.marginBottom = 50f;
		this.useHtml = true;
		this.fontSize = Font.DEFAULTSIZE;
	}
	
	/**
	 * Constructor
	 * @param downloadFileName 다운로드될 파일명(확장자 제외)
	 * @param viewFileName HTML 템플릿으로 사용될 파일명
	 * @param dataMap 템플릿 파일에 매핑될 데이터맵
	 */
	public PdfModel(String downloadFileName, String viewFileName, Map<String, Object> dataMap) {
		this.downloadFileName = downloadFileName;
		this.viewFileName = viewFileName;
		this.htmlDataMap = dataMap;
		this.pageSize = PageSize.A4;
		this.marginLeft = 25f;
		this.marginRight = 25f;
		this.marginTop = 50f;
		this.marginBottom = 50f;
		this.useHtml = true;
		this.fontSize = Font.DEFAULTSIZE;
	}
	
	/**
	 * Constructor
	 * @param downloadFileName 다운로드될 파일명(확장자 제외)
	 * @param viewFileName HTML 템플릿으로 사용될 파일명
	 * @param dataMap 템플릿 파일에 매핑될 데이터맵
	 * @param pageSize 페이지크기
	 * @param marginLeft 왼쪽여백
	 * @param marginRight 오른쪽여백
	 * @param marginTop 상단여백
	 * @param marginBottom 하단여백
	 * @param isHtml HTML 템플릿 사용유무
	 */
	public PdfModel(String downloadFileName, String viewFileName, Map<String, Object> dataMap, 
			Rectangle pageSize, float marginLeft, float marginRight, float marginTop, float marginBottom, boolean useHtml, float fontSize) {
		this.downloadFileName = downloadFileName;
		this.viewFileName = viewFileName;
		this.htmlDataMap = dataMap;
		this.pageSize = pageSize;
		this.marginLeft = marginLeft;
		this.marginRight = marginRight;
		this.marginTop = marginTop;
		this.marginBottom = marginBottom;
		this.useHtml = useHtml;
		this.fontSize = fontSize;
	}
	
	/**
	 * HTML 템플릿에 매핑될 데이터 삽입
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value){
		this.getHtmlDataMap().put(key, value);
	}
	
	/**
	 * PDF Model 생성
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getModel(){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("model", this);
		return model;
	}
	
	/* setter,getter */
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	public String getViewFileName() {
		return viewFileName;
	}
	public void setViewFileName(String viewFileName) {
		this.viewFileName = viewFileName;
	}
	public Map<String, Object> getHtmlDataMap() {
		return htmlDataMap;
	}
	public void setHtmlDataMap(Map<String, Object> htmlDataMap) {
		this.htmlDataMap = htmlDataMap;
	}
	public String getTextStr() {
		return textStr;
	}
	public void setTextStr(String textStr) {
		this.textStr = textStr;
	}
	public Rectangle getPageSize() {
		return pageSize;
	}
	public void setPageSize(Rectangle pageSize) {
		this.pageSize = pageSize;
	}
	public float getMarginLeft() {
		return marginLeft;
	}
	public void setMarginLeft(float marginLeft) {
		this.marginLeft = marginLeft;
	}
	public float getMarginRight() {
		return marginRight;
	}
	public void setMarginRight(float marginRight) {
		this.marginRight = marginRight;
	}
	public float getMarginTop() {
		return marginTop;
	}
	public void setMarginTop(float marginTop) {
		this.marginTop = marginTop;
	}
	public float getMarginBottom() {
		return marginBottom;
	}
	public void setMarginBottom(float marginBottom) {
		this.marginBottom = marginBottom;
	}
	public float getFontSize() {
		return fontSize;
	}
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}
	public boolean isUseHtml() {
		return useHtml;
	}
	public void setUseHtml(boolean useHtml) {
		this.useHtml = useHtml;
	}
	public String getCssFileName() {
		return cssFileName;
	}
	public void setCssFileName(String cssFileName) {
		this.cssFileName = cssFileName;
	}
}
