package com.bitacademy.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bitacademy.security.AuthInterceptor;
import com.bitacademy.security.LoginInterceptor;
import com.bitacademy.security.LogoutInterceptor;

@EnableWebMvc
@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}
	
	@Bean
	public LoginInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
	
	@Bean
	public LogoutInterceptor logoutInterceptor() {
		return new LogoutInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
				.addInterceptor(authInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/user/auth")
				.excludePathPatterns("/user/logout")
				.excludePathPatterns("/assets/**");
		
		registry
				.addInterceptor(loginInterceptor())
				.addPathPatterns("/user/auth");
				
		registry
				.addInterceptor(logoutInterceptor())
				.addPathPatterns("/user/logout");
	}	
}
