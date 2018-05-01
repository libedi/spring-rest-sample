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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 공통util
 * @author 
 *
 */
public class CommonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	private static final String patternTel = "(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})";
	
	/**
	 * 전화번호를 잘라서 문자열 배열로 리턴
	 * @param telStr 전화번호, 휴대폰번호
	 * @return String[] 
	 * @author jisu
	 */
	public static String[] telToArray(String telStr){
		if(StringUtil.isEmpty(telStr)) return null;
		Pattern pattern = Pattern.compile(patternTel);
		Matcher matcher = pattern.matcher(telStr);
		String[] arrTel = new String[3];
		if(matcher.matches()){
			arrTel[0] = matcher.group(1);
			arrTel[1] = matcher.group(2);
			arrTel[2] = matcher.group(3);
		}
		return arrTel;
	}
	
	/**
	 * 서버 IP 주소
	 * @return String
	 */
	public static String getLocalServerIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}