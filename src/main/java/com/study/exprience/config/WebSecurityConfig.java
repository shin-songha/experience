package com.study.exprience.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER").and()
		.withUser("admin").password("password").roles("USER", "ADMIN");
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/").permitAll()
//			.antMatchers("/program/").access("hasRole(ROLE_USER')")
//			.antMatchers("/admin").access("hasRole(ROLE_ADMIN')")
//			.anyRequest().permitAll();
//	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	    	.authorizeRequests()
	        .antMatchers("/").permitAll()
	        .antMatchers("/admin/**").access("hasRole('ADMIN')")
	        .and().formLogin();
	}
}
