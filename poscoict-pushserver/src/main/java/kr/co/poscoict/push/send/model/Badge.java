package kr.co.poscoict.push.send.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import io.swagger.annotations.ApiModelProperty;

/**
 * 서버별 뱃지건수
 * @author Sangjun, Park
 *
 */
public class Badge {
	@ApiModelProperty(notes = "총 건수")
	private int totalCnt;
	@ApiModelProperty(notes = "ERP건수")
	private int erpCnt;
	@ApiModelProperty(notes = "법인카드 건수")
	private int cardCnt;
	@ApiModelProperty(notes = "사업관리 건수")
	private int pomCnt;
	@ApiModelProperty(notes = "보안승인 건수")
	private int secCnt;
	@ApiModelProperty(notes = "기타결재 건수")
	private int etcCnt;
	
	@JsonGetter("totalCnt")
	public int getTotalCnt() {
		this.totalCnt = erpCnt + cardCnt + pomCnt + secCnt + etcCnt;
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getErpCnt() {
		return erpCnt;
	}
	public void setErpCnt(int erpCnt) {
		this.erpCnt = erpCnt;
	}
	public int getCardCnt() {
		return cardCnt;
	}
	public void setCardCnt(int cardCnt) {
		this.cardCnt = cardCnt;
	}
	public int getPomCnt() {
		return pomCnt;
	}
	public void setPomCnt(int pomCnt) {
		this.pomCnt = pomCnt;
	}
	public int getSecCnt() {
		return secCnt;
	}
	public void setSecCnt(int secCnt) {
		this.secCnt = secCnt;
	}
	public int getEtcCnt() {
		return etcCnt;
	}
	public void setEtcCnt(int etcCnt) {
		this.etcCnt = etcCnt;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Badge [totalCnt=").append(totalCnt).append(", erpCnt=").append(erpCnt).append(", cardCnt=")
				.append(cardCnt).append(", pomCnt=").append(pomCnt).append(", secCnt=").append(secCnt)
				.append(", etcCnt=").append(etcCnt).append("]");
		return builder.toString();
	}
}
