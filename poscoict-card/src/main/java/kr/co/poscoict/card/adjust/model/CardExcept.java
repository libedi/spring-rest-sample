package kr.co.poscoict.card.adjust.model;

import javax.validation.constraints.NotNull;

import kr.co.poscoict.card.common.model.User;

/**
 * 정산제외
 * @author Sangjun, Park
 *
 */
public class CardExcept {
	@NotNull
	private Integer seq;
	
	private String exceptCode;
	private String returnCode;
	private String returnMessage;
	
	private User user;
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getExceptCode() {
		return exceptCode;
	}
	public void setExceptCode(String exceptCode) {
		this.exceptCode = exceptCode;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CardExcept [");
		if (seq != null)
			builder.append("seq=").append(seq).append(", ");
		if (exceptCode != null)
			builder.append("exceptCode=").append(exceptCode).append(", ");
		if (user != null)
			builder.append("user=").append(user).append(", ");
		if (returnCode != null)
			builder.append("returnCode=").append(returnCode).append(", ");
		if (returnMessage != null)
			builder.append("returnMessage=").append(returnMessage);
		builder.append("]");
		return builder.toString();
	}
}
