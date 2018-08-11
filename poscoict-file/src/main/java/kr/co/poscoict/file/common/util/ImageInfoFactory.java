package kr.co.poscoict.file.common.util;

import kr.co.poscoict.file.common.model.img.FHDImageInfo;
import kr.co.poscoict.file.common.model.img.HDImageInfo;
import kr.co.poscoict.file.common.model.img.ImageDef;
import kr.co.poscoict.file.common.model.img.ImageInfo;
import kr.co.poscoict.file.common.model.img.ImageShape;

public class ImageInfoFactory {
	public static ImageInfo getImageInfo(ImageDef imageDef, ImageShape imageShape) {
		if(imageDef == ImageDef.HD) {
			return new HDImageInfo(imageShape);
		} else {
			return new FHDImageInfo(imageShape);
		}
	}
}
