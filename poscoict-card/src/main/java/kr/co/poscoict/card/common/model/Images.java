package kr.co.poscoict.card.common.model;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * 이미지 파일 형식
 * @author Sangjun, Park
 *
 */
public enum Images {
	JPG, JPEG, JPE, PNG, GIF, BMP;
	
	/**
	 * 이미지 파일 여부
	 * @param ext
	 * @return
	 */
	public static boolean isImage(String ext) {
		return Arrays.stream(Images.values()).anyMatch(image -> StringUtils.equalsIgnoreCase(ext, image.toString()));
	}
}
