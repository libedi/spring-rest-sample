/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.framework.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class MyBatisConfig {


	@MapperScan(basePackages = {"com.skplanet.impay.ccs*"}, sqlSessionTemplateRef = "paySqlSessionTemplate")
	static class PaySqlSessionFactory {

		@Autowired
		private DataSource payDatasource;

		@Bean
		public SqlSessionFactory paySqlSessionFactory() throws Exception {
			SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
			sqlSessionFactory.setDataSource(payDatasource);
			/**
			 * Spring의 자체 버그로 인해 ConfigurationProperties를 해도 반영이 안됨
			 */
			sqlSessionFactory.setConfigLocation(new ClassPathResource("MapperConfig.xml"));
			sqlSessionFactory.afterPropertiesSet();
			return sqlSessionFactory.getObject();
		}


		@Bean
		public SqlSessionTemplate paySqlSessionTemplate() throws Exception {
			return new SqlSessionTemplate(paySqlSessionFactory());
		}

		@Bean
		@Primary
		public DataSourceTransactionManager payTransactionManager() {
			return new DataSourceTransactionManager(payDatasource);
		}
	}

	@MapperScan(basePackages = "com.skplanet.impay.rms*", sqlSessionTemplateRef = "rmSqlSessionTemplate")
	static class RmSqlSessionFactory {

		@Resource(name="rmDatasource")
		private DataSource rmDatasource;

		@Bean
		public SqlSessionFactory rmSqlSessionFactory() throws Exception {
			SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
			sqlSessionFactory.setDataSource(rmDatasource);
			/**
			 * Spring의 자체 버그로 인해 ConfigurationProperties를 해도 반영이 안됨
			 */
			sqlSessionFactory.setConfigLocation(new ClassPathResource("MapperConfig.xml"));
			sqlSessionFactory.afterPropertiesSet();
			return sqlSessionFactory.getObject();
		}


		@Bean
		public SqlSessionTemplate rmSqlSessionTemplate() throws Exception {
			return new SqlSessionTemplate(rmSqlSessionFactory());
		}

		@Bean
		public DataSourceTransactionManager rmTransactionManager() {
			return new DataSourceTransactionManager(rmDatasource);
		}
	}
}