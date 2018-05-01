/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.model.FileInfo;
import com.skplanet.impay.ccs.common.model.PdfModel;
import com.skplanet.impay.ccs.common.service.mapper.FileMapper;
import com.skplanet.impay.ccs.common.util.DateUtil;
import com.skplanet.impay.ccs.framework.exception.InvalidParameterException;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.common.service.MessageByLocaleServiceImpl;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * File Service
 * @author Sangjun, Park
 *
 */
@Service
public class FileService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private MessageByLocaleServiceImpl message;
	
	@Value("${upload.path}")
	private String uploadBasicPath;
	
	@Value("${file.allow.extention}")
	private String[] fileAllowExtension;
	
	@Value("${upload.path.pdf}")
	private String PDF_UPLOAD_PATH;
	
	@Value("${file.path.css}")
	private String CSS_FILE_PATH;
	
	@Value("${file.path.font}")
	private String FONT_FILE_PATH;
	
	@Value("${file.path.image}")
	private String IMG_FILE_PATH;

	/**
	 * 파일 업로드
	 * @param multipartHttpServletRequest MultipartHttpServletRequest
	 * @param fileInfo 파일 정보
	 * @return RestResult<List<FileInfo>>
	 */
	public RestResult<List<FileInfo>> upload(MultipartHttpServletRequest multipartHttpServletRequest, FileInfo fileInfo){
		RestResult<List<FileInfo>> result = new RestResult<>();
		List<FileInfo> uploadFileList = new ArrayList<>();
		
		try{
			Path newUploadPath = Paths.get(this.uploadBasicPath, fileInfo.getSubUploadPath());
			Files.createDirectories(newUploadPath);
			if(!Files.isDirectory(newUploadPath, LinkOption.NOFOLLOW_LINKS)){
	    		throw new FileNotFoundException("Unable to create directory " + newUploadPath.toString());
	    	}
			fileInfo.setUpldFilePath(newUploadPath.toString());
			
			Iterator<String> uploadFileNames = ((MultipartRequest) multipartHttpServletRequest).getFileNames();
			// 파일 미선택 처리
			if(!uploadFileNames.hasNext()){
				result.setSuccess(false);
				result.setMessage(message.getMessage("common.alert.NoSelectFile"));
				return result;
			}
			
			while(uploadFileNames.hasNext()){
				MultipartFile multipartFile = ((MultipartRequest) multipartHttpServletRequest).getFile(uploadFileNames.next());
				String originalFileName = multipartFile.getOriginalFilename().trim();
				
				if(StringUtils.isNotEmpty(originalFileName)){
					String fileName = getFileName(originalFileName);
		    		String extensionName = getExtension(originalFileName).toLowerCase();
		    		// 파일 확장자 검사
		    		if(Arrays.asList(this.fileAllowExtension).contains(extensionName)){
		    			StringBuilder newFileName = new StringBuilder();
		    			newFileName.append(fileName).append(".").append(extensionName);
			    		
			    		// 업로드 파일 정보 생성
			    		FileInfo newFileInfo = new FileInfo();
			    		newFileInfo.setSubUploadPath(fileInfo.getSubUploadPath());
			    		newFileInfo.setUpldFilePath(newUploadPath.toString());
			    		newFileInfo.setFileNm(newFileName.toString());	// 원본파일명
			    		
			    		if(Files.exists(Paths.get(newUploadPath.toString(), newFileName.toString()), LinkOption.NOFOLLOW_LINKS)){
			    			newFileName.setLength(0);
			    			newFileName.append(fileName).append("_").append(System.currentTimeMillis()).append(".").append(extensionName);
			    		}
			    		newFileInfo.setUpldFileNm(newFileName.toString());	// 업로드 파일명
			    		uploadFileList.add(newFileInfo);
			    		
			    		multipartFile.transferTo(Paths.get(newUploadPath.toString(), newFileName.toString()).toFile());
		    		} else {
		    			// 미허용 확장자 파일 처리
		    			if(!uploadFileList.isEmpty()){
		    				for(FileInfo deleteFileInfo : uploadFileList){
		    					Path deleteFilePath = Paths.get(deleteFileInfo.getUpldFilePath(), deleteFileInfo.getFileNm());
		    					if(Files.exists(deleteFilePath, LinkOption.NOFOLLOW_LINKS)){
		    						Files.delete(deleteFilePath);
		    					}
		    				}
		    			}
						result.setSuccess(false);
						result.setMessage(message.getMessage("common.alert.NotAllowFileExt"));
						return result;
					}
				}
			}
		} catch(IOException e){
			logger.error("FileService.upload() :: FAIL - {}", e.toString(), e);
			result.setSuccess(false);
			result.setMessage(message.getMessage("common.error.message"));
			return result;
		}
		result.setSuccess(true);
		result.setMessage(message.getMessage("common.alert.uploadOk"));
		result.setResult(uploadFileList);
		logger.info("FileService.upload() :: SUCCESS");
		return result;
	}
	
	/**
     * 파일명만 구하기
     * @param fullFileName 파일명
     * @return String
     */
    private String getFileName(String fullFileName) {
    	return fullFileName.substring(fullFileName.lastIndexOf(File.separator) + 1 , fullFileName.lastIndexOf("."));
    }
    
    /**
     * 확장자 구하기
     * @param fileName 파일명
     * @return String
     */
    private String getExtension(String fileName) {
    	return fileName.substring(fileName.lastIndexOf(".") + 1 , fileName.length());
    }
    
    /**
     * PDF 파일 생성
     * @param pdfModel PDF 생성을 위한 정보
     * @return FileInfo : 오류발생시 null
     * @throws DocumentException 
     * @throws IOException 
     */
    public FileInfo createPdf(PdfModel pdfModel){
    	logger.info("FileService.createPdf() :: PDF Build START");
		
    	FileInfo fileInfo = new FileInfo();
    	Document document = null;
    	try{
	    	// 업로드 파일정보 설정
	    	fileInfo.setUpldFilePath(PDF_UPLOAD_PATH);
	    	if(StringUtils.isEmpty(pdfModel.getDownloadFileName())){
	    		throw new InvalidParameterException("Downloaded File name is null.");
	    	}
	    	if(pdfModel.getDownloadFileName().lastIndexOf(".") > 0 && StringUtils.equals(".pdf", this.getExtension(pdfModel.getDownloadFileName()).toLowerCase())){
	    		fileInfo.setUpldFileNm(this.getFileName(pdfModel.getDownloadFileName()) + "_" + DateUtil.getCurrentDateTime() + ".pdf");
	    	} else {
	    		fileInfo.setUpldFileNm(pdfModel.getDownloadFileName() + "_" + DateUtil.getCurrentDateTime() + ".pdf");
	    	}
	    	fileInfo.setFileNm(fileInfo.getUpldFileNm());
	
	    	// 디렉토리 생성
	    	Path pdfFile = Paths.get(uploadBasicPath, fileInfo.getUpldFilePath(), fileInfo.getUpldFileNm());
	    	Files.createDirectories(pdfFile.getParent());
	    	if(!Files.isDirectory(pdfFile.getParent(), LinkOption.NOFOLLOW_LINKS)){
	    		throw new FileNotFoundException("Unable to create directory " + pdfFile.getParent().toString());
	    	}
    	
    		// 설정 적용 및 메타데이터 생성
    		document = new Document(pdfModel.getPageSize(), pdfModel.getMarginLeft(), pdfModel.getMarginRight(), 
    				pdfModel.getMarginTop(), pdfModel.getMarginBottom());
    		PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(pdfFile));
    		writer.setViewerPreferences(PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage);
    		
    		// PDF Document 생성
    		writer.setInitialLeading(12.5f);
    		document.open();
    		this.buildPdfDocument(pdfModel, document, writer);
    		document.close();
    		logger.info("FileService.createPdf() :: PDF Build COMPLETE");
    	} catch(RuntimeException re){
    		logger.error("FileService.createPdf() :: PDF Build ERROR - {}", re.getMessage(), re);
    		fileInfo = null;
    	} catch(Exception e){
    		logger.error("FileService.createPdf() :: PDF Build ERROR - {}", e.getMessage(), e);
    		fileInfo = null;
    	} finally {
    		if(document != null && document.isOpen()){
    			document.close();
    		}
    	}
		
    	return fileInfo;
    }
    
    /**
     * PDF Document 데이터 생성
     * @param pdfModel PDF 생성을 위한 정보
     * @param document Document
     * @param writer PdfWriter
     * @throws IOException
     * @throws DocumentException 
     * @throws TemplateException 
     */
    private void buildPdfDocument(PdfModel pdfModel, Document document, PdfWriter writer) throws IOException, DocumentException, TemplateException{
    	if(pdfModel.isUseHtml()){
    		// CSS
    		CSSResolver cssResolver = null;
    		if(StringUtils.isNotEmpty(pdfModel.getCssFileName())){
    			cssResolver = new StyleAttrCSSResolver();
        		CssFile cssFile = XMLWorkerHelper.getCSS(Files.newInputStream(Paths.get(context.getRealPath(CSS_FILE_PATH), pdfModel.getCssFileName())));
        		cssResolver.addCss(cssFile);
    		} else {
    			cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
    		}

    		// HTML and Font
    		XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
    		fontProvider.register(Paths.get(context.getRealPath(FONT_FILE_PATH), "NanumGothic-Regular.ttf").toString(), "NanumGothic");
    		CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
    		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
    		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
    		
    		// Pipeline
    		PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
    		HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
    		CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
    		
    		XMLWorker worker = new XMLWorker(css, true);
    		XMLParser parser = new XMLParser(worker, Charset.forName("UTF-8"));
    		
    		pdfModel.put("imgPath", context.getRealPath(IMG_FILE_PATH));
    		pdfModel.put("cssPath", context.getRealPath(CSS_FILE_PATH));
    		
    		Template template = freeMarkerConfigurer.createConfiguration().getTemplate(pdfModel.getViewFileName());
    		String htmlStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, pdfModel.getHtmlDataMap())
    						.replaceAll("\"", "\\\"").replaceAll("\'", "\\\'");
    		
    		logger.debug("createPdf() : HTML STRING\n{}", htmlStr.toString());
    		
    		parser.parse(new ByteArrayInputStream(htmlStr.toString().getBytes("UTF-8")));
    		
    	} else {
    		String textStr = pdfModel.getTextStr();
        	logger.debug("createPdf() : TEXT STRING - {}", textStr);
        	// 폰트 설정
        	Font font = new Font(BaseFont.createFont(Paths.get(context.getRealPath(FONT_FILE_PATH), "NanumGothic-Regular.ttf").toString(), 
        			BaseFont.IDENTITY_H, BaseFont.EMBEDDED), pdfModel.getFontSize());
        	// Document 에 추가
    		document.add(new Paragraph(textStr, font));
    	}
    }
    
    /**
     * 파일 첨부 테이블에 insert
     * @param fileInfo 파일 정보
     * @return int
     */
    public int insertAttchFile(FileInfo fileInfo){
    	CustomUserDetails userInfo = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	fileInfo.setRegr(userInfo.getUserSeq());
    	return this.fileMapper.insertAttchFile(fileInfo);
    }
    
}
