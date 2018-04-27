package kr.co.poscoict.card.adjust.model;

import org.hibernate.validator.constraints.NotEmpty;

import kr.co.poscoict.card.common.model.User;

/**
 * 법인카드 정산 조회
 * @author Sangjun, Park
 *
 */
public class CardSearch {
	@NotEmpty
	private String cardno;
	@NotEmpty
	private String from;
	@NotEmpty
	private String to;
	
	private String status;
	
	private User user;
	
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
