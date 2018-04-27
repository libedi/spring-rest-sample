package kr.co.poscoict.push.email.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import kr.co.poscoict.push.common.model.ListPattern;

/**
 * 이메일
 * @author Sangjun, Park
 *
 */
public class Email {
	@ApiModelProperty(notes = "받는사람 메일주소", position = 0, required = true, allowableValues = "receiver@poscoict.com")
	@NotNull
	@ListPattern(regexp = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$",
			message = "이메일 형식이 아닙니다.")
	private List<String> to;
	
	@ApiModelProperty(notes = "보내는사람 메일주소", position = 3, example = "sender@poscoict.com")
	@Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$",
			message = "이메일 형식이 아닙니다.")
	private String from;
	
	@ApiModelProperty(notes = "메일제목", position = 1, required = true, example = "Email Title")
	@NotEmpty
	private String subject;
	
	@ApiModelProperty(notes = "메일내용", position = 2, required = true, example = "This is a email message.")
	@NotEmpty
	private String message;
	
	@ApiModelProperty(notes = "첨부파일ID리스트", position = 4, allowableValues = "0123")
	private List<String> attachFileIdList;
	
	@ApiModelProperty(notes = "HTML여부", position = 5, example = "true")
	private boolean isHtml = false;
	
	public List<String> getTo() {
		return to;
	}
	public void setTo(List<String> to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getAttachFileIdList() {
		return attachFileIdList;
	}
	public void setAttachFileIdList(List<String> attachFileIdList) {
		this.attachFileIdList = attachFileIdList;
	}
	public boolean isHtml() {
		return isHtml;
	}
	public void setHtml(boolean isHtml) {
		this.isHtml = isHtml;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Email [");
		if (to != null)
			builder.append("to=").append(to).append(", ");
		if (from != null)
			builder.append("from=").append(from).append(", ");
		if (subject != null)
			builder.append("subject=").append(subject).append(", ");
		if (message != null)
			builder.append("message=").append(message).append(", ");
		if (attachFileIdList != null)
			builder.append("attachFileIdList=").append(attachFileIdList).append(", ");
		builder.append("isHtml=").append(isHtml).append("]");
		return builder.toString();
	}
}