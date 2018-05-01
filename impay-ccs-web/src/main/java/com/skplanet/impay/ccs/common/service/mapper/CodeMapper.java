package com.skplanet.impay.ccs.common.service.mapper;

import java.util.List;
import com.skplanet.impay.ccs.common.model.Code;

public interface CodeMapper {
	List<Code> selectCodeByUprCd(String uprCd);
	List<Code> selectSCodeByUprCd(String uprCd);
	Code selectCode(String cd);
}
