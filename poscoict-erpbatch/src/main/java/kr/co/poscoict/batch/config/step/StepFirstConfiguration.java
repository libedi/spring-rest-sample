package kr.co.poscoict.batch.config.step;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.spit.es.services.crypt.seed.Seed;

import kr.co.poscoict.batch.model.Count;
import kr.co.poscoict.batch.model.PushInfo;
import kr.co.poscoict.batch.model.User;

/**
 * Step Configuration - 1 : ERP 푸시건수 조회 Step 
 * @author Sangjun, Park
 *
 */
@Configuration
public class StepFirstConfiguration {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	@Qualifier(value = "pstSqlSessionFactory")
	private SqlSessionFactory pstSqlSessionFactory;
	
	@Autowired
	@Qualifier(value = "pstSqlSessionTemplate")
	private SqlSessionTemplate pstSqlSessionTemplate;
	
	@Autowired
	@Qualifier(value = "oraSqlSessionFactory")
	private SqlSessionFactory oraSqlSessionFactory;
	
	@Autowired
	private ErpStepExecutionListener stepExecutionListener;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private XmlMapper xmlMapper;
	
	@Value("#{T(java.lang.Integer).parseInt(${erp.batch.page-size})}")
	private Integer pageSize;
	
	@Value("#{T(java.lang.Integer).parseInt(${erp.batch.commit-interval})}")
	private Integer commitInterval;
	
	@Value("${url.erp}")
	private String erpUrl;
	
	/**
	 * 사용자 조회 ItemReader
	 * @return
	 */
	@Bean
	public ItemReader<User> selectUserItemReader() {
		MyBatisPagingItemReader<User> reader = new MyBatisPagingItemReader<>();
		reader.setSqlSessionFactory(oraSqlSessionFactory);
		reader.setQueryId("kr.co.poscoict.batch.ora.mapper.OracleMapper.selectUser");
		reader.setPageSize(pageSize);
		return reader;
	}
	
	/**
	 * 푸시건수 조회 ItemProcessor
	 * @return
	 */
	@Bean
	public ItemProcessor<User, PushInfo> pushCountItemProcessor() {
		return (u) -> {
			PushInfo p = this.pstSqlSessionTemplate.selectOne("kr.co.poscoict.batch.pst.mapper.PostgresqlMapper.selectPushInfo", u.getEmpcd());
			if(p == null) {
				p = new PushInfo();
				p.setEmpcd(u.getEmpcd());
			}
			String x = this.restTemplate.getForObject(new StringBuilder(erpUrl).append(new Seed().crypt(u.getEmpcd())).toString(), String.class);
			Count c = this.xmlMapper.readValue(x, Count.class);
			p.setPreCnt(p.getNowCnt());
			p.setNowCnt(c.getValue());
			return p;
		};
	}
	
	/**
	 * 푸시건수 입력 ItemWriter
	 * @return
	 */
	@Bean
	public ItemWriter<PushInfo> insertPushItemWriter() {
		MyBatisBatchItemWriter<PushInfo> writer = new MyBatisBatchItemWriter<>();
		writer.setSqlSessionFactory(pstSqlSessionFactory);
		writer.setStatementId("kr.co.poscoict.batch.pst.mapper.PostgresqlMapper.insertPushInfo");
		return writer;
	}
	
	/**
	 * First Step Configuration
	 * @return
	 */
	@Bean
	public Step firstStep() {
		return this.stepBuilderFactory.get("firstStep")
				.listener(stepExecutionListener)
				.allowStartIfComplete(true)
				.<User, PushInfo> chunk(commitInterval)
				.reader(selectUserItemReader())
				.processor(pushCountItemProcessor())
				.writer(insertPushItemWriter())
				.faultTolerant()
				.skip(ResourceAccessException.class)
				.skipLimit(10)
				.build();
	}
}
