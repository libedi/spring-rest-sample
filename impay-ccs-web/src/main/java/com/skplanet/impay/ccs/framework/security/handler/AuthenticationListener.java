package com.skplanet.impay.ccs.framework.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.framework.security.service.CustomUserDetailsService;

/**
 * AuthenticationListener
 * 
 * @author 
 *
 */
@Component
public class AuthenticationListener implements ApplicationListener <AbstractAuthenticationEvent> {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	public void onApplicationEvent(AbstractAuthenticationEvent appEvent) {
		if (appEvent instanceof AuthenticationSuccessEvent) {
			// 로그인성공시 이벤트 처리
			AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;
			CustomUserDetails userDetails =  (CustomUserDetails) event.getAuthentication().getPrincipal();

			// 비밀번호 오류횟수 초기화
			customUserDetailsService.updateUserPwdErrCntInit(userDetails.getUserId());
		}

		if (appEvent instanceof AuthenticationFailureBadCredentialsEvent) {
			// 인증실패시 이벤트 처리
			AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) appEvent;
			String username = event.getAuthentication().getPrincipal().toString();

			// 비밀번호 오류횟수 업데이트
			customUserDetailsService.updateUserPwdErrCnt(username);
		}
	}
}