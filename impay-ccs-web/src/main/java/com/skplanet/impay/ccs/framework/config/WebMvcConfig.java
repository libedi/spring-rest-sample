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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.skplanet.impay.ccs.framework.interceptor.MenuInterceptor;

/**
 * @author PP58701
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration ir = registry.addInterceptor(menuInterceptor());
		ir.addPathPatterns("/**");
		ir.excludePathPatterns("/login/**");
		ir.excludePathPatterns("/error");
		ir.excludePathPatterns("/error/**");
		ir.excludePathPatterns("/resources/**");
		ir.excludePathPatterns("/favicon.ico");
		ir.excludePathPatterns("/*/*Popup");
		ir.excludePathPatterns("/*/*/*Popup");
		ir.excludePathPatterns("/*/*/*/*Popup");
	}
	
	@Bean
	public MenuInterceptor menuInterceptor() {
		return new MenuInterceptor();
	}
	
	@Bean
	public FreeMarkerConfigurationFactoryBean freemarkerConfiguration(){
		FreeMarkerConfigurationFactoryBean freemarkerConfiguration = new FreeMarkerConfigurationFactoryBean();
		freemarkerConfiguration.setTemplateLoaderPath("classpath:templates");
		freemarkerConfiguration.setDefaultEncoding("UTF-8");
		return freemarkerConfiguration;
	}
}
