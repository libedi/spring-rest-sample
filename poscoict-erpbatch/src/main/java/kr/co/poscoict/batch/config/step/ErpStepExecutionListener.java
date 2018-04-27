package kr.co.poscoict.batch.config.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

/**
 * Step 실행 리스너
 * @author Sangjun, Park
 *
 */
@Component
public class ErpStepExecutionListener implements StepExecutionListener {
	private final Logger logger = LoggerFactory.getLogger(ErpStepExecutionListener.class); 

	@Override
	public void beforeStep(StepExecution stepExecution) {
		logger.info("STEP START : {}", stepExecution.getSummary());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.info("STEP END : {}", stepExecution.getSummary());
		return stepExecution.getExitStatus();
	}

}
