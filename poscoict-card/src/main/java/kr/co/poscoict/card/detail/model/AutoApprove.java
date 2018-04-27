package kr.co.poscoict.card.detail.model;

/**
 * 승인권자
 * @author Sangjun, Park
 *
 */
public class AutoApprove {
	private String selfApproveYn;
	private long autoApproveAmt;
	private long selfApproveAmt;
	
	
	public String getSelfApproveYn() {
		return selfApproveYn;
	}
	public void setSelfApproveYn(String selfApproveYn) {
		this.selfApproveYn = selfApproveYn;
	}
	public long getAutoApproveAmt() {
		return autoApproveAmt;
	}
	public void setAutoApproveAmt(long autoApproveAmt) {
		this.autoApproveAmt = autoApproveAmt;
	}
	public long getSelfApproveAmt() {
		return selfApproveAmt;
	}
	public void setSelfApproveAmt(long selfApproveAmt) {
		this.selfApproveAmt = selfApproveAmt;
	}
}
