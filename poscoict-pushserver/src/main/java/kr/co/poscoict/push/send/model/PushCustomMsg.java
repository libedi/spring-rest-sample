package kr.co.poscoict.push.send.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 푸시 추가 Payload
 * @author Sangjun, Park
 *
 */
public class PushCustomMsg {
	@JsonIgnore
	private static final String TARGET_URL_PARAMETER = "targetUrl=";
	
	private String redirectUrl;

	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = new StringBuilder(TARGET_URL_PARAMETER).append(redirectUrl).toString();
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PushCustomMsg [");
		if (redirectUrl != null)
			builder.append("redirectUrl=").append(redirectUrl);
		builder.append("]");
		return builder.toString();
	}
}
