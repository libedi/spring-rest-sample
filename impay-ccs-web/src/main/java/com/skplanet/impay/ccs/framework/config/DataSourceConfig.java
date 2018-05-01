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

import com.skplanet.impay.ccs.framework.profile.Alpha;
import com.skplanet.impay.ccs.framework.profile.Dev;
import com.skplanet.impay.ccs.framework.profile.Local;
import com.skplanet.impay.ccs.framework.profile.Prod;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.sql.DataSource;

/**
 * @author Jibeom Jung
 */
@Configuration
@EnableConfigurationProperties
public class DataSourceConfig {

    @Dev
    @Local
    @Alpha
    static class DevDataSource {
        @Bean
        @Primary
        @ConfigurationProperties(prefix = "datasource.pay")
        public DataSource payDatasource() {
            return DataSourceBuilder.create().build();
        }


        @Bean
        @ConfigurationProperties(prefix = "datasource.rm")
        public DataSource rmDatasource() {
            return DataSourceBuilder.create().build();
        }
    }
    
    @Prod
	static class ProdDataSource {
		@Bean(destroyMethod = "")
		@Primary
		@ConfigurationProperties(prefix = "datasource.pay")
		public FactoryBean payDatasource() {
			return new JndiObjectFactoryBean();
		}

		@Bean(destroyMethod = "")
		@ConfigurationProperties(prefix = "datasource.rm")
		public FactoryBean rmDatasource() {
			return new JndiObjectFactoryBean();
		}
	}

    @Alpha
	static class AlphaDataSource {
		@Bean(destroyMethod = "")
		@Primary
		@ConfigurationProperties(prefix = "datasource.pay")
		public FactoryBean payDatasource() {
			return new JndiObjectFactoryBean();
		}

		@Bean(destroyMethod = "")
		@ConfigurationProperties(prefix = "datasource.rm")
		public FactoryBean rmDatasource() {
			return new JndiObjectFactoryBean();
		}
	}

}
