package kr.co.poscoict.card.common.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.posco.securyti.StringCrypto;

import kr.co.poscoict.card.common.model.Badge;
import kr.co.poscoict.card.common.model.User;
import kr.co.poscoict.card.common.service.CommonService;

/**
 * Common Api Controller
 * @author Sangjun, Park
 *
 */
@RestController
public class CommonApiController {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 전체 뱃지 건수 조회
	 * @return
	 * @throws Exception 
	 * @throws RestClientException 
	 */
	@GetMapping("/badges")
	@ResponseStatus(HttpStatus.OK)
	public Badge getAllBadgeCount(@AuthenticationPrincipal User user) throws RestClientException, Exception {
		return this.commonService.getAllBadgeCount(user.getEmpcd());
	}
	
	/**
	 * 법인카드 뱃지 건수 조회
	 * @param encodedEmpcd 암호화된 직번
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@GetMapping("/badges/card/{encodedEmpcd}")
	@ResponseStatus(HttpStatus.OK)
	public int getBadgeCount(@PathVariable(value = "encodedEmpcd") String encodedEmpcd) throws UnsupportedEncodingException, Exception {
		return this.commonService.getBadgeCount(StringCrypto.decrypt(URLDecoder.decode(encodedEmpcd, "UTF-8")));
	}
	
	/**
	 * 업무서버 헬스체크
	 * @param url
	 */
	@GetMapping("/healthCheck")
	@ResponseStatus(HttpStatus.OK)
	public void healthCheck(@RequestParam String url) {
		this.restTemplate.getForObject(new StringBuilder(url).append("/healthcheck.html").toString(), String.class);
	}
}
