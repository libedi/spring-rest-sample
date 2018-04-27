package kr.co.poscoict.card.detail.model;

/**
 * 프로젝트
 * @author Sangjun, Park
 *
 */
public class Project {
	private String projectName;
	private String projectNumber;
	private String projectType;
	private String projectStatusCode;
	private String projectStatusCodeNm;
	private String projectId;
	
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
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getProjectStatusCode() {
		return projectStatusCode;
	}
	public void setProjectStatusCode(String projectStatusCode) {
		this.projectStatusCode = projectStatusCode;
	}
	public String getProjectStatusCodeNm() {
		return projectStatusCodeNm;
	}
	public void setProjectStatusCodeNm(String projectStatusCodeNm) {
		this.projectStatusCodeNm = projectStatusCodeNm;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Project [");
		if (projectName != null)
			builder.append("projectName=").append(projectName).append(", ");
		if (projectNumber != null)
			builder.append("projectNumber=").append(projectNumber).append(", ");
		if (projectType != null)
			builder.append("projectType=").append(projectType).append(", ");
		if (projectStatusCode != null)
			builder.append("projectStatusCode=").append(projectStatusCode).append(", ");
		if (projectStatusCodeNm != null)
			builder.append("projectStatusCodeNm=").append(projectStatusCodeNm).append(", ");
		if (projectId != null)
			builder.append("projectId=").append(projectId);
		builder.append("]");
		return builder.toString();
	}
}
