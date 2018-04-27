package kr.co.poscoict.batch.model;

/**
 * 사용자 정보
 * @author Sangjun, Park
 *
 */
public class User {
	private String empcd;
	private String email;
	
	public String getEmpcd() {
		return empcd;
	}
	public void setEmpcd(String empcd) {
		this.empcd = empcd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [");
		if (empcd != null)
			builder.append("empcd=").append(empcd).append(", ");
		if (email != null)
			builder.append("email=").append(email);
		builder.append("]");
		return builder.toString();
	}
}
