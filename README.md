# FavDubbo

基于Dubbo搭建的一套微服务开发框架，SpringBoot + Dubbo + Dubbo admin UI + Seata

# 核心框架
SpringBoot, 人人都懂的Java开发框架

Dubbo, 阿里开源的服务管理神器，通过RPC协议实现服务的输入输出，与Spring框架集成良好。

Dubbo admin UI, Dubbo 服务可视化监控管理工具

Zookeeper, 分布式应用程序协调服务，Dubbo服务的注册中心

Seata, 阿里开源的分布式事务管理组件


安装说明：

1. Zookeeper安装 - 网上有详细的教程，此处略

2. 创建数据库dubboseata，并从sql\dubbo_seata.sql中导入数据

3. 部署Dubbo admin UI, https://github.com/apache/dubbo-admin。  目前最新版本为 V2.8
(Dubbo Admin 从2.7版本开始前后端分离了，虽然部署麻烦了点，但同时也告诉小伙伴们，VUE现在很火，如果你还不了解，建议抽时间学习下。)

4. 安装Seata Server.

5. 将项目导入本地的开发环境, 并启动

6. 测试

http://xx.xx.xx.xx:7778/purchase?username=admin&productCode=C201909010001&amount=2.01

# 使用说明

## Dubbo 知识点

![image](https://github.com/favccxx/FavDubbo/raw/master/images/dubbo.png)

1. Dobbo Provider如果在接口中声明了组和版本等信息，那么在调用的使用，也必须指明服务的组和版本，否则会报错。

@Service(group = "UserGroup", version = "1.0.2")
@Component
public class UserServiceImpl implements UserService {

	@Override
	public String findByUsername(String username) {
		return "hello user, " + username;
	}

}

@Reference(group = "UserGroup", version = "1.0.2", check = false)
UserService userService;


2. 在服务消费方设置@Reference(check = false)，默认是true，如果依赖的服务不可用会报错。在开发阶段关闭它，可以省很多事情。

3. dubbo。protocol.port 设置成 - 1，表示随机端口号，默认从20880开始自增。便于集群部署

## Seata知识点

Seata基于两阶段提交模式设计的，高效，零侵入，设计上包括三个模块TM、RM和TC。

TM(Transaction Manager)：全局事务管理器，控制全局事务边界，负责全局事务开启、全局提交、全局回滚。

RM(Resource Manager)：资源管理器，控制分支事务，负责分支注册、状态汇报，并接收事务协调器的指令，驱动分支（本地）事务的提交和回滚。

TC(Transaction Coordinator)：事务协调器，维护全局事务的运行状态，负责协调并驱动全局事务的提交或回滚。

Seata 架构
![image](https://github.com/favccxx/FavDubbo/raw/master/images/Seata.png)

Seata配置
```java 
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
		factoryBean
				.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"));
		factoryBean.setTransactionFactory(new JdbcTransactionFactory());
		return factoryBean.getObject();
	}

	@Bean
	public GlobalTransactionScanner globalTransactionScanner() {
		return new GlobalTransactionScanner(applicationId, "my_group");
	}

}
```

Seata 官方采用的是阿里的DruidDataSource, 我用的是Spring默认的HikariDataSource数据源，也是OK的。

# 结尾语

虽然Dubbo 微服务的性能很好，能够承受双11那么高的并发量，不如Spring Cloud全家桶那么便捷。在没有特别




