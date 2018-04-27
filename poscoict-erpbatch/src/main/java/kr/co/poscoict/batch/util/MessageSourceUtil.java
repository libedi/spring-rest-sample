package kr.co.poscoict.batch.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * MessageSource Util
 * @author Sangjun, Park
 *
 */
@Component
public class MessageSourceUtil {
	@Autowired
	private MessageSource messageSource;
	
	public String getMessage(String id, Object... params) {
		return this.messageSource.getMessage(id, params, Locale.KOREAN); 
	}
}
