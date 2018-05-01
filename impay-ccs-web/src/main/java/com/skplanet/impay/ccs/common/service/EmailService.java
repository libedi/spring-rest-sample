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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.skplanet.impay.ccs.common.model.Email;
import com.skplanet.impay.ccs.common.model.SendReqModel;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * 이메일 발송 Service
 * @author jisu park
 *
 */
@Service
public class EmailService{
	private static Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private SendReqService sendReqService;
	
	@Autowired 
	private JavaMailSender mailSender;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${ccs.url}")
	private String hostAddress;
	
	@Value("${spring.mail.from}")
	private String fromEmailAddr;
	
	
	/**
	 * email 발송(DB)
	 * @param email email 정보
	 * @return String 발송요청순번
	 */
	public String sendEmailDB(Email email){
		SendReqModel sendReq = new SendReqModel();
		sendReq.setIdvdSndYn(email.getIdvdSendYn());
		sendReq.setIdvdEmail(email.getReceipient());
		sendReq.setNotiTypId(email.getNotiTypId());
		sendReq.setSndTitl(email.getTitle());
		sendReq.setSndCtnt(email.getSncCtnt());
		sendReq.setAttchFileSeq(email.getAttchFileSeq());
		sendReq.setSndChnlFlg("M");
		String sndReqSeq = sendReqService.sendRequest(sendReq);
		logger.info("[이메일을 발송하였습니다.]");
		return sndReqSeq;
	}

	/**
	 * 메일발송 (simple message)
	 * @param to 받는이
	 * @param subject 제목
	 * @param msg 내용
	 * @throws MessagingException
	 */
	public void sendEmail(String to, String subject, String msg) throws MessagingException {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(fromEmailAddr);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
	}
	
	/**
	 * 메일발송(mime message)
	 * @param email email 정보
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException 
	 */
	public void sendEmail(Email email) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.addHeader("Content-type", "text/html; charset=UTF-8");
        message.addHeader("format", "flowed");
        message.addHeader("Content-Transfer-Encoding", "8bit");
        
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email.getTo());
        helper.setFrom(StringUtils.isNotEmpty(email.getFrom()) ? email.getFrom() : this.fromEmailAddr);
        helper.setSubject(email.getSubject());
        helper.setText(email.getMessage(), true);
		
		// 파일첨부
		if(email.getAttachFile() != null && !email.getAttachFile().isEmpty()) {
			for(File attachFile: email.getAttachFile()){
				helper.addAttachment(MimeUtility.encodeText(attachFile.getName(), "UTF-8", "B"), attachFile);
			}
		}
        mailSender.send(message);
	}
	
	/**
	 * 이메일 HTML 문자열 생성
	 * @param content 내용
	 * @return 고객에게 보내는 일반적인 이메일 형식의 HTML 문자열
	 * @throws TemplateException 
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws MalformedTemplateNameException 
	 * @throws TemplateNotFoundException 
	 */
	public String getEmailContentString(String content) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 매핑데이터 설정
		dataMap.put("content", content.replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
		dataMap.put("resources", hostAddress + "/resources");
		
		Template template = freeMarkerConfigurer.createConfiguration().getTemplate("mail_forum.ftl");
		String htmlStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap)
				.replaceAll("\"", "\\\"").replaceAll("\'", "\\\'");
		
		logger.debug("EMAIL HTML CONTENT :\n{}", htmlStr);
		
		return htmlStr;
	}
	
}