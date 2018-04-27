package kr.co.poscoict.batch.framework.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis Configuration
 * @author Sangjun, Park
 *
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig {
	@MapperScan(basePackages = {"kr.co.poscoict.batch.pst"}, sqlSessionTemplateRef = "pstSqlSessionTemplate")
	static class PstSqlSessionFactory {
		@Autowired
		private DataSource pstDataSource;
		
		@Bean
		public SqlSessionFactory pstSqlSessionFactory() throws Exception {
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
			sqlSessionFactoryBean.setDataSource(pstDataSource);
			sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("config/mybatis/mybatis-config.xml"));
			return sqlSessionFactoryBean.getObject();
		}
		
		@Bean
		public SqlSessionTemplate pstSqlSessionTemplate() throws Exception {
			return new SqlSessionTemplate(pstSqlSessionFactory(), ExecutorType.BATCH);
		}
		
		@Primary
		@Bean
		public DataSourceTransactionManager pstTransactionManager() {
			return new DataSourceTransactionManager(pstDataSource);
		}
		
		/**
		 * Spring batch에서 metadata 등록을 위한 기본 DataSource 설정
		 * @return
		 */
		@Bean
		public BatchConfigurer configurer() {
			return new DefaultBatchConfigurer(this.pstDataSource);
		}
	}
	
	@MapperScan(basePackages = {"kr.co.poscoict.batch.ora"}, sqlSessionTemplateRef = "oraSqlSessionTemplate")
	static class OraSqlSessionFactory {
		@Autowired
		@Qualifier(value = "oraDataSource")
		private DataSource oraDataSource;
		
		@Bean
		public SqlSessionFactory oraSqlSessionFactory() throws Exception {
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
			sqlSessionFactoryBean.setDataSource(oraDataSource);
			sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("config/mybatis/mybatis-config.xml"));
			return sqlSessionFactoryBean.getObject();
		}
		
		@Bean
		public SqlSessionTemplate oraSqlSessionTemplate() throws Exception {
			return new SqlSessionTemplate(oraSqlSessionFactory(), ExecutorType.BATCH);
		}
		
		@Bean
		public DataSourceTransactionManager oraTransactionManager() {
			return new DataSourceTransactionManager(oraDataSource);
		}
	}
}
