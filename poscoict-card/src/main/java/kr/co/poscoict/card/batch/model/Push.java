package kr.co.poscoict.card.batch.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Push Model
 * @author Sangjun, Park
 *
 */
public class Push {
	private String empcd;
	
	private String email;
	
	@NotEmpty
	private String message;
	
	private int badge;
	
	private String targetUrl;
	
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
			builder.append("targetUrl=").append(targetUrl);
		builder.append("]");
		return builder.toString();
	}
}
