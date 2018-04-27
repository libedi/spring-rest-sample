package kr.co.poscoict.card.common.model;

/**
 * 코드
 * @author Sangjun, Park
 *
 */
public class Code {
	private String code;
	private String codeNm;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeNm() {
		return codeNm;
	}
	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Code [");
		if (code != null)
			builder.append("code=").append(code).append(", ");
		if (codeNm != null)
			builder.append("codeNm=").append(codeNm);
		builder.append("]");
		return builder.toString();
	}
}
