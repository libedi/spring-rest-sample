package kr.co.poscoict.push.send.model;

public enum ExtHost {
	/**
	 * ERP
	 */
	ERP("erp"),
	/**
	 * 법인카드
	 */
	CARD("card"),
	/**
	 * 사업관리
	 */
	POM("pom"),
	/**
	 * 보안승인
	 */
	SECURITY("sec"),
	/**
	 * 기타결재
	 */
	ETC("etc");
	
	private String value;
	
	private ExtHost(String value) {
		this.value = value;
	}
	
	public String toString() {
		return this.value;
	}
}
