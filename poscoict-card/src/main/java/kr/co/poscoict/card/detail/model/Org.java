package kr.co.poscoict.card.detail.model;

/**
 * 수행조직
 * @author Sangjun, Park
 *
 */
public class Org {
	private String expOrgName;
	private String expOrgId;
	private String segment2;
	
	public String getExpOrgName() {
		return expOrgName;
	}
	public void setExpOrgName(String expOrgName) {
		this.expOrgName = expOrgName;
	}
	public String getExpOrgId() {
		return expOrgId;
	}
	public void setExpOrgId(String expOrgId) {
		this.expOrgId = expOrgId;
	}
	public String getSegment2() {
		return segment2;
	}
	public void setSegment2(String segment2) {
		this.segment2 = segment2;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Org [");
		if (expOrgName != null)
			builder.append("expOrgName=").append(expOrgName).append(", ");
		if (expOrgId != null)
			builder.append("expOrgId=").append(expOrgId).append(", ");
		if (segment2 != null)
			builder.append("segment2=").append(segment2);
		builder.append("]");
		return builder.toString();
	}
}
