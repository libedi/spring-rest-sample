package kr.co.poscoict.card.framework.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.spit.es.services.crypt.seed.Seed;

/**
 * Login Filter
 * @author Sangjun, Park
 *
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {
	private static final String SSO_PARAMETER = "encodeId";

	public LoginFilter(String url, AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(url));
		this.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String empcd = request.getParameter(SSO_PARAMETER);
		return StringUtils.isNotEmpty(empcd) ? this.getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(new Seed().decrypt(empcd), "", Collections.emptyList())
				)
				: null;
	}
}
