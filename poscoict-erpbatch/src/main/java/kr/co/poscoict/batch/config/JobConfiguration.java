package kr.co.poscoict.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Job Configuration
 * @author Sangjun, Park
 *
 */
@Configuration
@EnableBatchProcessing
public class JobConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private JobExecutionNotificationListener listener;
	
	@Autowired
	@Qualifier(value = "firstStep")
	private Step firstStep;
	
	@Autowired
	@Qualifier(value = "secondStep")
	private Step secondStep;

	/**
	 * ERP Batch Job Configuration
	 * @return
	 */
	@Bean
	public Job erpJob() {
		return this.jobBuilderFactory.get("erpJob")
				.listener(listener)
				.incrementer(new RunIdIncrementer())
				.start(firstStep)
				.next(secondStep)
				.build();
	}
	
}
