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

import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 정보
 * @author kkangmin
 *
 */
public class FileInfo {
	private String subUploadPath;		// 업로드 될 위치(ex. PayWinReq\가맹점 코드)
	private String upldFilePath;		// 업로드 파일 경로
	private String upldFileNm;			// 업로드 파일명
	private String fileNm;				// 원본 파일명
	private String preViewPath;
	
	private String downloadFileName;	// 다운로드 파일명
	private String downloadType;		// 다운로드 타입	
	private String downloadDir;
	private String sampleDownloadPath;
	
	private Long size;	
	private MultipartFile[] uploadFile = {};
	private String errorCode;			//파일업로드 에러코드
	private String errorMsg;			//파일업로드 에러메시지
	
	private String useYn;
	private String regr;
	private String regDt;
	private Long attchFileSeq;
	private String fileClfCd;
	
	
	public String getSubUploadPath() {
		return subUploadPath;
	}
	public void setSubUploadPath(String subUploadPath) {
		this.subUploadPath = subUploadPath;
	}
	public String getUpldFilePath() {
		return upldFilePath;
	}
	public void setUpldFilePath(String upldFilePath) {
		this.upldFilePath = upldFilePath;
	}
	public String getUpldFileNm() {
		return upldFileNm;
	}
	public void setUpldFileNm(String upldFileNm) {
		this.upldFileNm = upldFileNm;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getPreViewPath() {
		return preViewPath;
	}
	public void setPreViewPath(String preViewPath) {
		this.preViewPath = preViewPath;
	}
	
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	public String getDownloadType() {
		return downloadType;
	}
	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public MultipartFile[] getUploadFile() {
		return (this.uploadFile != null) ? (MultipartFile[]) uploadFile.clone() : null;
	}
	public void setUploadFile(MultipartFile[] uploadFile) {
		this.uploadFile = (MultipartFile[]) uploadFile.clone();
	}
	public String getDownloadDir() {
		return downloadDir;
	}
	public void setDownloadDir(String downloadDir) {
		this.downloadDir = downloadDir;
	}
	
	public String getSampleDownloadPath() {
		return sampleDownloadPath;
	}
	public void setSampleDownloadPath(String sampleDownloadPath) {
		this.sampleDownloadPath = sampleDownloadPath;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getRegr() {
		return regr;
	}
	public void setRegr(String regr) {
		this.regr = regr;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public Long getAttchFileSeq() {
		return attchFileSeq;
	}
	public void setAttchFileSeq(Long attchFileSeq) {
		this.attchFileSeq = attchFileSeq;
	}
	public String getFileClfCd() {
		return fileClfCd;
	}
	public void setFileClfCd(String fileClfCd) {
		this.fileClfCd = fileClfCd;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}