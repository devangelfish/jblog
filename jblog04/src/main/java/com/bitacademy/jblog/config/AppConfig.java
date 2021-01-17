package com.bitacademy.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.bitacademy.config.app.DBConfig;
import com.bitacademy.config.app.MyBatisConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.bitacademy.jblog.service", "com.bitacademy.jblog.repository"})
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {

}
