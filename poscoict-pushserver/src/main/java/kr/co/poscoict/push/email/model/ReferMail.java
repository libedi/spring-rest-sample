package kr.co.poscoict.push.email.model;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import kr.co.poscoict.push.framework.model.ValidMarker.Create;
import kr.co.poscoict.push.framework.model.ValidMarker.Delete;

/**
 * 참조자 이메일
 * @author Sangjun, Park
 *
 */
public class ReferMail {
	@ApiModelProperty(notes = "발송구분", position = 0, required = true, example = "S")
	@NotEmpty(groups = Create.class)
	@Pattern(regexp = "^[S|C]$", message = "올바른 구분자가 아닙니다.", groups = Create.class)
	private String sendGubun;
	
	@ApiModelProperty(notes = "DL_ORDER_ID", position = 1, required = true, example = "163566")
	@NotEmpty(groups = {Create.class, Delete.class})
	private String dlOrderId;
	
	@ApiModelProperty(notes = "보내는사람 직번", position = 4, example = "6467")
	private Integer fromEmpid;
	
	@ApiModelProperty(notes = "보내는사람 메일주소", position = 5, example = "test@poscoict.com")
	private String fromEmail;
	
	@ApiModelProperty(notes = "받는사람 직번리스트", position = 3, required = true, allowableValues = "6421")
	@NotEmpty(groups = Create.class)
	private List<Integer> toEmpid;
	
	@ApiModelProperty(notes = "승인여부", position = 2, required = true, example = "S")
	@NotEmpty(groups = Create.class)
	@Pattern(regexp = "^[S|Y|N]$", message = "올바른 값이 아닙니다.", groups = Create.class)
	private String approvedFlag;
	
	@ApiModelProperty(notes = "발송여부", position = 6)
	private String sendFlag;
	
	@ApiModelProperty(notes = "순번", position = 7)
	private Integer seq;
	
	@ApiModelProperty(notes = "순번리스트", position = 8)
	private List<Integer> seqList;
	
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
	public Integer getFromEmpid() {
		return fromEmpid;
	}
	public void setFromEmpid(Integer fromEmpid) {
		this.fromEmpid = fromEmpid;
	}
	public String getFromEmail() {
		return fromEmail;
	}
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	public List<Integer> getToEmpid() {
		return toEmpid;
	}
	public void setToEmpid(List<Integer> toEmpid) {
		this.toEmpid = toEmpid;
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
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public List<Integer> getSeqList() {
		return seqList;
	}
	public void setSeqList(List<Integer> seqList) {
		this.seqList = seqList;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReferMail [");
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
		if (approvedFlag != null)
			builder.append("approvedFlag=").append(approvedFlag).append(", ");
		if (sendFlag != null)
			builder.append("sendFlag=").append(sendFlag).append(", ");
		if (seq != null)
			builder.append("seq=").append(seq).append(", ");
		if (seqList != null)
			builder.append("seqList=").append(seqList);
		builder.append("]");
		return builder.toString();
	}
}