package kr.co.poscoict.card.detail.model;

import kr.co.poscoict.card.common.model.User;

/**
 * 전표
 * @author Sangjun, Park
 *
 */
public class Invoice implements Cloneable {
	private Integer seqId;
	private String sessionId;
	private String glDate;
	private String vendorId;
	private String vendorSiteId;
	private String termsId;
	private String cardno;
	private String hasVat;
	private String description;
	private Integer seq;
	private String attend;
	private String fileId;
	private String fileNm;
	private String attributeCategory;
	private String deptCode;
	private String mainAccCode;
	private String subAccCode;
	private String projectName;
	private String projectNumber;
	private Long projectId;
	private String projectType;
	private String taskName;
	private String taskNumber;
	private Long taskId;
	private String expType;
	private String expOrgName;
	private Long expOrgId;
	private Long transactionAmount;
	private Long vat1;
	private User user;
	private Integer transferId;
	private String xErrFlag;
	private String xErrStep;
	private String xErrMsg;
	private String invoiceId;
	private String invoiceVendorId;
	private String invoiceNum;
	private Integer holdCount;
	private String approvalStatus;
	private String fundsReturnCode;
	private Long wfId;
	private String empId;
	private String supervisorId;
	private String itemKey;
	private int apprLevelCnt;
	private int apprLevel;
	
	public Integer getSeqId() {
		return seqId;
	}
	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getGlDate() {
		return glDate;
	}
	public void setGlDate(String glDate) {
		this.glDate = glDate;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorSiteId() {
		return vendorSiteId;
	}
	public void setVendorSiteId(String vendorSiteId) {
		this.vendorSiteId = vendorSiteId;
	}
	public String getTermsId() {
		return termsId;
	}
	public void setTermsId(String termsId) {
		this.termsId = termsId;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getHasVat() {
		return hasVat;
	}
	public void setHasVat(String hasVat) {
		this.hasVat = hasVat;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getAttend() {
		return attend;
	}
	public void setAttend(String attend) {
		this.attend = attend;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getAttributeCategory() {
		return attributeCategory;
	}
	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getMainAccCode() {
		return mainAccCode;
	}
	public void setMainAccCode(String mainAccCode) {
		this.mainAccCode = mainAccCode;
	}
	public String getSubAccCode() {
		return subAccCode;
	}
	public void setSubAccCode(String subAccCode) {
		this.subAccCode = subAccCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskNumber() {
		return taskNumber;
	}
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getExpType() {
		return expType;
	}
	public void setExpType(String expType) {
		this.expType = expType;
	}
	public String getExpOrgName() {
		return expOrgName;
	}
	public void setExpOrgName(String expOrgName) {
		this.expOrgName = expOrgName;
	}
	public Long getExpOrgId() {
		return expOrgId;
	}
	public void setExpOrgId(Long expOrgId) {
		this.expOrgId = expOrgId;
	}
	public Long getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(Long transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public Long getVat1() {
		return vat1;
	}
	public void setVat1(Long vat1) {
		this.vat1 = vat1;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getTransferId() {
		return transferId;
	}
	public void setTransferId(Integer transferId) {
		this.transferId = transferId;
	}
	public String getxErrFlag() {
		return xErrFlag;
	}
	public void setxErrFlag(String xErrFlag) {
		this.xErrFlag = xErrFlag;
	}
	public String getxErrStep() {
		return xErrStep;
	}
	public void setxErrStep(String xErrStep) {
		this.xErrStep = xErrStep;
	}
	public String getxErrMsg() {
		return xErrMsg;
	}
	public void setxErrMsg(String xErrMsg) {
		this.xErrMsg = xErrMsg;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getInvoiceVendorId() {
		return invoiceVendorId;
	}
	public void setInvoiceVendorId(String invoiceVendorId) {
		this.invoiceVendorId = invoiceVendorId;
	}
	public String getInvoiceNum() {
		return invoiceNum;
	}
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	public Integer getHoldCount() {
		return holdCount;
	}
	public void setHoldCount(Integer holdCount) {
		this.holdCount = holdCount;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getFundsReturnCode() {
		return fundsReturnCode;
	}
	public void setFundsReturnCode(String fundsReturnCode) {
		this.fundsReturnCode = fundsReturnCode;
	}
	public Long getWfId() {
		return wfId;
	}
	public void setWfId(Long wfId) {
		this.wfId = wfId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getSupervisorId() {
		return supervisorId;
	}
	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}
	public String getItemKey() {
		return itemKey;
	}
	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}
	public int getApprLevelCnt() {
		return apprLevelCnt;
	}
	public void setApprLevelCnt(int apprLevelCnt) {
		this.apprLevelCnt = apprLevelCnt;
	}
	public int getApprLevel() {
		return apprLevel;
	}
	public void setApprLevel(int apprLevel) {
		this.apprLevel = apprLevel;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
