package com.bitacademy.config.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {
	
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.addBasenames("classpath:com/bitacademy/jblog/config/web/properties/messages_ko.properties");
		messageSource.setDefaultEncoding("UTF-8");
		
		return messageSource;	
	}
}
