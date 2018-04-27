package kr.co.poscoict.card.detail.model;

import kr.co.poscoict.card.common.model.Search;

/**
 * Task 조회 DTO
 * @author Sangjun, Park
 *
 */
public class DetailSearch extends Search {
	private String projectId;
	private String projectNumber;

	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
}
