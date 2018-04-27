package kr.co.poscoict.card.detail.model;

/**
 * 주계정
 * @author Sangjun, Park
 *
 */
public class Account {
	private String accName;
	private String accGubun;
	private String mainAccCode;
	private String mainAccDesc;
	private String subAccCode;
	private String subAccDesc;
	private String attributeCategory;
	private String recentUse;

	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAccGubun() {
		return accGubun;
	}
	public void setAccGubun(String accGubun) {
		this.accGubun = accGubun;
	}
	public String getMainAccCode() {
		return mainAccCode;
	}
	public void setMainAccCode(String mainAccCode) {
		this.mainAccCode = mainAccCode;
	}
	public String getMainAccDesc() {
		return mainAccDesc;
	}
	public void setMainAccDesc(String mainAccDesc) {
		this.mainAccDesc = mainAccDesc;
	}
	public String getSubAccCode() {
		return subAccCode;
	}
	public void setSubAccCode(String subAccCode) {
		this.subAccCode = subAccCode;
	}
	public String getSubAccDesc() {
		return subAccDesc;
	}
	public void setSubAccDesc(String subAccDesc) {
		this.subAccDesc = subAccDesc;
	}
	public String getAttributeCategory() {
		return attributeCategory;
	}
	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}
	public String getRecentUse() {
		return recentUse;
	}
	public void setRecentUse(String recentUse) {
		this.recentUse = recentUse;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [");
		if (accName != null)
			builder.append("accName=").append(accName).append(", ");
		if (accGubun != null)
			builder.append("accGubun=").append(accGubun).append(", ");
		if (mainAccCode != null)
			builder.append("mainAccCode=").append(mainAccCode).append(", ");
		if (mainAccDesc != null)
			builder.append("mainAccDesc=").append(mainAccDesc).append(", ");
		if (subAccCode != null)
			builder.append("subAccCode=").append(subAccCode).append(", ");
		if (subAccDesc != null)
			builder.append("subAccDesc=").append(subAccDesc).append(", ");
		if (attributeCategory != null)
			builder.append("attributeCategory=").append(attributeCategory).append(", ");
		if (recentUse != null)
			builder.append("recentUse=").append(recentUse);
		builder.append("]");
		return builder.toString();
	}
}