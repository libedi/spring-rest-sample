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

import java.util.List;

import org.springframework.validation.FieldError;

import com.skplanet.impay.ccs.framework.model.FieldErrorResource;

/**
 * Restful 요청에 대한 응답을 표현하기 위한 클래스
 * @author budnamu
 */
public class RestResult<T> {

	private boolean success;							// 성공 실패 여부
	private String message;								// 성공일 때 메시지	
	private List<FieldError> fieldError;
	private List<FieldErrorResource> fieldErrorResource; //에러리소스
	private int insertCnt;								// Update 결과값
	private int updateCnt;								// Update 결과값
	private int deleteCnt;								// Update 결과값 
	private T result;
	
	public RestResult() {}
	
	public RestResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public RestResult(String message) {
		this.message = message;
		this.success = false;
	}
	
	public RestResult(boolean success) {
		this.success = success;
		this.message = null;
	}
	
	public RestResult(int insertCnt, int updateCnt, int deleteCnt) {
		
		this.insertCnt = insertCnt;
		this.updateCnt = updateCnt;
		this.deleteCnt = deleteCnt;
		
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getUpdateCnt() {
		return updateCnt;
	}

	public void setUpdateCnt(int updateCnt) {
		this.updateCnt = updateCnt;
	}

	public int getInsertCnt() {
		return insertCnt;
	}

	public void setInsertCnt(int insertCnt) {
		this.insertCnt = insertCnt;
	}

	public int getDeleteCnt() {
		return deleteCnt;
	}

	public void setDeleteCnt(int deleteCnt) {
		this.deleteCnt = deleteCnt;
	}

	public List<FieldError> getFieldError() {
		return fieldError;
	}

	public void setFieldError(List<FieldError> fieldError) {
		this.fieldError = fieldError;
	}

	public List<FieldErrorResource> getFieldErrorResource() {
		return fieldErrorResource;
	}

	public void setFieldErrorResource(List<FieldErrorResource> fieldErrorResource) {
		this.fieldErrorResource = fieldErrorResource;
	}
	
	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
