package kr.co.poscoict.push.framework.model;

public class AccountCredentials {
	private String encodedId;

	public String getEncodedId() {
		return encodedId;
	}
	public void setEncodedId(String encodedId) {
		this.encodedId = encodedId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountCredentials [");
		if (encodedId != null)
			builder.append("encodedId=").append(encodedId);
		builder.append("]");
		return builder.toString();
	}
}
