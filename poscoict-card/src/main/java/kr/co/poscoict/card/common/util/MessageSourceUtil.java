package kr.co.poscoict.card.common.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
		return this.getMessage(id, LocaleContextHolder.getLocale(), params); 
	}
	
	public String getMessage(String id, Locale locale, Object... params) {
		return this.messageSource.getMessage(id, params, locale);
	}
}