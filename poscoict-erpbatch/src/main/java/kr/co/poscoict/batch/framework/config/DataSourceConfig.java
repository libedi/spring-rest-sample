package kr.co.poscoict.batch.framework.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import kr.co.poscoict.batch.framework.profile.Dev;
import kr.co.poscoict.batch.framework.profile.Local;
import kr.co.poscoict.batch.framework.profile.Prod;

/**
 * DataSource Configuration
 * @author Sangjun, Park
 *
 */
@Configuration
@EnableConfigurationProperties
public class DataSourceConfig {
	
	@Local
	@Dev
	static class DevDataSource {
		@Primary
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource")
		public DataSource pstDataSource() {
			return DataSourceBuilder.create().build();
		}
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource.ora")
		public DataSource oraDataSource() {
			return DataSourceBuilder.create().build();
		}
	}
	
	@Prod
	static class ProdDataSource {
		@Primary
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource.prod")
		public DataSource pstDataSource() {
			return DataSourceBuilder.create().build();
		}
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource.prod.ora")
		public DataSource oraDataSource() {
			return DataSourceBuilder.create().build();
		}
	}
}