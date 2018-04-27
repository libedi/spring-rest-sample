package kr.co.poscoict.push.email.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.poscoict.push.common.exception.EmailFailException;
import kr.co.poscoict.push.common.util.EmailSenderUtil;
import kr.co.poscoict.push.common.util.MessageSourceUtil;
import kr.co.poscoict.push.email.mapper.EmailMapper;
import kr.co.poscoict.push.email.model.Email;
import kr.co.poscoict.push.email.model.Refer;
import kr.co.poscoict.push.email.model.ReferMail;
import kr.co.poscoict.push.framework.transaction.OraTransactional;

/**
 * 이메일 service
 * @author Sangjun, Park
 *
 */
@Service
public class EmailService {
	
	@Autowired
	private EmailMapper emailMapper;
	
	@Autowired
	private EmailSenderUtil emailSenderUtil;
	
	@Autowired
	private MessageSourceUtil messageSource;

	/**
	 * 이메일 발송 처리
	 * @param email
	 * @throws EmailFailException 
	 */
	public void sendEmail(Email email) throws EmailFailException {
		try {
			this.emailSenderUtil.sendEmail(email);
		} catch (UnsupportedEncodingException | MessagingException e) {
			throw new EmailFailException(messageSource.getMessage("msg.email.fail"), e);
		}
	}
	
	/**
	 * 참조자 이메일 발송
	 * @param referMail
	 */
	@OraTransactional
	public void sendReferMail(ReferMail referMail) {
		this.emailMapper.insertEmail(referMail);
	}
	
	/**
	 * 이메일 발송 취소
	 * @param email
	 */
	@OraTransactional
	public void cancelEmail(ReferMail referMail) {
		if(!StringUtils.equalsIgnoreCase(referMail.getApprovedFlag(), "C")) {
			referMail.setApprovedFlag("C");
		}
		this.emailMapper.updateEmail(referMail);
	}
	
	/**
	 * 참조자 이메일 조회
	 * @param email
	 * @return
	 */
	public List<Refer> getEmailList(ReferMail referMail) {
		return this.emailMapper.selectEmail(referMail);
	}

}
