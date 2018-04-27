package kr.co.poscoict.card.common.service;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.posco.securyti.StringCrypto;

import kr.co.poscoict.card.common.mapper.CommonMapper;
import kr.co.poscoict.card.common.model.Badge;
import kr.co.poscoict.card.common.model.HostInfo;
import kr.co.poscoict.card.common.model.User;

/**
 * Badge Service
 * @author Sangjun, Park
 *
 */
@Service
public class CommonService {
	@Autowired
	private CommonMapper commonMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${url.push}")
	private String pushUrl;
	
	@Value("${url.home}")
	private String homeUrl;
	
	@Value("${url.erp}")
	private String erpUrl;
	
	@Value("${url.card}")
	private String cardUrl;
	
	@Value("${url.work}")
	private String workUrl;
	
	@Value("${url.bs-trip}")
	private String bsTripUrl;
	
	@Value("${url.pom}")
	private String pomUrl;
	
	@Value("${url.sec}")
	private String secUrl;
	
	@Value("${url.etc}")
	private String etcUrl;
	
	/**
	 * 사용자 정보 조회
	 * @param empcd
	 * @return
	 */
	public User getUser(String empcd) {
		return this.commonMapper.selectUser(empcd);
	}
	
	/**
	 * 전체 뱃지 건수 조회
	 * @param empcd 
	 * @return
	 * @throws Exception 
	 * @throws RestClientException 
	 */
	public Badge getAllBadgeCount(String empcd) throws RestClientException, Exception {
		return this.restTemplate.getForObject(
				new StringBuilder().append(this.pushUrl).append("/badges/")
				.append(URLEncoder.encode(StringCrypto.encrypt(empcd), "UTF-8")).toString(),
				Badge.class);
	}
	
	/**
	 * 법인카드 뱃지 건수 조회(정산대상 건수 조회)
	 * @param empcd
	 * @return
	 */
	public int getBadgeCount(String empcd) {
		return this.commonMapper.selectTargetCount(empcd);
	}
	
	/**
	 * 외부 호스트 URL 조회
	 * @return
	 */
	public HostInfo getHostUrl() {
		HostInfo urls = new HostInfo();
		urls.setHomeUrl(homeUrl);
		urls.setPushUrl(pushUrl);
		urls.setErpUrl(erpUrl);
		urls.setCardUrl(cardUrl);
		urls.setWorkUrl(workUrl);
		urls.setBsTripUrl(bsTripUrl);
		urls.setPomUrl(pomUrl);
		urls.setSecUrl(secUrl);
		urls.setEtcUrl(etcUrl);
		return urls;
	}
}
