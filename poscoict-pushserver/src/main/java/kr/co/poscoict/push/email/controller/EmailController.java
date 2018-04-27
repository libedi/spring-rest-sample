package kr.co.poscoict.push.email.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import kr.co.poscoict.push.common.exception.EmailFailException;
import kr.co.poscoict.push.common.model.User;
import kr.co.poscoict.push.common.util.MessageSourceUtil;
import kr.co.poscoict.push.email.model.Email;
import kr.co.poscoict.push.email.model.Refer;
import kr.co.poscoict.push.email.model.ReferMail;
import kr.co.poscoict.push.email.service.EmailService;
import kr.co.poscoict.push.framework.model.ValidMarker.Create;
import kr.co.poscoict.push.framework.model.ValidMarker.Delete;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 이메일 Controller
 * @author Sangjun, Park
 *
 */
@RestController
@RequestMapping("/email")
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MessageSourceUtil messageSource;
	
	/**
	 * 이메일 즉시 발송
	 * @param email
	 * @throws EmailFailException
	 */
	@ApiOperation(value = "이메일 즉시 발송", authorizations = {
			@Authorization(value = "jwt")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "발송성공")
	})
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void sendEmail(
			@ApiParam(value = "이메일 데이터", required = true) @RequestBody @Valid Email email,
			@ApiIgnore @AuthenticationPrincipal User user) throws EmailFailException {
		if(StringUtils.isEmpty(email.getFrom())) {
			email.setFrom(user.getEmail());
		}
		this.emailService.sendEmail(email);
	}
	
	/**
	 * 참조자 이메일 발송 요청
	 * @param referMail
	 */
	@ApiOperation(value = "참조자 이메일 발송 요청", authorizations = {
			@Authorization(value = "jwt")
	})
	@ApiResponses({
		@ApiResponse(code = 201, message = "발송요청성공")
	})
	@PostMapping(path = "/refer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ReferMail sendReferMail(
			@ApiParam(value = "참조자 이메일 데이터", required = true) @RequestBody @Validated(Create.class) ReferMail referMail,
			@ApiIgnore @AuthenticationPrincipal User user) {
		if(referMail.getFromEmpid() == null) {
			referMail.setFromEmpid(user.getEmpId());
		}
		if(StringUtils.isEmpty(referMail.getFromEmail())) {
			referMail.setFromEmail(user.getEmail());
		}
		this.emailService.sendReferMail(referMail);
		return referMail;
	}
	
	/**
	 * 참조자 이메일 취소 요청
	 * @param email
	 */
	@ApiOperation(value = "참조자 이메일 취소 요청", authorizations = {
			@Authorization(value = "jwt")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "취소요청성공"),
		@ApiResponse(code = 404, message = "해당 이메일 요청 내역 미존재")
	})
	@DeleteMapping(path = "/refer")
	@ResponseStatus(HttpStatus.OK)
	public void cancelEmail(
			@ApiParam(value = "참조자 이메일 데이터", required = true) @RequestBody @Validated(Delete.class) ReferMail referMail,
			@ApiIgnore @AuthenticationPrincipal User user) {
		if(referMail.getFromEmpid() == null) {
			referMail.setFromEmpid(user.getEmpId());
		}
		if(!StringUtils.equalsIgnoreCase(referMail.getSendFlag(), "N")) {
			referMail.setSendFlag("N");
		}
		if(!StringUtils.equalsIgnoreCase(referMail.getSendGubun(), "S")) {
			referMail.setSendGubun("S");
		}
		List<Refer> referList = this.emailService.getEmailList(referMail);
		if(referList.size() == 0) {
			throw new ResourceNotFoundException(messageSource.getMessage("msg.email.no-found"));
		}
		referMail.setSeqList(referList.stream().map(r -> r.getSeq()).collect(Collectors.toList()));
		this.emailService.cancelEmail(referMail);
	}
	
}
