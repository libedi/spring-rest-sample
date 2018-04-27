package kr.co.poscoict.card.adjust.model;

/**
 * 카드 사용내역
 * @author Sangjun, Park
 *
 */
public class CardHistory {
	private Integer seq;				// 일련번호
	private String transdate;		// 카드승인일자
	private String transtime;		// 승인시간
	private String merchname;		// 가맹점명
	private String statCd;			// 상태코드
	private String statNm;			// 상태코도명
	private String apprno;			// 승인번호
	private String apprtot;			// 승인금액
	private String appramt;			// 공급가
	private String vat;				// 부가세
	private String tips;			// 봉사료
	private String mccname;			// 업종명
	private String merchaddr;		// 가맹점주소
	private String apprPerson;		// 승인권자
	private String apprDate;		// 정산승인일자
	private String apprComments;	// 승인메시지
	private String transferFlag;	// transfer여부 : "N"인 경우에만 정산제외,정산 가능
	private String cardno;			// 카드번호
	private String hasVatYn;		// 부가세 포함여부
	private String initDate;		// 정산일자 초기값
	private String taxtype;			// 과세유형
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getTransdate() {
		return transdate;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	public String getTranstime() {
		return transtime;
	}
	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}
	public String getMerchname() {
		return merchname;
	}
	public void setMerchname(String merchname) {
		this.merchname = merchname;
	}
	public String getStatCd() {
		return statCd;
	}
	public void setStatCd(String statCd) {
		this.statCd = statCd;
	}
	public String getStatNm() {
		return statNm;
	}
	public void setStatNm(String statNm) {
		this.statNm = statNm;
	}
	public String getApprno() {
		return apprno;
	}
	public void setApprno(String apprno) {
		this.apprno = apprno;
	}
	public String getApprtot() {
		return apprtot;
	}
	public void setApprtot(String apprtot) {
		this.apprtot = apprtot;
	}
	public String getAppramt() {
		return appramt;
	}
	public void setAppramt(String appramt) {
		this.appramt = appramt;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public String getMccname() {
		return mccname;
	}
	public void setMccname(String mccname) {
		this.mccname = mccname;
	}
	public String getMerchaddr() {
		return merchaddr;
	}
	public void setMerchaddr(String merchaddr) {
		this.merchaddr = merchaddr;
	}
	public String getApprPerson() {
		return apprPerson;
	}
	public void setApprPerson(String apprPerson) {
		this.apprPerson = apprPerson;
	}
	public String getApprDate() {
		return apprDate;
	}
	public void setApprDate(String apprDate) {
		this.apprDate = apprDate;
	}
	public String getApprComments() {
		return apprComments;
	}
	public void setApprComments(String apprComments) {
		this.apprComments = apprComments;
	}
	public String getTransferFlag() {
		return transferFlag;
	}
	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getHasVatYn() {
		return hasVatYn;
	}
	public void setHasVatYn(String hasVatYn) {
		this.hasVatYn = hasVatYn;
	}
	public String getInitDate() {
		return initDate;
	}
	public void setInitDate(String initDate) {
		this.initDate = initDate;
	}
	public String getTaxtype() {
		return taxtype;
	}
	public void setTaxtype(String taxtype) {
		this.taxtype = taxtype;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CardHistory [");
		if (seq != null)
			builder.append("seq=").append(seq).append(", ");
		if (transdate != null)
			builder.append("transdate=").append(transdate).append(", ");
		if (transtime != null)
			builder.append("transtime=").append(transtime).append(", ");
		if (merchname != null)
			builder.append("merchname=").append(merchname).append(", ");
		if (statCd != null)
			builder.append("statCd=").append(statCd).append(", ");
		if (statNm != null)
			builder.append("statNm=").append(statNm).append(", ");
		if (apprno != null)
			builder.append("apprno=").append(apprno).append(", ");
		if (apprtot != null)
			builder.append("apprtot=").append(apprtot).append(", ");
		if (appramt != null)
			builder.append("appramt=").append(appramt).append(", ");
		if (vat != null)
			builder.append("vat=").append(vat).append(", ");
		if (tips != null)
			builder.append("tips=").append(tips).append(", ");
		if (mccname != null)
			builder.append("mccname=").append(mccname).append(", ");
		if (merchaddr != null)
			builder.append("merchaddr=").append(merchaddr).append(", ");
		if (apprPerson != null)
			builder.append("apprPerson=").append(apprPerson).append(", ");
		if (apprDate != null)
			builder.append("apprDate=").append(apprDate).append(", ");
		if (apprComments != null)
			builder.append("apprComments=").append(apprComments).append(", ");
		if (transferFlag != null)
			builder.append("transferFlag=").append(transferFlag).append(", ");
		if (cardno != null)
			builder.append("cardno=").append(cardno).append(", ");
		if (hasVatYn != null)
			builder.append("hasVatYn=").append(hasVatYn).append(", ");
		if (initDate != null)
			builder.append("initDate=").append(initDate).append(", ");
		if (initDate != null)
			builder.append("taxtype=").append(initDate);
		builder.append("]");
		return builder.toString();
	}
}
