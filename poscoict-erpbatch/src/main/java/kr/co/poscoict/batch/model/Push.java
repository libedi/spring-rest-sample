package kr.co.poscoict.batch.model;

/**
 * Push Entity
 * @author Sangjun, Park
 *
 */
public class Push {
	private String message;
	private String targetUrl;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
		if (message != null)
			builder.append("message=").append(message).append(", ");
		if (targetUrl != null)
			builder.append("targetUrl=").append(targetUrl);
		builder.append("]");
		return builder.toString();
	}
}
