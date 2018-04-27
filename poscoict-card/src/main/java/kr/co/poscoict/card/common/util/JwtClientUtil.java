package kr.co.poscoict.card.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import kr.co.poscoict.card.common.model.User;

/**
 * API request Util with JWT
 * @author Sangjun, Park
 *
 */
@Component
public class JwtClientUtil {
	private static final String AUTHORIZATION_HEADER = "Authorization";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private EncoderUtil encoderUtil;
	
	/**
	 * 인증토큰 조회
	 * @param apiServerUrl
	 * @return
	 * @throws Exception
	 */
	public String getAuthenticationToken(String apiServerUrl) throws Exception {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return this.getAuthenticationToken(apiServerUrl, user.getEmpcd());
	}
	
	/**
	 * 인증토큰 조회
	 * @param apiServerUrl
	 * @param empcd
	 * @return
	 * @throws Exception
	 */
	public String getAuthenticationToken(String apiServerUrl, String empcd) throws Exception {
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("encodedId", this.encoderUtil.encodeByFamily(empcd));
		ResponseEntity<Void> resp = this.restTemplate.postForEntity(apiServerUrl + "/auth", requestMap, Void.class);
		if(resp.getStatusCode() == HttpStatus.OK) {
			String authHeader = resp.getHeaders().getFirst(AUTHORIZATION_HEADER);
			if(StringUtils.isNotEmpty(authHeader)) {
				return authHeader;
			}
		}
		return "";
	}
	
	
	/**
	 * API 요청 - GET
	 * @param apiUrl
	 * @param body
	 * @param token
	 * @param responseType
	 * @return
	 */
	public <T> ResponseEntity<T> getForEntity(String apiUrl, Object body, String token, Class<T> responseType) {
		return this.requestWithToken(apiUrl, body, token, HttpMethod.GET, responseType);
	}
	
	/**
	 * API 요청 - GET
	 * @param apiUrl
	 * @param body
	 * @param token
	 * @param responseType
	 * @return
	 */
	public <T> T getForObject(String apiUrl, Object body, String token, Class<T> responseType) {
		ResponseEntity<T> resp = this.getForEntity(apiUrl, body, token, responseType);
		return resp.getStatusCode() == HttpStatus.OK ? resp.getBody() : null;
	}
	
	/**
	 * API 요청 - POST
	 * @param apiUrl
	 * @param body
	 * @param token
	 * @param responseType
	 * @return
	 */
	public <T> ResponseEntity<T> postForEntity(String apiUrl, Object body, String token, Class<T> responseType) {
		return this.requestWithToken(apiUrl, body, token, HttpMethod.POST, responseType);
	}
	
	/**
	 * API 요청 - POST
	 * @param apiUrl
	 * @param body
	 * @param token
	 * @param responseType
	 * @return
	 */
	public <T> T postForObject(String apiUrl, Object body, String token, Class<T> responseType) {
		ResponseEntity<T> resp = this.postForEntity(apiUrl, body, token, responseType);
		return resp.getStatusCode() == HttpStatus.OK ? resp.getBody() : null;
	}
	
	/**
	 * API 요청 - PUT
	 * @param apiUrl
	 * @param body
	 * @param token
	 * @param responseType
	 * @return
	 */
	public <T> ResponseEntity<T> putForEntity(String apiUrl, Object body, String token, Class<T> responseType) {
		return this.requestWithToken(apiUrl, body, token, HttpMethod.PUT, responseType);
	}
	
	/**
	 * API 요청 - PUT
	 * @param apiUrl
	 * @param body
	 * @param token
	 * @param responseType
	 * @return
	 */
	public <T> T putForObject(String apiUrl, Object body, String token, Class<T> responseType) {
		ResponseEntity<T> resp = this.putForEntity(apiUrl, body, token, responseType);
		return resp.getStatusCode() == HttpStatus.OK ? resp.getBody() : null;
	}
	
	/**
	 * API 요청 - DELETE
	 * @param apiUrl
	 * @param body
	 * @param token
	 * @param responseType
	 * @return
	 */
	public <T> ResponseEntity<T> delete(String apiUrl, Object body, String token, Class<T> responseType) {
		return this.requestWithToken(apiUrl, body, token, HttpMethod.DELETE, responseType);
	}
	
	/**
	 * API 요청 - DELETE
	 * @param apiUrl
	 * @param body
	 * @param token
	 * @param responseType
	 * @return
	 */
	public void delete(String apiUrl, Object body, String token) {
		this.delete(apiUrl, body, token, Void.class);
	}
	
	/**
	 * API 요청
	 * @param apiUrl
	 * @param body
	 * @param token
	 * @param method
	 * @param responseType
	 * @return
	 */
	public <T> ResponseEntity<T> requestWithToken(String apiUrl, Object body, String token, HttpMethod method, Class<T> responseType) {
		Assert.hasLength(token, "Authentication token must be not empty.");
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION_HEADER, token);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return this.restTemplate.exchange(apiUrl, method, new HttpEntity<Object>(body, headers), responseType);
	}
}
