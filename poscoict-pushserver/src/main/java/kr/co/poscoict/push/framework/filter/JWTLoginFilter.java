package kr.co.poscoict.push.framework.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posco.securyti.StringCrypto;

import kr.co.poscoict.push.common.model.User;
import kr.co.poscoict.push.framework.model.AccountCredentials;
import kr.co.poscoict.push.framework.security.service.TokenAuthenticationService;

/**
 * JWT Login Filter
 * @author Sangjun, Park
 *
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	private final Logger logger = LoggerFactory.getLogger(JWTLoginFilter.class);
	
	public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(url));
		this.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		try {
			AccountCredentials creds = new ObjectMapper().readValue(request.getInputStream(), AccountCredentials.class);
			creds.setEncodedId(StringCrypto.decrypt(URLDecoder.decode(creds.getEncodedId(), "UTF-8")));
			return this.getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEncodedId(), "", Collections.emptyList())
				);
		} catch (AuthenticationException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BadCredentialsException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// 인증 성공시 response header에 토큰을 보낸다.
		TokenAuthenticationService.addAuthentication(response, (User) authResult.getPrincipal());
	}
}
