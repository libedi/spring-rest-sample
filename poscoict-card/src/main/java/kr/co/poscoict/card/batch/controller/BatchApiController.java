package kr.co.poscoict.card.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.co.poscoict.card.batch.service.BatchService;

/**
 * Batch Api Controller
 * @author Sangjun, Park
 *
 */
@RestController
@RequestMapping("/batch")
public class BatchApiController {
	private final Logger logger = LoggerFactory.getLogger(BatchApiController.class);
	
	@Autowired
	private BatchService batchService;
	
	/**
	 * 법인카드 푸시 발송
	 * @throws Exception 
	 */
	@GetMapping(path = "/push")
	@ResponseStatus(HttpStatus.OK)
	public void sendPush() throws Exception {
		this.batchService.sendPushManual();
	}
	
	/**
	 * Exception Handler
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public void handleBatchError(Exception e) {
		logger.error("BATCH ERROR : {}", e.getMessage(), e);
	}
}
