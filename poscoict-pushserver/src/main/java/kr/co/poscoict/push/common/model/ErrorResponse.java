package kr.co.poscoict.push.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Error Response Model
 * @author Sangjun, Park
 *
 */
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {
	private String errorField;
	private String errorMessage;
	
	public ErrorResponse() {}
	
	public ErrorResponse(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public ErrorResponse(String errorField, String errorMessage) {
		this.errorField = errorField;
		this.errorMessage = errorMessage;
	}

	public String getErrorField() {
		return errorField;
	}
	public void setErrorField(String errorField) {
		this.errorField = errorField;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorResponse [");
		if (errorField != null)
			builder.append("errorField=").append(errorField).append(", ");
		if (errorMessage != null)
			builder.append("errorMessage=").append(errorMessage);
		builder.append("]");
		return builder.toString();
	}
}
