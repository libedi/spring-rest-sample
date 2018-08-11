package kr.co.poscoict.file.common.model.img;

/**
 * 이미지 해상도
 * @author Sangjun, Park
 *
 */
public enum ImageDef {
	HD, FHD;
	
	/**
	 * 이미지가 해상도보다 작은지 여부
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean isSmall(int width, int height) {
		switch(this) {
		case HD:
			return (width < ImageInfo.IMAGE_SIZE_1280 && height < ImageInfo.IMAGE_SIZE_720) 
					|| (width < ImageInfo.IMAGE_SIZE_720 && height < ImageInfo.IMAGE_SIZE_1280);
		case FHD:
			return (width < ImageInfo.IMAGE_SIZE_1980 && height < ImageInfo.IMAGE_SIZE_1080)
					|| (width < ImageInfo.IMAGE_SIZE_1080 && height < ImageInfo.IMAGE_SIZE_1980);
		default:
			break;
		}
		return false;
	}
}
