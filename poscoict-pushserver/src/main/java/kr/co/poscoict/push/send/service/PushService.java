package kr.co.poscoict.push.send.service;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.posco.securyti.StringCrypto;
import com.spit.es.services.crypt.seed.Seed;

import kr.co.poscoict.push.common.service.CommonService;
import kr.co.poscoict.push.send.mapper.PushMapper;
import kr.co.poscoict.push.send.model.Badge;
import kr.co.poscoict.push.send.model.Count;
import kr.co.poscoict.push.send.model.ExtHost;
import kr.co.poscoict.push.send.model.FamilyPush;
import kr.co.poscoict.push.send.model.MsgType;
import kr.co.poscoict.push.send.model.Push;
import kr.co.poscoict.push.send.model.PushCustomMsg;

/**
 * Push Service
 * @author Sangjun, Park
 *
 */
@Service
public class PushService {
	private final Logger logger = LoggerFactory.getLogger(PushService.class);
	
	@Autowired
	private PushMapper pushMapper;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private XmlMapper xmlMapper;
	
	@Value("${url.erp}")
	private String erpUrl;
	
	@Value("${url.push}")
	private String pushUrl;
	
	@Value("${url.card}")
	private String cardUrl;
	
	@Value("${url.pom}")
	private String pomUrl;
	
	@Value("${url.sec}")
	private String secUrl;
	
	@Value("${url.etc}")
	private String etcUrl;
	
	@Value("${app.id.iphone}")
	private String appIdIphone;
	
	@Value("${app.id.android}")
	private String appIdAndroid;

	/**
	 * 푸시 서버 전송
	 * @param push
	 * @throws Exception 
	 */
	public void push(Push push) throws Exception {
		// badge 카운트 가져오기
		push.setBadge(this.getAllBadgeCount(push.getEmpcd()).getTotalCnt());
		if(StringUtils.isEmpty(push.getEmail())) {
			push.setEmail(this.commonService.getUser(push.getEmpcd()).getEmail());
		}
		FamilyPush fp = this.convertPushToFamily(push);
		logger.debug("PUSH MESSAGE : {}", fp.toString());
		Map<String, List<String>> valueMap = new LinkedHashMap<>();
		@SuppressWarnings("unchecked")
		Map<String, Object> bodyMap = this.xmlMapper.convertValue(fp, Map.class);
		for(Entry<String, Object> entry : bodyMap.entrySet()) {
			valueMap.put(entry.getKey(), Arrays.asList(String.valueOf(entry.getValue())));
		}
		String result = this.restTemplate.postForObject(this.pushUrl, new LinkedMultiValueMap<String, String>(valueMap), String.class);
		logger.info("SEND PUSH COMPLETED - [{}] Target: {}", push.getEmpcd(), push.getTargetUrl());
		logger.debug("PUSH RESPONSE : {}", result);
	}
	
	/**
	 * 패밀리 Push 정보로 변환
	 * @param push
	 * @return
	 */
	private FamilyPush convertPushToFamily(Push push) {
		FamilyPush fp = new FamilyPush(this.appIdIphone, this.appIdAndroid);
		fp.setMailAddress(push.getEmail());
		fp.setAprvCount(push.getBadge());
		fp.setAprvzMessage(push.getMessage());
		// 알림상태에 따라 del로 수정
		if(push.isEnableSound() && this.pushMapper.selectPushEnable(push.getEmpcd())) {
			fp.setAppSound(true);
			fp.setArpvMsgType(MsgType.add);
		} else {
			fp.setAppSound(false);
			fp.setArpvMsgType(MsgType.del);
		}
		PushCustomMsg cm = new PushCustomMsg();
		cm.setRedirectUrl(push.getTargetUrl());
		fp.setCustomMsg(cm);
		return fp;
	}

	/**
	 * 뱃지 건수 조회
	 * @param empcd
	 * @return Badge
	 * @throws Exception 
	 */
	public Badge getAllBadgeCount(String empcd) {
		Badge badge = new Badge();
		badge.setErpCnt(this.getBadgeCount(empcd, ExtHost.ERP));
		badge.setCardCnt(this.getBadgeCount(empcd, ExtHost.CARD));
		badge.setPomCnt(this.getBadgeCount(empcd, ExtHost.POM));
		badge.setSecCnt(this.getBadgeCount(empcd, ExtHost.SECURITY));
		badge.setEtcCnt(this.getBadgeCount(empcd, ExtHost.ETC));
		return badge;
	}
	
	/**
	 * 업무서버별 뱃지건수 조회
	 * @param empcd
	 * @param extHost
	 * @return
	 * @throws Exception 
	 */
	public int getBadgeCount(String empcd, ExtHost extHost) {
		StringBuilder hostUrl = new StringBuilder();
		switch(extHost) {
		case ERP:
			hostUrl.append(erpUrl);
			break;
		case CARD:
			hostUrl.append(cardUrl);
			break;
		case POM:
			hostUrl.append(pomUrl);
			break;
		case SECURITY:
			hostUrl.append(secUrl);
			break;
		case ETC:
			hostUrl.append(etcUrl);
			break;
		}
		try {
			if(extHost == ExtHost.ERP) {
				String xmlStr = this.restTemplate.getForObject(hostUrl.append(new Seed().crypt(empcd)).toString(), String.class);
				return StringUtils.isNotEmpty(xmlStr) ? this.xmlMapper.readValue(xmlStr, Count.class).getValue() : 0;
			} else {
				return this.restTemplate.getForObject(
						hostUrl.append("/badges/").append(extHost.toString()).append("/")
						.append(URLEncoder.encode(StringCrypto.encrypt(empcd), "UTF-8")).toString(),
						Integer.class);
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}
}
