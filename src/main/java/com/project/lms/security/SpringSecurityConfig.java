package com.project.lms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.lms.service.implementation.CustomUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Bean
	public CustomUserDetailsServiceImpl getUserService() {
		return new CustomUserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserService());
		daoAuthenticationProvider.setPasswordEncoder(this.getPasswordEncoder());
		return daoAuthenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(this.getAuthenticationProvider());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		 http.authorizeRequests().antMatchers("/**").permitAll().and().csrf().disable();
		
		  http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
		  .antMatchers(HttpMethod.POST,"/user").permitAll()
		  .antMatchers("/user/**").hasAnyRole("USER") .antMatchers("/**").permitAll()
		  .and().formLogin().loginPage("/login") .successHandler(loginSuccessHandler)
		  .and().csrf().disable() ;
//		
		

	}

}