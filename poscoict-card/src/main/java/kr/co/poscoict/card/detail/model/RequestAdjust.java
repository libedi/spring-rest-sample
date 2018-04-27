package kr.co.poscoict.card.detail.model;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import kr.co.poscoict.card.common.model.User;

/**
 * 정산승인요청
 * @author Sangjun, Park
 *
 */
public class RequestAdjust {
	private Integer seq;
	@NotEmpty
	private String hasVat;
	@NotEmpty
	private String adjustType;
	private String cardno;
	@NotEmpty
	private String glDateSubmit;
	@NotEmpty
	private String empcd;
	@NotEmpty
	private String empId;
	@NotEmpty
	private String userId;
	@NotEmpty
	private String attend;
	@NotEmpty
	private String jwt;
	private String fileId;
	private String fileNm;
	private List<String> deptCode;
	private List<Long> appramt;
	private List<String> description;
	private List<String> projectName;
	private List<String> projectNumber;
	private List<String> projectType;
	private List<String> projectCode;
	private List<String> projectStatusCodeNm;
	private List<Long> projectId;
	private List<Long> taskId;
	private List<String> taskName;
	private List<String> taskNumber;
	private List<String> expOrgName;
	private List<Long> expOrgId;
	private List<String> segment2;
	private List<String> expType;
	private List<String> mainAccCode;
	private List<String> subAccCode;
	private List<String> attributeCategory;
	
	private User user;
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getHasVat() {
		return hasVat;
	}
	public void setHasVat(String hasVat) {
		this.hasVat = hasVat;
	}
	public String getAdjustType() {
		return adjustType;
	}
	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getGlDateSubmit() {
		return glDateSubmit;
	}
	public void setGlDateSubmit(String glDateSubmit) {
		this.glDateSubmit = glDateSubmit;
	}
	public String getEmpcd() {
		return empcd;
	}
	public void setEmpcd(String empcd) {
		this.empcd = empcd;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAttend() {
		return attend;
	}
	public void setAttend(String attend) {
		this.attend = attend;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
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
	public List<String> getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(List<String> deptCode) {
		this.deptCode = deptCode;
	}
	public List<Long> getAppramt() {
		return appramt;
	}
	public void setAppramt(List<Long> appramt) {
		this.appramt = appramt;
	}
	public List<String> getDescription() {
		return description;
	}
	public void setDescription(List<String> description) {
		this.description = description;
	}
	public List<String> getProjectName() {
		return projectName;
	}
	public void setProjectName(List<String> projectName) {
		this.projectName = projectName;
	}
	public List<String> getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(List<String> projectNumber) {
		this.projectNumber = projectNumber;
	}
	public List<String> getProjectType() {
		return projectType;
	}
	public void setProjectType(List<String> projectType) {
		this.projectType = projectType;
	}
	public List<String> getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(List<String> projectCode) {
		this.projectCode = projectCode;
	}
	public List<String> getProjectStatusCodeNm() {
		return projectStatusCodeNm;
	}
	public void setProjectStatusCodeNm(List<String> projectStatusCodeNm) {
		this.projectStatusCodeNm = projectStatusCodeNm;
	}
	public List<Long> getProjectId() {
		return projectId;
	}
	public void setProjectId(List<Long> projectId) {
		this.projectId = projectId;
	}
	public List<Long> getTaskId() {
		return taskId;
	}
	public void setTaskId(List<Long> taskId) {
		this.taskId = taskId;
	}
	public List<String> getTaskName() {
		return taskName;
	}
	public void setTaskName(List<String> taskName) {
		this.taskName = taskName;
	}
	public List<String> getTaskNumber() {
		return taskNumber;
	}
	public void setTaskNumber(List<String> taskNumber) {
		this.taskNumber = taskNumber;
	}
	public List<String> getExpOrgName() {
		return expOrgName;
	}
	public void setExpOrgName(List<String> expOrgName) {
		this.expOrgName = expOrgName;
	}
	public List<Long> getExpOrgId() {
		return expOrgId;
	}
	public void setExpOrgId(List<Long> expOrgId) {
		this.expOrgId = expOrgId;
	}
	public List<String> getSegment2() {
		return segment2;
	}
	public void setSegment2(List<String> segment2) {
		this.segment2 = segment2;
	}
	public List<String> getExpType() {
		return expType;
	}
	public void setExpType(List<String> expType) {
		this.expType = expType;
	}
	public List<String> getMainAccCode() {
		return mainAccCode;
	}
	public void setMainAccCode(List<String> mainAccCode) {
		this.mainAccCode = mainAccCode;
	}
	public List<String> getSubAccCode() {
		return subAccCode;
	}
	public void setSubAccCode(List<String> subAccCode) {
		this.subAccCode = subAccCode;
	}
	public List<String> getAttributeCategory() {
		return attributeCategory;
	}
	public void setAttributeCategory(List<String> attributeCategory) {
		this.attributeCategory = attributeCategory;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestAdjust [");
		if (seq != null)
			builder.append("seq=").append(seq).append(", ");
		if (hasVat != null)
			builder.append("hasVat=").append(hasVat).append(", ");
		if (adjustType != null)
			builder.append("adjustType=").append(adjustType).append(", ");
		if (cardno != null)
			builder.append("cardno=").append(cardno).append(", ");
		if (glDateSubmit != null)
			builder.append("glDateSubmit=").append(glDateSubmit).append(", ");
		if (empcd != null)
			builder.append("empcd=").append(empcd).append(", ");
		if (empId != null)
			builder.append("empId=").append(empId).append(", ");
		if (userId != null)
			builder.append("userId=").append(userId).append(", ");
		if (attend != null)
			builder.append("attend=").append(attend).append(", ");
		if (jwt != null)
			builder.append("jwt=").append(jwt).append(", ");
		if (fileId != null)
			builder.append("fileId=").append(fileId).append(", ");
		if (deptCode != null)
			builder.append("deptCode=").append(deptCode).append(", ");
		if (appramt != null)
			builder.append("appramt=").append(appramt).append(", ");
		if (description != null)
			builder.append("description=").append(description).append(", ");
		if (projectName != null)
			builder.append("projectName=").append(projectName).append(", ");
		if (projectNumber != null)
			builder.append("projectNumber=").append(projectNumber).append(", ");
		if (projectType != null)
			builder.append("projectType=").append(projectType).append(", ");
		if (projectCode != null)
			builder.append("projectCode=").append(projectCode).append(", ");
		if (projectStatusCodeNm != null)
			builder.append("projectStatusCodeNm=").append(projectStatusCodeNm).append(", ");
		if (projectId != null)
			builder.append("projectId=").append(projectId).append(", ");
		if (taskId != null)
			builder.append("taskId=").append(taskId).append(", ");
		if (taskName != null)
			builder.append("taskName=").append(taskName).append(", ");
		if (taskNumber != null)
			builder.append("taskNumber=").append(taskNumber).append(", ");
		if (expOrgName != null)
			builder.append("expOrgName=").append(expOrgName).append(", ");
		if (expOrgId != null)
			builder.append("expOrgId=").append(expOrgId).append(", ");
		if (segment2 != null)
			builder.append("segment2=").append(segment2).append(", ");
		if (expType != null)
			builder.append("expType=").append(expType).append(", ");
		if (mainAccCode != null)
			builder.append("mainAccCode=").append(mainAccCode).append(", ");
		if (subAccCode != null)
			builder.append("subAccCode=").append(subAccCode).append(", ");
		if (attributeCategory != null)
			builder.append("attributeCategory=").append(attributeCategory).append(", ");
		if (user != null)
			builder.append("user=").append(user);
		builder.append("]");
		return builder.toString();
	}
}
