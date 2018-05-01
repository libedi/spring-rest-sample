package com.skplanet.impay.ccs.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 날짜 및 시간을 시스템으로부터 연산하는 클래스입니다.
 * <PRE>
 * 1. ClassName : DateUtil
 * 2. FileName : DateUtil.java
 * 3. Pakage : com.skplanet.impay.ccs.common.util
 * 4. 작성자 : 주영길
 * 5. 작성일 : 2015. 11. 13.오후 2:05:48
 * 6. 변경이력
 *  이름 / 일자 / 변경내용
 *  주영길 / 2015. 11. 13. / -
 */
public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	/**
	 * 현재 일시를 YYYYMMDDHHMMSS 형식으로 리턴
	 * @return
	 */
	public static String getCurrentDateTime() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				currentLocale);
		return formatter.format(today);
	}
	/**
	 * 현재 일시를 YYYYMMDD HH:MM 형식으로 리턴
	 * @return
	 */
	public static String getCurrentDateTimeMin() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyyMMdd HH:mm";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				currentLocale);
		return formatter.format(today);
	}
	/**
	 * 현재 일시를 YYYY 형식으로 리턴
	 * @return
	 */
	public static String getCurrentYear() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyy";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				currentLocale);
		return formatter.format(today);
	}

	/**
	 * 현재 일시를 HHMMSS 형식으로 리턴
	 * @return
	 */
	public static String getCurrentTime() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "HHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				currentLocale);
		return formatter.format(today);

	}

	/**
	 * 현재 날짜를 YYYYMMDD 형식으로 리턴
	 * @return
	 */
	public static String getCurrentDate() {
		return getCurrentDateTime().substring(0, 8);
	}
	
	
	/**
	 * 현재 날짜를 YYYYMM 형식으로 리턴
	 * @return
	 */
	public static String getCurrentYearMonth() {
		return getCurrentDateTime().substring(0, 6);
	}
	
	/**
	 * 현재연도 기준 'year'년전까지 연도 리스트 가져오기
	 * @return
	 */
	public static List<Integer> getYearList(int year){
		List<Integer> result = new ArrayList<Integer>();
		
		int currentYear = Integer.parseInt(DateUtil.getCurrentYear()); // 현재연도 
		
		for(int index = currentYear - year; index < currentYear + 1; index++){
			result.add(index);
		}
		return result;
	}
	
	/**
	 * 기준연도 기준 'year'년전까지 연도 리스트 가져오기
	 * @return
	 */
	public static List<Integer> getYearList(String strBaseYear, int year){
		List<Integer> result = new ArrayList<Integer>();
		
		int baseYear = Integer.parseInt(strBaseYear); // 현재연도 
		
		for(int index = baseYear - 10; index < baseYear + 1; index++){
			result.add(index);
		}
		return result;
	}
	
	public static String formatDateYYYYMM(String value, String separator) {
		
		String result = "";
		
		if(!CustomStringUtils.isNull(value)) {
			result = value.toString().substring(0,4)+separator+value.substring(4,6);
		} 
		return result;
		
	}
	
	public static String formatDateYYMM(String value, String separator) {
		
		String result = "";
		
		if(!CustomStringUtils.isNull(value)) {
			result = value.toString().substring(2,4)+separator+value.substring(4,6);
		} 
		return result;
		
	}
	
	public static String formatDateYYMMDD(String value, String separator) {
		
		String result = "";
		
		if(!CustomStringUtils.isNull(value)) {
			result = value.toString().substring(2,4)+separator+value.substring(4,6)+separator+separator+value.substring(6,8);			
		} 
		
		return result;
	}
	
	/**
	 * 현재 날짜에 date만큼 구하기
	 * @return
	 */
	public static String getDate(String format, int date){
		final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		final Calendar today = Calendar.getInstance();
		today.add(Calendar.DATE, date);
		return dateFormat.format(today.getTime());
	}
	/**
	 * 입력 날짜에 한달 전 날짜 구하기
	 * @param format
	 * @param date
	 * @param addDate
	 * @return
	 * @throws ParseException 
	 */
	public static String getMonth(String format, String date, int addDate){
		final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		final Calendar cal = Calendar.getInstance();
		try {
			Date pasDate = dateFormat.parse(date);
			cal.setTime(pasDate);
			cal.add(Calendar.MONTH, addDate);
		} catch (ParseException e) {
			logger.info("ParseException : " + e.toString());
		}
		return dateFormat.format(cal.getTime());
	}
	/**
	 * 입력 날짜에 일주일 전 날짜 구하기
	 * @param format
	 * @param date
	 * @param addDate
	 * @return
	 * @throws ParseException
	 */
	public static String getWeekDate(String format, String date, int addDate){
		final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		final Calendar cal = Calendar.getInstance();
		try {
			Date pasDate = dateFormat.parse(date);
			cal.setTime(pasDate);
			cal.add(Calendar.DATE, addDate);
		} catch (ParseException e) {
			logger.info("DB Error : " + e.toString());
		}
		return dateFormat.format(cal.getTime());
	}
	/**
	 * 입력 날짜에 분 단위 계산
	 * @param format
	 * @param date
	 * @param addDate
	 * @return
	 * @throws ParseException
	 */
	public static String getMinuteDate(String format, String date, int addDate){
		final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		final Calendar cal = Calendar.getInstance();
		try {
			Date pasDate = dateFormat.parse(date);
			cal.setTime(pasDate);
			cal.add(Calendar.MINUTE, addDate);
		} catch (ParseException e) {
			logger.info("DB Error : " + e.toString());
		}
		return dateFormat.format(cal.getTime());
	}
	
}