package com.skplanet.impay.ccs.common.code.domain;

import com.skplanet.impay.ccs.common.code.Code;
import com.skplanet.impay.ccs.common.code.CodeDisplayName;

public enum ImpayBoolean implements Code {

	@CodeDisplayName(codeDisplayName = "참")
	True(0),
	
	@CodeDisplayName(codeDisplayName = "거짓")
	False(1),
	
	;
	
	private ImpayBoolean(final int c) {
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