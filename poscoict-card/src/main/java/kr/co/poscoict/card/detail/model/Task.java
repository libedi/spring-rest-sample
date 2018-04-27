package kr.co.poscoict.card.detail.model;

/**
 * Task
 * @author Sangjun, Park
 *
 */
public class Task {
	private Long taskId;
	private String taskName;
	private String taskNumber;
	
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Task [");
		if (taskId != null)
			builder.append("taskId=").append(taskId).append(", ");
		if (taskName != null)
			builder.append("taskName=").append(taskName).append(", ");
		if (taskNumber != null)
			builder.append("taskNumber=").append(taskNumber);
		builder.append("]");
		return builder.toString();
	}
}
