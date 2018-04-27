package kr.co.poscoict.card.adjust.model;

/**
 * 카드
 * @author Sangjun, Park
 *
 */
public class Card {
	private String cardNumber;
	private String cardBrandLookupCode;
	private String personName;
	
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardBrandLookupCode() {
		return cardBrandLookupCode;
	}
	public void setCardBrandLookupCode(String cardBrandLookupCode) {
		this.cardBrandLookupCode = cardBrandLookupCode;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Card [");
		if (cardNumber != null)
			builder.append("cardNumber=").append(cardNumber).append(", ");
		if (cardBrandLookupCode != null)
			builder.append("cardBrandLookupCode=").append(cardBrandLookupCode).append(", ");
		if (personName != null)
			builder.append("personName=").append(personName);
		builder.append("]");
		return builder.toString();
	}
}
