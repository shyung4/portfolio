package com.naver.phone.controller;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.naver.phone.filter.UserFilter;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	@Bean
	public FilterRegistrationBean<UserFilter> filterBean() {
		FilterRegistrationBean<UserFilter> registrationBean = new FilterRegistrationBean<>(new UserFilter());
		registrationBean.addUrlPatterns("/phone/*");
		registrationBean.setOrder(1);
		
		return registrationBean;
}
	
}
