/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.framework.config;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import com.opensymphony.sitemesh.webapp.SiteMeshFilter;
import com.skplanet.impay.framework.log.web.filter.IMPAYLogbackMDCFilter;

/**
 * @author 
 */
@Configuration
public class WebApplicationConfig  {
    
    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(
        		"messages/messages-sysm",
        		"messages/messages-cam",
        		"messages/messages-rpm",
        		"messages/messages",
        		"messages/messages-security",
        		"messages/messages-uim",
        		"messages/messages-csm",
        		"messages/messages-csm-rms"
        );
        
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.KOREAN);
        return slr;
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean
  	public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
    
    @Bean
    public FilterRegistrationBean characterFilterRegistrationBean() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(characterEncodingFilter());
        filter.addUrlPatterns("/*");
        filter.setOrder(Integer.MIN_VALUE);
        return filter;
    }
    
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {    	
    	LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();    	
    	validator.setValidationMessageSource(messageSource());
    	return validator;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(xssEscapeServletFilter());
        filter.setFilter(new SiteMeshFilter());
        filter.addUrlPatterns("/*");
        return filter;
    }
    
    @Bean
    public FilterRegistrationBean logFilterRegistrationBean() {    	
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new IMPAYLogbackMDCFilter());
        filter.addUrlPatterns("/*");
        return filter;
    }
    
	@Bean
	public ServletContextInitializer servletContextInitializer() {
	    return new ServletContextInitializer() {
	        @Override
	        public void onStartup(ServletContext servletContext) throws ServletException {
	            servletContext.getSessionCookieConfig().setName("CCS_JSESSION_ID");
	        }
	    };
	}
	
	@Bean
	public XssEscapeServletFilter xssEscapeServletFilter() {
		XssEscapeServletFilter xssFilter = new XssEscapeServletFilter();
		return xssFilter;
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

	    return new EmbeddedServletContainerCustomizer() {
	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer container) {
	        	ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/common");
	            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/common");
	            ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/error/common");
	            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/common");
	            ErrorPage error405Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error/common");
	            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/common");
	            container.addErrorPages(error400Page, error401Page, error403Page, error404Page, error405Page, error500Page);
	        }
	    };
	}
}
