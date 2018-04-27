package kr.co.poscoict.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * Job 실행 알림 리스너
 * @author Sangjun, Park
 *
 */
@Component
public class JobExecutionNotificationListener extends JobExecutionListenerSupport {
	private final Logger logger = LoggerFactory.getLogger(JobExecutionNotificationListener.class);
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.STARTED) {
			logger.info("JOB START : {}", jobExecution.getStartTime());
		}
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info("JOB FINISHED : {}", jobExecution.getEndTime());
		}
	}

}
