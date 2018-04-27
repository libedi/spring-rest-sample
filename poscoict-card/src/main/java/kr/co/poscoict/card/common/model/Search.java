package kr.co.poscoict.card.common.model;

/**
 * 법인카드 공통 조회 DTO
 * @author Sangjun, Park
 *
 */
public class Search {
	private String from;
	private String to;
	private String status;
	private String srchTxt;
	private String deptCode;
	private User user;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSrchTxt() {
		return srchTxt;
	}
	public void setSrchTxt(String srchTxt) {
		this.srchTxt = srchTxt;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Search [");
		if (from != null)
			builder.append("from=").append(from).append(", ");
		if (to != null)
			builder.append("to=").append(to).append(", ");
		if (status != null)
			builder.append("status=").append(status).append(", ");
		if (srchTxt != null)
			builder.append("srchTxt=").append(srchTxt).append(", ");
		if (user != null)
			builder.append("user=").append(user).append(", ");
		if (deptCode != null)
			builder.append("deptCode=").append(deptCode);
		builder.append("]");
		return builder.toString();
	}
}
