package kr.co.poscoict.push.send.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Push Model
 * @author Sangjun, Park
 *
 */
public class Push {
	@ApiModelProperty(notes = "직번", hidden = true)
	private String empcd;
	
	@ApiModelProperty(notes = "이메일주소", position = 1, example = "test@poscoict.com")
	@Email
	private String email;
	
	@ApiModelProperty(notes = "푸시메시지", position = 0, required = true, example = "This is a push message.")
	@NotEmpty
	private String message;
	
	@ApiModelProperty(notes = "뱃지카운트", hidden = true)
	private int badge;
	
	@ApiModelProperty(notes = "리다이렉트 URL", position = 2, example = "card")
	private String targetUrl;
	
	@ApiModelProperty(notes = "푸시알림여부", position = 3, example = "false")
	private boolean enableSound = true;
	
	public String getEmpcd() {
		return empcd;
	}
	public void setEmpcd(String empcd) {
		this.empcd = empcd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getBadge() {
		return badge;
	}
	public void setBadge(int badge) {
		this.badge = badge;
	}
	public String getTargetUrl() {
		return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public boolean isEnableSound() {
		return enableSound;
	}
	public void setEnableSound(boolean enableSound) {
		this.enableSound = enableSound;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Push [");
		if (empcd != null)
			builder.append("empcd=").append(empcd).append(", ");
		if (email != null)
			builder.append("email=").append(email).append(", ");
		if (message != null)
			builder.append("message=").append(message).append(", ");
		builder.append("badge=").append(badge).append(", ");
		if (targetUrl != null)
			builder.append("targetUrl=").append(targetUrl).append(", ");
		builder.append("enableSound=").append(enableSound).append("]");
		return builder.toString();
	}
}
