package kr.co.poscoict.push.common.handler;

import java.io.FileNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import kr.co.poscoict.push.common.exception.ResourceConflictException;
import kr.co.poscoict.push.common.model.ErrorResponse;
import kr.co.poscoict.push.common.util.MessageSourceUtil;

/**
 * REST API Exception handler
 * @author Sangjun, Park
 *
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);
	
	@Autowired
	private MessageSourceUtil messageSource;
	
	/**
	 * 400 처리 (BAD_REQUEST)
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error(e.getMessage(), e);
		return super.handleExceptionInternal(e,
				new ErrorResponse(e.getBindingResult().getFieldErrors().get(0).getField(), e.getBindingResult().getFieldErrors().get(0).getDefaultMessage()),
				headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * 400 처리 (BAD_REQUEST)
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = {IllegalArgumentException.class})
	protected ResponseEntity<Object> handleBadRequest(IllegalArgumentException e, WebRequest request) {
		logger.error(e.getMessage(), e);
		return super.handleExceptionInternal(e,
				new ErrorResponse(StringUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : messageSource.getMessage("api.error.badRequest")),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * 404 처리 (NOT_FOUND)
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = {ResourceNotFoundException.class, FileNotFoundException.class})
	protected ResponseEntity<Object> handleNotFound(Exception e, WebRequest request) {
		logger.error(e.getMessage(), e);
		return super.handleExceptionInternal(e,
				new ErrorResponse(StringUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : messageSource.getMessage("api.error.notFound")),
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	/**
	 * 409 처리 (CONFLICT)
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = {ResourceConflictException.class})
	protected ResponseEntity<Object> handleConflict(ResourceConflictException e, WebRequest request) {
		logger.error(e.getMessage(), e);
		return super.handleExceptionInternal(e,
				new ErrorResponse(StringUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : messageSource.getMessage("api.error.conflict")),
				new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	/**
	 * HTTP 요청시 오류 처리
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = {HttpClientErrorException.class, HttpServerErrorException.class})
	protected ResponseEntity<Object> handleHttpError(HttpStatusCodeException e, WebRequest request) {
		logger.error("HTTP ERROR : {} - {}", e.getStatusCode(), e.getStatusText());
		logger.error("HTTP ERROR : {}", e.getResponseBodyAsString(), e);
		return super.handleExceptionInternal(e,
				new ErrorResponse(StringUtils.isNotEmpty(e.getStatusText()) ? e.getStatusText() : messageSource.getMessage("api.error.serverError")),
				new HttpHeaders(),
				e.getStatusCode() != null ? e.getStatusCode() : HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}
	
	/**
	 * Rest client 오류 처리
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = RestClientException.class)
	protected ResponseEntity<Object> handleRestClientError(RestClientException e, WebRequest request) {
		logger.error("REST CLIENT ERROR : {}", e.getMessage(), e);
		return super.handleExceptionInternal(e,
				new ErrorResponse(StringUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : messageSource.getMessage("api.error.serverError")),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	/**
	 * 500 처리 (INTERNAL_SERVER_ERROR)
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	protected ResponseEntity<Object> handleInternalServerError(Exception e, WebRequest request) {
		logger.error(e.getMessage(), e);
		return super.handleExceptionInternal(e, new ErrorResponse(messageSource.getMessage("api.error.serverError")),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
