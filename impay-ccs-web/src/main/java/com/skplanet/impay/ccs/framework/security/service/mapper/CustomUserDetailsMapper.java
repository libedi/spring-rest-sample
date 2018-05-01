package com.skplanet.impay.ccs.framework.security.service.mapper;

import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;

public interface CustomUserDetailsMapper {
    CustomUserDetails selectUserByUsername(String username);
    
	/**
	 * 사용자 비밀번호 오류횟수 수정
	 * 
	 * @param param
	 * @return
	 */
	Integer updateUserPwdErrCnt(String username);
	
	/**
	 * 사용자 비밀번호 오류횟수 초기화
	 * 
	 * @param param
	 * @return
	 */
	Integer updateUserPwdErrCntInit(String username);
	
	/**
	 * 사용자 비밀번호 오류횟수 수정 & ID정지
	 * 
	 * @param param
	 * @return
	 */
	Integer updateUserPwdErrCntAndIdStop(String username);
}
