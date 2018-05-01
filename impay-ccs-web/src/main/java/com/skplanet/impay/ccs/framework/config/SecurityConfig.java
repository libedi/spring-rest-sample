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

import com.skplanet.impay.ccs.framework.security.crypto.password.Sha256PasswordEncoder;
import com.skplanet.impay.ccs.framework.security.handler.CustomAccessDeniedHandler;
import com.skplanet.impay.ccs.framework.security.handler.CustomFailureHandler;
import com.skplanet.impay.ccs.framework.security.handler.CustomSuccessHandler;
import com.skplanet.impay.ccs.framework.security.service.AjaxAwareAuthenticationEntryPoint;
import com.skplanet.impay.ccs.framework.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired 
    PasswordEncoder passwordEncoder;
    
    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }
    
    @Bean
    public AuthenticationSuccessHandler successHandler(){
    	CustomSuccessHandler customSuccessHandler = new CustomSuccessHandler();
    	return customSuccessHandler;
    }
    
    @Bean
    public AuthenticationFailureHandler failureHandler(){
    	CustomFailureHandler customFailureHandler = new CustomFailureHandler();
    	return customFailureHandler;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
    	return new Sha256PasswordEncoder();
    }
    
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService()).passwordEncoder(passwordEncoder());
	};
    
    @Override
    public void configure(WebSecurity web) throws Exception {
    	//resource 디렉토리에 대한 인증은 무시
        web.ignoring().antMatchers("/resources/**").antMatchers("/healthcheck.jsp").antMatchers("/robots.txt");
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {        
        http
			.headers()
				.httpStrictTransportSecurity()
				.xssProtection()
				.and()
			.exceptionHandling()
	        	.authenticationEntryPoint(new AjaxAwareAuthenticationEntryPoint("/login/form"))
	        	.accessDeniedHandler(new CustomAccessDeniedHandler())
	        	.and()
		    .authorizeRequests()
	            .antMatchers("/login/**").permitAll()
	            .antMatchers("/resources/**").permitAll()
	            .anyRequest().authenticated()
		        .and()
	        .formLogin()
	            .loginPage("/login/form")
	            .usernameParameter("username")
	            .passwordParameter("password")
	            .successHandler(new CustomSuccessHandler())
	            .failureHandler(new CustomFailureHandler())
	            .and()
	        .logout()
	            .logoutUrl("/login/logout")
	            .logoutSuccessUrl("/login/form").permitAll()
	            .and()
	        .csrf()
	            .disable()
		    .rememberMe()
	            .disable();
	        //.sessionManagement()
	        //	.maximumSessions(1); //여러명 로그인 테스트시 주석처리필요 - 보안점검으로 제한설정, 운영시는 제한필요
    }
}