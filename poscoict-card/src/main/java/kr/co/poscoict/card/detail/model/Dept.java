package kr.co.poscoict.card.detail.model;

/**
 * 부서
 * @author Sangjun, Park
 *
 */
public class Dept {
	private String deptCode;
	private String deptNm;
	
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Dept [");
		if (deptCode != null)
			builder.append("deptCode=").append(deptCode).append(", ");
		if (deptNm != null)
			builder.append("deptNm=").append(deptNm);
		builder.append("]");
		return builder.toString();
	}
}
