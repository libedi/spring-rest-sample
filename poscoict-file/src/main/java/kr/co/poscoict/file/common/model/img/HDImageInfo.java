package kr.co.poscoict.file.common.model.img;

public class HDImageInfo extends ImageInfo {
	public HDImageInfo(ImageShape imageShape) {
		if(imageShape == ImageShape.HORIZONTAL) {
			this.setWidth(IMAGE_SIZE_1280);
			this.setHeight(IMAGE_SIZE_720);
		} else {
			this.setWidth(IMAGE_SIZE_720);
			this.setHeight(IMAGE_SIZE_1280);
		}
		this.setImageShape(imageShape);
	}
}
