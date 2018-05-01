/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.service.mapper;

import com.skplanet.impay.ccs.common.model.FileInfo;

/**
 * FileMapper interface
 * @author Sangjun, Park
 *
 */
public interface FileMapper {

	/**
	 * 파일 첨부 테이블에 insert
	 * @param fileInfo
	 * @return int
	 */
	int insertAttchFile(FileInfo fileInfo);

}
