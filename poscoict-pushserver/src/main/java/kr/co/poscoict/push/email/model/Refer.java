package kr.co.poscoict.push.email.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 참조자
 * @author Sangjun, Park
 *
 */
@JsonInclude(Include.NON_NULL)
public class Refer {
	private Integer seq;
	private String sendGubun;
	private String dlOrderId;
	private String fromEmpid;
	private String fromEmail;
	private String toEmpid;
	private String toEmail;
	private String approvedFlag;
	private String sendFlag;
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getSendGubun() {
		return sendGubun;
	}
	public void setSendGubun(String sendGubun) {
		this.sendGubun = sendGubun;
	}
	public String getDlOrderId() {
		return dlOrderId;
	}
	public void setDlOrderId(String dlOrderId) {
		this.dlOrderId = dlOrderId;
	}
	public String getFromEmpid() {
		return fromEmpid;
	}
	public void setFromEmpid(String fromEmpid) {
		this.fromEmpid = fromEmpid;
	}
	public String getFromEmail() {
		return fromEmail;
	}
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	public String getToEmpid() {
		return toEmpid;
	}
	public void setToEmpid(String toEmpid) {
		this.toEmpid = toEmpid;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	public String getApprovedFlag() {
		return approvedFlag;
	}
	public void setApprovedFlag(String approvedFlag) {
		this.approvedFlag = approvedFlag;
	}
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Refer [");
		if (seq != null)
			builder.append("seq=").append(seq).append(", ");
		if (sendGubun != null)
			builder.append("sendGubun=").append(sendGubun).append(", ");
		if (dlOrderId != null)
			builder.append("dlOrderId=").append(dlOrderId).append(", ");
		if (fromEmpid != null)
			builder.append("fromEmpid=").append(fromEmpid).append(", ");
		if (fromEmail != null)
			builder.append("fromEmail=").append(fromEmail).append(", ");
		if (toEmpid != null)
			builder.append("toEmpid=").append(toEmpid).append(", ");
		if (toEmail != null)
			builder.append("toEmail=").append(toEmail).append(", ");
		if (approvedFlag != null)
			builder.append("approvedFlag=").append(approvedFlag).append(", ");
		if (sendFlag != null)
			builder.append("sendFlag=").append(sendFlag);
		builder.append("]");
		return builder.toString();
	}
}
