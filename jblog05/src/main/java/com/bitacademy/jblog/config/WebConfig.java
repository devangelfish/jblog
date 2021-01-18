package com.bitacademy.jblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bitacademy.security.AuthInterceptor;
import com.bitacademy.security.LoginInterceptor;
import com.bitacademy.security.LogoutInterceptor;

@Configuration
@PropertySource("classpath:config/webconfig.properties")
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private Environment env;
	
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
				.excludePathPatterns(env.getProperty("security.login-url"))
				.excludePathPatterns(env.getProperty("security.logout-url"))
				.excludePathPatterns(env.getProperty("web.statics") + "/**");
		
		registry
				.addInterceptor(loginInterceptor())
				.addPathPatterns(env.getProperty("security.login-url"));
				
		registry
				.addInterceptor(logoutInterceptor())
				.addPathPatterns(env.getProperty("security.logout-url"));
	}
	
	// MVC Resource Mapping(URL Magic Mapping)
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler(env.getProperty("resource.mapping"))
			.addResourceLocations("file:" + env.getProperty("resource.locations"));
	}	
}
