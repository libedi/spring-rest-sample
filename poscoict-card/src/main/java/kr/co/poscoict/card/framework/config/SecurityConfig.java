package kr.co.poscoict.card.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import kr.co.poscoict.card.framework.filter.LoginFilter;
import kr.co.poscoict.card.framework.security.service.PoscoUserService;

/**
 * Security Configuration
 * @author Sangjun, Park
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new PoscoUserService();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.exceptionHandling()
				.authenticationEntryPoint((req, resp, e) -> {
					resp.sendRedirect("/error/401");
				})
				.accessDeniedPage("/error/403")
				.and()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				.antMatchers("/batch/**").permitAll()
				.antMatchers("/error/**").permitAll()
				.antMatchers("/resources/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.addFilterBefore(new LoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
			.logout()
				.permitAll()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
			.rememberMe().disable()
			;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/batch/**").antMatchers("/resources/**").antMatchers("/badges/card/**").antMatchers("/healthcheck.html");
	}
	
}
