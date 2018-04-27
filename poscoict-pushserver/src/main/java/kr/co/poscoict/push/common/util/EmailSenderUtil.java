package kr.co.poscoict.push.common.util;

import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import kr.co.poscoict.push.email.model.Email;
import kr.co.poscoict.push.email.model.FileInfo;
import kr.co.poscoict.push.email.service.FileService;

/**
 * 이메일 발송 util
 * @author Sangjun, Park
 *
 */
@Component
public class EmailSenderUtil {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 메일발송
	 * @param email
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public void sendEmail(Email email) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = this.javaMailSender.createMimeMessage();
		message.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
		message.addHeader("format", "flowed");
		message.addHeader("Content-Transfer-Encoding", "8bit");
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(StringUtils.toStringArray(email.getTo()));
		helper.setFrom(email.getFrom());
		helper.setSubject(email.getSubject());
		helper.setText(email.getMessage(), email.isHtml());
		
		if(!CollectionUtils.isEmpty(email.getAttachFileIdList())) {
			List<FileInfo> attachFileList = this.fileService.getFiles(email.getAttachFileIdList());
			for(FileInfo file : attachFileList) {
				helper.addAttachment(
						MimeUtility.encodeText(file.getOriginalFileNm(), "UTF-8", "B"), 
						Paths.get(file.getUploadPath(), file.getUploadFileNm()).toFile());
			}
		}
		this.javaMailSender.send(message);
	}
}
