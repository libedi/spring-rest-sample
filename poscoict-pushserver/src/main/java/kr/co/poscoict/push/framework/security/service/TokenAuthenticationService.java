package kr.co.poscoict.push.framework.security.service;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.poscoict.push.common.model.User;

/**
 * Token Authentication Service
 * @author Sangjun, Park
 *
 */
public class TokenAuthenticationService {
	private static final long EXPIRATION_TIME = 86_400_000;	// 1 day
	private static final String SECRET = "7Y+s7Iqk7L2USUNU7JqpSldUU2VjcmV0S2V5";	// BASE64 encoded SecretKey
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";
	private static final String SUBJECT = "TokenForSendingPush";
	private static final String EMPCD = "empcd";
	private static final String EMPID = "empId";
	private static final String EMAIL = "email";
	
	/**
	 * 인증 토큰 생성
	 * @param response
	 * @param user
	 */
	public static void addAuthentication(HttpServletResponse response, User user) {
		String JWT = Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setSubject(SUBJECT)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.claim(EMPCD, user.getEmpcd())
				.claim(EMPID, user.getEmpId())
				.claim(EMAIL, user.getEmail())
				.signWith(SignatureAlgorithm.HS256, SECRET)
				.compact();
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}
	
	/**
	 * 토큰에서 인증정보 생성
	 * @param request
	 * @return
	 */
	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if(token != null) {
			Claims claims = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody();
			if(claims.get(EMPCD, String.class) != null) {
				User user = new User();
				user.setEmpcd(claims.get(EMPCD, String.class));
				user.setEmpId(claims.get(EMPID, Integer.class));
				user.setEmail(claims.get(EMAIL, String.class));
				return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			}
		}
		return null;
	}
}
