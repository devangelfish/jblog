package com.bitacademy.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.bitacademy.config.web.FileUploadConfig;
import com.bitacademy.config.web.MessageSourceConfig;
import com.bitacademy.config.web.MvcConfig;
import com.bitacademy.config.web.SecurityConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.bitacademy.jblog.controller", "com.bitacademy.jblog.exception"})
@Import({MvcConfig.class, SecurityConfig.class, MessageSourceConfig.class, FileUploadConfig.class})
public class WebConfig {

}
