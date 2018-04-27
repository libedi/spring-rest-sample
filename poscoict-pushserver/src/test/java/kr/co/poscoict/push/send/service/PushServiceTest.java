package kr.co.poscoict.push.send.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.poscoict.push.send.model.FamilyPush;
import kr.co.poscoict.push.send.model.MsgType;
import kr.co.poscoict.push.send.model.PushCustomMsg;

public class PushServiceTest {
	
	private RestTemplate restTemplate = new RestTemplate();
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void sendPush() throws Exception {
		String pushUrl = "https://tposfm.posco.net:7759/CommonPushServiceFamily/CommonPushService";
//		String url = new StringBuilder(pushUrl)
//				.append("?appIdIphone={0}&appIdAndroid={1}&mailAddress={2}&aprvMsgType={3}&aprvzMessage={4}&aprvCount={5}&appSound={6}&customMsg={7}")
//				.toString();
		PushCustomMsg cus = new PushCustomMsg();
		cus.setRedirectUrl("card");
		FamilyPush push = new FamilyPush("PF0070I020", "PF0070A020");
		push.setMailAddress("namht@poscoict.com");
		push.setArpvMsgType(MsgType.add);
		push.setAppSound(true);
		push.setAprvzMessage("법인카드 정산대상건이 있습니다.");
		push.setAprvCount(16);
		push.setCustomMsg(cus);
		
		Map<String, List<String>> valueMap = new LinkedHashMap<>();
		@SuppressWarnings("unchecked")
		Map<String, Object> bodyMap = this.objectMapper.convertValue(push, Map.class);
		for(Entry<String, Object> entry : bodyMap.entrySet()) {
			valueMap.put(entry.getKey(), Arrays.asList(String.valueOf(entry.getValue())));
		}
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>(valueMap);
		
		
		String result = this.restTemplate.postForObject(pushUrl, 
				map, String.class
//				,
//				push.getAppIdIphone(),
//				push.getAppIdAndroid(),
//				push.getMailAddress(),
//				push.getArpvMsgType(),
//				push.getAprvzMessage(),
//				push.getAprvCount(),
//				push.isAppSound(),
//				push.getCustomMsg()
				);
		System.out.println(result);
	}
}
