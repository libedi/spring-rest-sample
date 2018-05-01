/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.framework.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

public abstract class AbstractExcel2007View extends AbstractView {
	
	protected static final Logger logger = LoggerFactory.getLogger(AbstractExcel2007View.class);

	/** The content type for an Excel response */
	private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";	
	
	/** The extension to look for existing templates */
	private static final String EXTENSION = ".xlsx";
	
	/**	 * Default Constructor.	 * Sets the content type of the view to "application/vnd.ms-excel".	 */	
	public AbstractExcel2007View() {
		setContentType(CONTENT_TYPE);
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@Override
	protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("[AbstractExcel2007View] renderMergedOutputModel() START ");
		
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream baos = createTemporaryOutputStream();
		buildExcelDocument(model, workbook, request, response);
		workbook.write(baos);
		
		String fileName = (String) model.get("excelName");
		if(StringUtils.isNotEmpty(fileName)){
			writeToResponse(response, baos, filenameToResponse(request, response, makeFileName(fileName)));
		} else {
			writeToResponse(response, baos);
		}
		logger.info("[AbstractExcel2007View] renderMergedOutputModel() END ");
	}
	
	protected abstract void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response)	throws Exception;
	
	/**
	 * 파일명 구성 (화면명 + 금일날짜시간)
	 * @param filename
	 * @return String
	 */
	protected String makeFileName(String filename) {
		return new StringBuilder(filename + "_")
                .append(new SimpleDateFormat("yyyyMMddHHmmss").format((new Date())))
                .toString();
	}
	
	/**
	 * 브라우저별 파일명 처리
	 * @param request
	 * @param response
	 * @param fileName
	 * @return encoded_filename
	 * @throws UnsupportedEncodingException
	 */
	private String filenameToResponse(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException{
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		String encodedFilename = null;
		if (userAgent.contains("msie") || userAgent.contains("trident") || userAgent.contains("edge/")) {
			// MS IE, Edge
			encodedFilename = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "\\ ");
		} else {
			// FF, Opera, Safari, Chrome
			encodedFilename = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		return encodedFilename;
	}
	
	protected void writeToResponse(HttpServletResponse response, ByteArrayOutputStream baos, String excelName) throws IOException {
		response.setHeader("Content-Disposition", "attachment; filename=\"" + excelName + EXTENSION + "\"");
		this.writeToResponse(response, baos);
	}	
}