package kr.co.poscoict.card.framework.config;

import java.nio.charset.Charset;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate Configuration
 * @author Sangjun, Park
 *
 */
@Configuration
public class RestTemplateConfig {
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplateBuilder()
				.requestFactory(clientHttpRequestFactory())
				.build();
		for(HttpMessageConverter<?> messageConverter : restTemplate.getMessageConverters()) {
			if(messageConverter instanceof AllEncompassingFormHttpMessageConverter) {
				((AllEncompassingFormHttpMessageConverter) messageConverter).setCharset(Charset.forName("UTF-8"));
				((AllEncompassingFormHttpMessageConverter) messageConverter).setMultipartCharset(Charset.forName("UTF-8"));
			}
		}
		return restTemplate;
	}
	
	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		clientHttpRequestFactory.setReadTimeout(1000 * 10);	// 10초
		clientHttpRequestFactory.setConnectTimeout(1000 * 10);
		return clientHttpRequestFactory;
	}
	
}