package kr.co.poscoict.card.common.model;

/**
 * 뱃지 건수
 * @author Sangjun, Park
 *
 */
public class Badge {
	private int totalCnt;
	private int erpCnt;
	private int cardCnt;
	private int pomCnt;
	private int secCnt;
	private int etcCnt;
	
	public int getTotalCnt() {
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
