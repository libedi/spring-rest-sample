package kr.co.poscoict.push.framework.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * RestTemplate Configuration
 * @author Sangjun, Park
 *
 */
@Configuration
public class RestTemplateConfig {
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder()
				.requestFactory(clientHttpRequestFactory())
				.build();
	}
	
	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		clientHttpRequestFactory.setReadTimeout(1000 * 5);
		clientHttpRequestFactory.setConnectTimeout(1000 * 5);
		return clientHttpRequestFactory;
	}
	
	@Bean
	public XmlMapper xmlMapper() {
		return new XmlMapper();
	}
	
}