package kr.co.poscoict.card.detail.model;

/**
 * 원가유형
 * @author Sangjun, Park
 *
 */
public class Type {
	private String expType;
	private String mainAccCode;
	private String subAccCode;
	private String attributeCategory;
	
	public String getExpType() {
		return expType;
	}
	public void setExpType(String expType) {
		this.expType = expType;
	}
	public String getMainAccCode() {
		return mainAccCode;
	}
	public void setMainAccCode(String mainAccCode) {
		this.mainAccCode = mainAccCode;
	}
	public String getSubAccCode() {
		return subAccCode;
	}
	public void setSubAccCode(String subAccCode) {
		this.subAccCode = subAccCode;
	}
	public String getAttributeCategory() {
		return attributeCategory;
	}
	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Type [");
		if (expType != null)
			builder.append("expType=").append(expType).append(", ");
		if (mainAccCode != null)
			builder.append("mainAccCode=").append(mainAccCode).append(", ");
		if (subAccCode != null)
			builder.append("subAccCode=").append(subAccCode).append(", ");
		if (attributeCategory != null)
			builder.append("attributeCategory=").append(attributeCategory);
		builder.append("]");
		return builder.toString();
	}
}
