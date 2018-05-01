/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author PP58701
 *
 */
public class StringUtil extends StringUtils {

	public static final String SPECIAL_SYMBOL = "~!@#$%^&*()_+|`=\\;?/-<>:\".,'\\[\\]\\{\\}";

	/**
	 * 암호화
	 * 
	 * @param algorithm
	 *            (SHA, SHA-256, SHA-512, MD5 등)
	 * @param arg
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String encrypt(String algorithm, String arg) {
		MessageDigest md;
		try { 
			md = MessageDigest.getInstance(algorithm);
			// Hash계산
			md.update(arg.getBytes());
			byte[] digest = md.digest();

			// 16진수 문자열 변환
			StringBuilder buffer = new StringBuilder();
			for (int i = 0; i < digest.length; i++) {
				String tmp = Integer.toHexString(digest[i] & 0xff);
				if (tmp.length() == 1) {
					buffer.append('0').append(tmp);
				} else {
					buffer.append(tmp);
				}
			}
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			return StringUtil.EMPTY;
		}

		
	}

	/**
	 * 영문대/소 문자, 숫자, 특수기호 램덤생성
	 * 
	 * @param count
	 * @return
	 */
	public static String shuffle(int count) {
		StringBuilder buf = new StringBuilder();
		buf.append(StringUtil.upperCase(RandomStringUtils.randomAlphabetic(count)));
		buf.append(StringUtil.lowerCase(RandomStringUtils.randomAlphabetic(count)));
		buf.append(RandomStringUtils.randomNumeric(count));
		buf.append(RandomStringUtils.random(count, SPECIAL_SYMBOL));

		List<Character> characters = new ArrayList<Character>();
		for (char c : buf.toString().toCharArray()) {
			characters.add(c);
		}
		StringBuilder output = new StringBuilder(buf.toString().length());
		while (characters.size() != 0) {
			int randPicker = (int) (Math.random() * characters.size());
			output.append(characters.remove(randPicker));
		}
		return output.toString();
	}

	/**
	 * SKP 기준 패스워드 일치 검증
	 * 
	 * 영문 대문자, 영문 소문자, 숫자, 특수문자 중 2가지 이상을 조합하여야 함
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean validationPassword(String arg) {
		Integer count = 0;
		count += match("[a-z]", arg);
		count += match("[A-Z]", arg);
		count += match("[0-9]", arg);
		count += match(SPECIAL_SYMBOL, arg);
		return count > 1 ? true : false;
	}

	/**
	 * 패턴 매치 여부 수
	 * 
	 * @param pattern
	 * @param arg
	 * @return
	 */
	public static Integer match(String pattern, String arg) {
		Integer count = 0;
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(arg);

		if (m.find()) {
			count++;
		}
		return count;
	}
	
	/**
	 * 유효하지 않은 경로 필터
	 * 
	 * @param arg
	 * @return
	 */
	public static String filterInvalidPath(String arg){
		arg = arg.replaceAll("\\.\\./", "");
		arg = arg.replaceAll("\\./", "");
		arg = arg.replaceAll("\\.\\.\\\\", "");
		arg = arg.replaceAll("\\.\\\\", "");
		arg = arg.replaceAll("%", "");
		arg = arg.replaceAll(";", "");
		return arg;
	}
}