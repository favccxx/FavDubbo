package com.favccxx.dubbo.account.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;

import io.seata.rm.datasource.DataSourceProxy;
import io.seata.spring.annotation.GlobalTransactionScanner;

@Configuration
public class SeataConfiguration {

	@Value("${spring.application.name}")
	private String applicationId;

	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.driver-class-name}")
	private String driver;

	@Bean
	@Primary
	public HikariDataSource myHikariDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driver);
		return dataSource;
	}

	@Bean
	public DataSourceProxy dataSourceProxy(HikariDataSource dataSource) {
		return new DataSourceProxy(dataSource);
	}

	@Bean
	public SqlSessionFactory sessionFactory(DataSourceProxy proxy) throws Exception {
		MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
		factoryBean.setDataSource(proxy);
		factoryBean.setTypeAliasesPackage("com.favccxx.dubbo.model");
		factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath*:/com/favccxx/dubbo/account/mapper/*.java"));
		factoryBean.setTransactionFactory(new JdbcTransactionFactory());
		return factoryBean.getObject();
	}

	@Bean
	public GlobalTransactionScanner globalTransactionScanner() {
		return new GlobalTransactionScanner(applicationId, "my_group");
	}

}
