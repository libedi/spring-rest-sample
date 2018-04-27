package kr.co.poscoict.card.common.model;

/**
 * 외부 호스트 URL
 * @author Sangjun, Park
 *
 */
public class HostInfo {
	private String homeUrl;
	private String pushUrl;
	private String erpUrl;
	private String cardUrl;
	private String workUrl;
	private String bsTripUrl;
	private String pomUrl;
	private String secUrl;
	private String etcUrl;
	
	public String getHomeUrl() {
		return homeUrl;
	}
	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}
	public String getPushUrl() {
		return pushUrl;
	}
	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}
	public String getErpUrl() {
		return erpUrl;
	}
	public void setErpUrl(String erpUrl) {
		this.erpUrl = erpUrl;
	}
	public String getCardUrl() {
		return cardUrl;
	}
	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}
	public String getWorkUrl() {
		return workUrl;
	}
	public void setWorkUrl(String workUrl) {
		this.workUrl = workUrl;
	}
	public String getBsTripUrl() {
		return bsTripUrl;
	}
	public void setBsTripUrl(String bsTripUrl) {
		this.bsTripUrl = bsTripUrl;
	}
	public String getPomUrl() {
		return pomUrl;
	}
	public void setPomUrl(String pomUrl) {
		this.pomUrl = pomUrl;
	}
	public String getSecUrl() {
		return secUrl;
	}
	public void setSecUrl(String secUrl) {
		this.secUrl = secUrl;
	}
	public String getEtcUrl() {
		return etcUrl;
	}
	public void setEtcUrl(String etcUrl) {
		this.etcUrl = etcUrl;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HostInfo [");
		if (homeUrl != null)
			builder.append("homeUrl=").append(homeUrl).append(", ");
		if (pushUrl != null)
			builder.append("pushUrl=").append(pushUrl).append(", ");
		if (erpUrl != null)
			builder.append("erpUrl=").append(erpUrl).append(", ");
		if (cardUrl != null)
			builder.append("cardUrl=").append(cardUrl).append(", ");
		if (workUrl != null)
			builder.append("workUrl=").append(workUrl).append(", ");
		if (bsTripUrl != null)
			builder.append("bsTripUrl=").append(bsTripUrl).append(", ");
		if (pomUrl != null)
			builder.append("pomUrl=").append(pomUrl).append(", ");
		if (secUrl != null)
			builder.append("secUrl=").append(secUrl).append(", ");
		if (etcUrl != null)
			builder.append("etcUrl=").append(etcUrl);
		builder.append("]");
		return builder.toString();
	}
}
