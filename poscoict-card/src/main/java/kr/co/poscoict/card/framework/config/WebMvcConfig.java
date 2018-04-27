package kr.co.poscoict.card.framework.config;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ErrorPage;
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
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

import kr.co.poscoict.card.common.model.ErrorResponse;
import kr.co.poscoict.card.framework.filter.HeaderHttpMethodOverrideFilter;

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
	 * XSS filter
	 * @return
	 */
	@Bean
	public XssEscapeServletFilter xssEscapeServletFilter() {
		return new XssEscapeServletFilter();
	}
	
	/**
	 * Header HttpMethod Override Filter
	 * @return
	 */
	@Bean
	public HeaderHttpMethodOverrideFilter headerHttpMethodOverrideFilter() {
		return new HeaderHttpMethodOverrideFilter();
	}
	
	/**
	 * Filter 등록
	 * @return
	 */
	@Bean
	public FilterRegistrationBean FilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(characterEncodingFilter());
		filterRegistrationBean.setFilter(headerHttpMethodOverrideFilter());
		filterRegistrationBean.setFilter(xssEscapeServletFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
	
	/**
	 * Custom Error page
	 * @return
	 */
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return (container -> {
			container.addErrorPages(
					new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400"),
					new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401"),
					new ErrorPage(HttpStatus.FORBIDDEN, "/error/403"),
					new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"),
					new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500")
					);
		});
	}
}