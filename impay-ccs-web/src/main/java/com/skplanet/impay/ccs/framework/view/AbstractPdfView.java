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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.skplanet.impay.ccs.common.model.PdfModel;
import com.skplanet.impay.ccs.framework.exception.InvalidParameterException;

/**
 * PDF 생성을 위한 AbstractView
 * @author Sangjun, Park
 *
 */
public abstract class AbstractPdfView extends AbstractView {
	
	protected static final Logger logger = LoggerFactory.getLogger(AbstractPdfView.class);
	private final String PDF_CONTENT_TYPE = "application/pdf";
	
	public AbstractPdfView(){
		this.setContentType(PDF_CONTENT_TYPE);
	}
	
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)	throws Exception {
		logger.info("AbstractPdfView.renderMergedOutputModel() :: PDF Build START");
		
		Document document = null;
		try{
			PdfModel pdfModel = (PdfModel) model.get("model");
			// 다운로드 파일명 확인
			if(StringUtils.isEmpty(pdfModel.getDownloadFileName())){
	    		throw new InvalidParameterException("Downloaded File name is null.");
	    	}
			// create temporary byte array output stream
			ByteArrayOutputStream baos = this.createTemporaryOutputStream();
			
			// 설정 적용 및 메타데이터 생성
			document = new Document(pdfModel.getPageSize(), pdfModel.getMarginLeft(), pdfModel.getMarginRight(), 
					pdfModel.getMarginTop(), pdfModel.getMarginBottom());
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			this.prepareWriter(writer);
			this.buildPdfMetadata(model, document, request);
			
			// PDF Document 생성
			writer.setInitialLeading(12.5f);
			document.open();
			this.buildPdfDocument(model, document, writer, request, response);
			document.close();
			
			// HTTP response flush
			if(StringUtils.isNotEmpty(pdfModel.getDownloadFileName())){
				this.writeToResponse(response, baos, this.encodingFilename(request, response, this.makeFileName(pdfModel.getDownloadFileName())));
			} else {
				this.writeToResponse(response, baos);
			}
			logger.info("AbstractPdfView.renderMergedOutputModel() :: PDF Build COMPLETE");
		} catch (Exception e){
			logger.error("AbstractPdfView.renderMergedOutputModel() :: PDF Build ERROR - {}", e.getMessage(), e);
			throw e;
		} finally {
			if(document != null && document.isOpen()){
				document.close();
			}
		}
	}

	protected void prepareWriter(PdfWriter writer) throws DocumentException{
		writer.setViewerPreferences(this.getViewerPreferences());
	}
	
	protected int getViewerPreferences() {
		return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}

	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		
	}
	
	protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * Write the given temporary OutputStream to the HTTP response.
	 * @param response current HTTP response
	 * @param baos the temporary OutputStream to write
	 * @param fileName file name to build
	 * @throws IOException if writing/flushing failed
	 */
	protected void writeToResponse(HttpServletResponse response, ByteArrayOutputStream baos, String fileName) throws IOException {
		if(fileName.lastIndexOf(".") > 0 && StringUtils.equals(".pdf", fileName.substring(fileName.lastIndexOf(".")).toLowerCase())){
			response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
    	} else {
    		response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + ".pdf\"");
    	}
		this.writeToResponse(response, baos);
	}
	
	/**
	 * 파일명 구성 (화면명 + 금일날짜시간)
	 * @param filename
	 * @return String
	 */
	protected String makeFileName(String filename) {
		return new StringBuilder(filename).append("_").append(new SimpleDateFormat("yyyyMMddHHmmss").format((new Date()))).toString();
	}
	
	/**
	 * 브라우저별 파일명 처리
	 * @param request
	 * @param response
	 * @param fileName
	 * @return encoded_filename
	 * @throws UnsupportedEncodingException
	 */
	private String encodingFilename(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException{
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		String encodingFilename = null;
		if (userAgent.contains("msie") || userAgent.contains("trident") || userAgent.contains("edge/")) {
			// MS IE, Edge
			encodingFilename = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "\\ ");
		} else {
			// FF, Opera, Safari, Chrome
			encodingFilename = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		return encodingFilename;
	}

}
