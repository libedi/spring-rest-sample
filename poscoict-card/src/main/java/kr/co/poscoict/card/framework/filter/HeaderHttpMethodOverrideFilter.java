package kr.co.poscoict.card.framework.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * AJAX 에서 REST API 호출을 위한 Filter
 * @author Sangjun, Park
 *
 */
public class HeaderHttpMethodOverrideFilter extends OncePerRequestFilter {
	public static final String HTTP_METHOD_OVERRIDE_HEADER_NAME = "X-HTTP-Method-Override";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String headerValue = request.getHeader(HTTP_METHOD_OVERRIDE_HEADER_NAME);
		if(StringUtils.equalsIgnoreCase("POST", request.getMethod()) && StringUtils.isNotEmpty(headerValue)) {
			final HttpServletRequest wrapper = new HttpMethodRequestWrapper(request, headerValue.toUpperCase(Locale.ENGLISH));
			filterChain.doFilter(wrapper, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}
	
	private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {
		private final String method;
		
		public HttpMethodRequestWrapper(HttpServletRequest request, final String method) {
			super(request);
			this.method = method;
		}

		@Override
		public String getMethod() {
			return this.method;
		}
	}
}