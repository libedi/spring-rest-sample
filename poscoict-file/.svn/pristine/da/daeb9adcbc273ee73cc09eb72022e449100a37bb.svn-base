package kr.co.poscoict.file.framework.config;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.poscoict.file.common.model.ErrorResponse;

/**
 * Web Application MVC Configuration
 * @author libedi
 *
 */
@Configuration
@EnableConfigurationProperties
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * MessageSource 설정
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.messages")
	public MessageSource messageSource() {
		return new ResourceBundleMessageSource();
	}
	
	/**
	 * Locale 설정
	 * @return
	 */
	@Bean(name = "localeResolver")
	public LocaleResolver sessionLocaleResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.KOREAN);
		return localeResolver;
	}
	
	/**
	 * Locale 변경설정
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}
	
	/**
	 * Validator 설정
	 * @return
	 */
	@Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
    	LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    	validator.setValidationMessageSource(messageSource());
    	return validator;
    }
	
	/**
	 * Default Error Attributes 설정
	 * @return
	 */
	@Bean
	public ErrorAttributes errorAttribute() {
		return new DefaultErrorAttributes() {
			@SuppressWarnings("unchecked")
			@Override
			public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes,
					boolean includeStackTrace) {
				Integer status = (Integer) requestAttributes.getAttribute("javax.servlet.error.status_code", 0);
				return objectMapper.convertValue(
						new ErrorResponse(status == null ? "None" : HttpStatus.valueOf(status.intValue()).getReasonPhrase()),
						Map.class
						);
			}
		};
	}
	
	/**
	 * CharacterEncodingFilter 설정
	 * @return
	 */
	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}
	
	/**
	 * Filter 등록
	 * @return
	 */
	@Bean
	public FilterRegistrationBean FilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(characterEncodingFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
}