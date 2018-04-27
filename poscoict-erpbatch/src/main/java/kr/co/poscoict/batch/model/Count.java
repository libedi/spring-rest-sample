package kr.co.poscoict.batch.model;

/**
 * ERP 건수
 * @author Sangjun, Park
 *
 */
public class Count {
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Count [value=").append(value).append("]");
		return builder.toString();
	}
	
}
