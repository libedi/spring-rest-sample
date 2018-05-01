package com.skplanet.impay.ccs.common.code.domain;

import com.skplanet.impay.ccs.common.code.Code;
import com.skplanet.impay.ccs.common.code.CodeAbbr;
import com.skplanet.impay.ccs.common.code.CodeDisplayName;
import com.skplanet.impay.ccs.common.code.CodeUtils;

public enum ImpayCpScale implements Code, CodeAbbr {
	
	@CodeDisplayName(codeDisplayName = "전체")
	all(0, ""),
	
	@CodeDisplayName(codeDisplayName = "대")
	large(1, "SCSCL0"),
	
	@CodeDisplayName(codeDisplayName = "중")
	middle(2, "SCSCM0"),
	
	@CodeDisplayName(codeDisplayName = "소")
	small(3, "SCSCS0"),
	
	;
	
	private ImpayCpScale(final int c, final String abbr) {
		this.code = c;
		this.abbr = abbr;
		CodeUtils.setCodeDisplayNameByAnnotation(ImpayCpScale.class, this);
	}
	
	private int code;
	private String abbr;
	private String codeDisplayName;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getCodeDisplayName() {
		return codeDisplayName;
	}
	public void setCodeDisplayName(String codeDisplayName) {
		this.codeDisplayName = codeDisplayName;
	}
	
	public String getAbbr() {
		return this.abbr;
	}
}