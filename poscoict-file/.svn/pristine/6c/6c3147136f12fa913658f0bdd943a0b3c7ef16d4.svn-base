package kr.co.poscoict.file.common.model.img;

import org.springframework.util.Assert;

/**
 * 이미지 형태
 * @author Sangjun, Park
 *
 */
public enum ImageShape {
	HORIZONTAL, VERTICAL;
	
	public static ImageShape getImageShape(int width, int height) {
		Assert.isTrue(!(width == 0 && height == 0), "Invalid Image Size.");
		if(width < height) {
			return VERTICAL;
		}
		return HORIZONTAL;
	}
}
