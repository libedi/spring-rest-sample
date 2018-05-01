/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.rms.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;

/**
 * 사용자정보 유틸
 * @author Dongsang Wi
 */
public class RmsAuthUtil {
	
	/**
	 * 사용자 정보
	 * @return CustomUserDetails
	 */
	public static CustomUserDetails getUserInfo() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userInfo = null;
		if (auth != null) {
			if (auth.getPrincipal() instanceof CustomUserDetails) {
				userInfo = (CustomUserDetails) auth.getPrincipal();
			}
		}
		return userInfo;
	}
	
	/**
	 * 사용자 ID
	 * @return String
	 */
	public static String getUserId() {
		CustomUserDetails userInfo = getUserInfo();
		if (userInfo != null) {
			return userInfo.getUserId();
		} else {
			return "";
		}
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
			
		}
		return null;
	}
	
}