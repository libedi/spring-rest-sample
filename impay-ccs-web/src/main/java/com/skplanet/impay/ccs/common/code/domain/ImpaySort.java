package com.skplanet.impay.ccs.common.code.domain;

import com.skplanet.impay.ccs.common.code.Code;
import com.skplanet.impay.ccs.common.code.CodeDisplayName;

public enum ImpaySort implements Code {

	@CodeDisplayName(codeDisplayName = "오름차순")
	Asc(0),
	
	@CodeDisplayName(codeDisplayName = "내림차순")
	Desc(1),
	
	;
	
	private ImpaySort(final int c) {
		this.code = c;
	}
	
	private int code;
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
}