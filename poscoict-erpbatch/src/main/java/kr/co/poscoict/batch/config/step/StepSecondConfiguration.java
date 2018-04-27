package kr.co.poscoict.batch.config.step;

import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import com.posco.securyti.StringCrypto;

import kr.co.poscoict.batch.model.Push;
import kr.co.poscoict.batch.model.PushInfo;
import kr.co.poscoict.batch.util.JwtClientUtil;
import kr.co.poscoict.batch.util.MessageSourceUtil;

/**
 * Step Configuration - 2: 푸시발송
 * @author Sangjun, Park
 *
 */
@Configuration
public class StepSecondConfiguration {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	@Qualifier(value = "pstSqlSessionFactory")
	private SqlSessionFactory pstSqlSessionFactory;
	
	@Autowired
	private ErpStepExecutionListener stepExecutionListener;
	
	@Autowired
	private JwtClientUtil jwtClientUtil;
	
	@Autowired
	private MessageSourceUtil messageSource;
	
	@Value("#{T(java.lang.Integer).parseInt(${erp.batch.page-size})}")
	private Integer pageSize;
	
	@Value("#{T(java.lang.Integer).parseInt(${erp.batch.commit-interval})}")
	private Integer commitInterval;
	
	@Value("${url.push}")
	private String pushUrl;
	
	@Value("${url.target.erp}")
	private String erpTargetUrl;
	
	/**
	 * 푸시발송 대상조회 ItemReader
	 * @return
	 */
	@Bean
	public ItemReader<PushInfo> selectPushTargetItemReader() {
		MyBatisPagingItemReader<PushInfo> reader = new MyBatisPagingItemReader<>();
		reader.setSqlSessionFactory(pstSqlSessionFactory);
		reader.setQueryId("kr.co.poscoict.batch.pst.mapper.PostgresqlMapper.selectPushTarget");
		reader.setPageSize(pageSize);
		return reader;
	}
	
	/**
	 * 푸시발송 ItemWriter
	 * @return
	 */
	@Bean
	public ItemWriter<PushInfo> sendPushItemWriter() {
		return (items) -> {
			String token = "";
			Push push = new Push();
			for(PushInfo p : items) {
				push.setMessage(this.messageSource.getMessage("msg.push.erp"));
				push.setTargetUrl(this.erpTargetUrl);
				if(StringUtils.isEmpty(token)) {
					token = this.jwtClientUtil.getAuthenticationToken(pushUrl, p.getEmpcd());
				}
				this.jwtClientUtil.postForObject(
						new StringBuilder(pushUrl).append("/push/")
							.append(URLEncoder.encode(StringCrypto.encrypt(p.getEmpcd()), "UTF-8")).toString(),
						push, token, Void.class);
			}
		};
	}
	
	/**
	 * Second Step Configuration
	 * @return
	 */
	@Bean
	public Step secondStep() {
		return this.stepBuilderFactory.get("secondStep")
				.listener(stepExecutionListener)
				.allowStartIfComplete(true)
				.<PushInfo, PushInfo> chunk(commitInterval)
				.reader(selectPushTargetItemReader())
				.writer(sendPushItemWriter())
				.faultTolerant()
				.skip(HttpClientErrorException.class)
				.skip(HttpServerErrorException.class)
				.skip(ResourceAccessException.class)
				.skip(RestClientException.class)
				.skipLimit(10)
				.build();
	}
}
