package com.skplanet.impay.rms.util;

import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

/**
 * 메세지 유틸
 * @author Dongsang Wi
 */
@Component
public class RmsMessageUtil {
	
	@Resource(name = "messageSource")
	private MessageSource messageSource;
	
	/**
	 * 단일 메세지
	 * @param key
	 * @return
	 */
	public String getMessage(String key) {
		return messageSource.getMessage(key, null, Locale.getDefault());
	}
	
	/**
	 * 단일 메세지
	 * @param key
	 * @param args
	 * @return
	 */
	public String getMessageWithArgs(String key, Object[] args) {
		return messageSource.getMessage(key, args, Locale.getDefault());
	}
	
	/**
	 * 단일 메세지 & prefix
	 * @param prefix
	 * @param key
	 * @return
	 */
	public String getMessage(String prefix, String key) {
		if (StringUtils.isNotEmpty(key)) {
			try {
				return messageSource.getMessage(prefix+key, null, Locale.getDefault());
			} catch (NoSuchMessageException e) {}
		}
		return "";
	}
	
	/**
	 * 배열 메세지
	 * @param arrKey
	 * @return
	 */
	public String[] getMessage(String arrKey[]) {
		int lenArrKey = arrKey.length;
		String [] arrVal = new String[lenArrKey];
		for (int i = 0; i < lenArrKey; i++) {
			arrVal[i] = messageSource.getMessage(arrKey[i], null, Locale.getDefault());
		}
		return arrVal;
	}
	
	/**
	 * 배열 메세지 & prefix
	 * @param prefix
	 * @param arrKey
	 * @return
	 */
	public String[] getMessage(String prefix, String arrKey[]) {
		int lenArrKey = arrKey.length;
		String [] arrVal = new String[lenArrKey];
		for (int i = 0; i < lenArrKey; i++) {
			arrVal[i] = messageSource.getMessage(prefix + arrKey[i], null, Locale.getDefault());
		}
		return arrVal;
	}
	
}