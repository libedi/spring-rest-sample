package kr.co.poscoict.card.batch.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.posco.securyti.StringCrypto;

import kr.co.poscoict.card.batch.mapper.BatchMapper;
import kr.co.poscoict.card.batch.model.Push;
import kr.co.poscoict.card.common.model.User;
import kr.co.poscoict.card.common.util.JwtClientUtil;
import kr.co.poscoict.card.common.util.MessageSourceUtil;

/**
 * Batch Service
 * @author Sangjun, Park
 *
 */
@Service
public class BatchService {
	private final Logger logger = LoggerFactory.getLogger(BatchService.class);
	
	private final String SCHEDULE_MODE = System.getProperty("schedule.mode");
	private final String TARGET_URL = "card";
	
	@Autowired
	private BatchMapper batchMapper;
	
	@Autowired
	private JwtClientUtil jwtClientUtil;
	
	@Autowired
	private MessageSourceUtil messageSource;
	
	@Value("${url.push}")
	private String pushUrl;
	
	/**
	 * 법인카드 푸시 발송 스케쥴러
	 * : 매일 10시 30분, 14시 30분에 실행
	 */
	@Scheduled(cron = "0 30 10,14 * * *")
	public void sendPushSchedule() {
		if(this.isScheduleMode()) {
			logger.info("SCHEDULED SERVICE START : sendPushSchedule");
			try {
				this.sendPush();
				logger.info("SCHEDULED SERVICE FINISH : sendPushSchedule");
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 법인카드 수동 푸시 발송
	 * @throws Exception 
	 */
	@Async
	public void sendPushManual() throws Exception {
		logger.info("MANUALLY SERVICE START : sendPushManual");
		this.sendPush();
		logger.info("MANUALLY SERVICE FINISH : sendPushManual");
	}
	
	/**
	 * 법인카드 푸시 발송
	 * @throws Exception
	 */
	private void sendPush() throws Exception {
		// 푸시 정보 설정
		Push push = new Push();
		push.setMessage(this.messageSource.getMessage("batch.msg.push", Locale.KOREAN));
		push.setTargetUrl(TARGET_URL);
		
		String token = "";
		List<User> userList = this.batchMapper.selectPushTargetUserList();
		for(User user : userList) {
			try {
				if(StringUtils.isEmpty(token)) {
					token = this.jwtClientUtil.getAuthenticationToken(pushUrl, user.getEmpcd());
				}
				push.setEmpcd(user.getEmpcd());
				push.setEmail(user.getEmail());
				this.jwtClientUtil.postForObject(
						new StringBuilder(pushUrl).append("/push/").append(URLEncoder.encode(StringCrypto.encrypt(push.getEmpcd()), "UTF-8"))
						.toString(), push, token, Void.class);
			} catch(RestClientException | IOException e) {
				logger.error("SKIP EXCEPTION : {}", e.getMessage(), e);
				continue;
			} catch(Exception e) {
				throw e;
			}
		}
	}
	
	/**
	 * 스케줄링 모드 여부 확인
	 * @return
	 */
	private boolean isScheduleMode() {
		return SCHEDULE_MODE != null && StringUtils.equalsIgnoreCase(SCHEDULE_MODE, "on");
	}
	
}
