package kr.co.poscoict.card.detail.model;

/**
 * 승인권자
 * @author Sangjun, Park
 *
 */
public class Approve {
	private String empcd;
	private String empId;
	private String hname;
	private String jikwicdDisp;
	private String deptcdDisp;
	private String userId;
	private long autoApproveAmt;
	private long selfApproveAmt;
	
	public String getEmpcd() {
		return empcd;
	}
	public void setEmpcd(String empcd) {
		this.empcd = empcd;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getHname() {
		return hname;
	}
	public void setHname(String hname) {
		this.hname = hname;
	}
	public String getJikwicdDisp() {
		return jikwicdDisp;
	}
	public void setJikwicdDisp(String jikwicdDisp) {
		this.jikwicdDisp = jikwicdDisp;
	}
	public String getDeptcdDisp() {
		return deptcdDisp;
	}
	public void setDeptcdDisp(String deptcdDisp) {
		this.deptcdDisp = deptcdDisp;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getAutoApproveAmt() {
		return autoApproveAmt;
	}
	public void setAutoApproveAmt(long autoApproveAmt) {
		this.autoApproveAmt = autoApproveAmt;
	}
	public long getSelfApproveAmt() {
		return selfApproveAmt;
	}
	public void setSelfApproveAmt(long selfApproveAmt) {
		this.selfApproveAmt = selfApproveAmt;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Approve [");
		if (empcd != null)
			builder.append("empcd=").append(empcd).append(", ");
		if (empId != null)
			builder.append("empId=").append(empId).append(", ");
		if (hname != null)
			builder.append("hname=").append(hname).append(", ");
		if (jikwicdDisp != null)
			builder.append("jikwicdDisp=").append(jikwicdDisp).append(", ");
		if (deptcdDisp != null)
			builder.append("deptcdDisp=").append(deptcdDisp).append(", ");
		if (userId != null)
			builder.append("userId=").append(userId).append(", ");
		builder.append("autoApproveAmt=").append(autoApproveAmt).append(", selfApproveAmt=").append(selfApproveAmt)
				.append("]");
		return builder.toString();
	}
}
