/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.rpm.service.mapper;

import org.apache.ibatis.session.ResultHandler;

import com.skplanet.impay.ccs.rpm.model.RpmSearch;
import com.skplanet.impay.ccs.rpm.model.TjurRcpt;
import com.skplanet.impay.ccs.rpm.model.TjurUploadRcpt;

/**
 * 이관 접수 Mapper
 * @author Sangjun, Park
 *
 */
public interface TransferRcptMapper {

	void selectCnslCpTjurListByPaging(RpmSearch rpmSearch, ResultHandler<TjurRcpt> resultHasdler);
	
	int selectCnslCpTjurListCount(RpmSearch rpmSearch);
	
	void selectCnslCpTjurList(RpmSearch rpmSearch, ResultHandler<TjurRcpt> resultHasdler);

	TjurUploadRcpt selectCnslFileUpld(String rcptNo);

	int updateCnslFileUpld(TjurUploadRcpt tjurUploadRcpt);

}
