package com.favccxx.dubbo.storage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

@Configuration
public class MyBatisPlusConfig {

	private final static Logger logger = LoggerFactory.getLogger(MyBatisPlusConfig.class);

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		logger.debug("注册分页插件");
		return new PaginationInterceptor();
	}

	@Bean
	@Profile({ "test" }) // 设置 dev test 环境开启
	public PerformanceInterceptor performanceInterceptor() {
		return new PerformanceInterceptor();
	}

}
