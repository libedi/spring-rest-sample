package kr.co.poscoict.push.framework.config;

import javax.servlet.http.HttpServletResponse;

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

import kr.co.poscoict.push.framework.filter.JWTAuthenticationFilter;
import kr.co.poscoict.push.framework.filter.JWTLoginFilter;
import kr.co.poscoict.push.framework.security.service.PoscoUserService;

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
					resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					resp.getWriter().flush();
				})
				.accessDeniedHandler((req, resp, e) -> {
					resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
					resp.getWriter().flush();
				})
				.and()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/auth").permitAll()
				.antMatchers("/badges/**").permitAll()
				.antMatchers("/healthcheck").permitAll()
				.antMatchers(
						"/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
						"/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagge‌​r-ui.html",
						"/swagger-resources/configuration/security").permitAll()
				.anyRequest().authenticated()
				.and()
			.addFilterBefore(new JWTLoginFilter("/auth", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/badges/**");
	}
}
