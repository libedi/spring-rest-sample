/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.skplanet.impay.ccs.common.model.FileInfo;
import com.skplanet.impay.ccs.common.model.PdfModel;
import com.skplanet.impay.ccs.common.model.RestResult;
import com.skplanet.impay.ccs.common.service.FileService;
import com.skplanet.impay.ccs.common.util.StringUtil;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;

@Controller
@RequestMapping("/file")
public class FileController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	private final String DEFAULT_MIME_TYPE = "application/octet-stream";
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ServletContext servletContext;
	
	@Value("${download.path}")
	private String downloadPath;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	/**
	 * 파일 업로드
	 * @param multipartHttpServletRequest MultipartHttpServletRequest
	 * @param fileInfo 파일 정보
	 * @return RestResult<List<FileInfo>>
	 */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody RestResult<List<FileInfo>> upload(MultipartHttpServletRequest multipartHttpServletRequest, FileInfo fileInfo){
    	return fileService.upload(multipartHttpServletRequest, fileInfo);
    }
    
    /**
     * 파일 다운로드
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param fileInfo 파일 정보
     * @param userInfo 사용자 정보
     * @throws IOException
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void download(HttpServletRequest request, HttpServletResponse response , FileInfo fileInfo,
    		@AuthenticationPrincipal CustomUserDetails userInfo) throws IOException{
    	// 로그인 사용자 아닌 경우 파일 다운로드 제한
    	if(StringUtils.isEmpty(userInfo.getUserSeq())){
    		throw new UsernameNotFoundException("Unable to download file.");
    	}

    	StringBuilder filePath = new StringBuilder();
    	String downloadType = fileInfo.getDownloadType();
    	if(StringUtils.isNotEmpty(downloadType)) {
    		downloadType = downloadType.replaceAll("\"", "").trim();
    	}
    	
    	if(StringUtils.equals("sample", downloadType)) {
    		filePath.append(servletContext.getRealPath("/")).append(downloadPath);
    	} else {
    		filePath.append(this.uploadPath).append(fileInfo.getUpldFilePath());
    	}
    	
    	Path downloadFile = Paths.get(StringUtil.filterInvalidPath(filePath.toString()), StringUtil.filterInvalidPath(fileInfo.getUpldFileNm()));
    	String downloadFileName = "";
    	
    	// 크로스 브라우징을 위한 파일명 인코딩
    	String browser = request.getHeader("User-Agent").toLowerCase();    	
    	if(browser.contains("msie") || browser.contains("trident") || browser.contains("edge/")){
    		// MS IE, Edge
    		downloadFileName = URLEncoder.encode(fileInfo.getUpldFileNm().replaceAll("\"", "").trim(),"UTF-8").replaceAll("\\+", "\\ ");
    	} else {
    		// FF, Opera, Safari, Chrome
    		downloadFileName = new String(fileInfo.getUpldFileNm().replaceAll("\"", "").trim().getBytes("UTF-8"), "ISO-8859-1");
    	}
    	
      	String mimeType = servletContext.getMimeType(downloadFile.toString());
    	if(mimeType == null){
    		response.setContentType(DEFAULT_MIME_TYPE);
    	} else {
    		response.setContentType(mimeType);
    	}
    	response.setContentLength((int) downloadFile.toFile().length());
    	response.setHeader("Content-Disposition",  "attachment; filename=\"" + downloadFileName + "\";");
    	response.setHeader("Content-Transfer-Encoding", "binary");
		FileCopyUtils.copy(Files.newInputStream(downloadFile), response.getOutputStream());

    	logger.debug("FileController :: DOWNLOAD FILE PATH : {}", downloadFile.toString());
    }
    
    /**
     * PDF 파일 생성
     * @param pdfModel PDF 생성을 위한 정보
     * @return RestResult<FileInfo>
     */
    @RequestMapping(value = "/createPdf", method = RequestMethod.POST)
    public @ResponseBody RestResult<FileInfo> createPdf(PdfModel pdfModel){
    	RestResult<FileInfo> result = new RestResult<FileInfo>();
    	FileInfo fileInfo = this.fileService.createPdf(pdfModel);
    	if(fileInfo != null){
    		result.setSuccess(true);
    		result.setResult(fileInfo);
    	} else {
    		result.setSuccess(false);
    		result.setResult(null);
    	}
    	return result;
    }
}