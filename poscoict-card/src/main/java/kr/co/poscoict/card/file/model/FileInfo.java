package kr.co.poscoict.card.file.model;

/**
 * 파일 정보
 * @author Sangjun, Park
 *
 */
public class FileInfo {
	private String fileId;
	private String uploadPath;
	private String uploadFileNm;
	private String originalFileNm;
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getUploadFileNm() {
		return uploadFileNm;
	}
	public void setUploadFileNm(String uploadFileNm) {
		this.uploadFileNm = uploadFileNm;
	}
	public String getOriginalFileNm() {
		return originalFileNm;
	}
	public void setOriginalFileNm(String originalFileNm) {
		this.originalFileNm = originalFileNm;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileInfo [");
		if (fileId != null)
			builder.append("fileId=").append(fileId).append(", ");
		if (uploadPath != null)
			builder.append("uploadPath=").append(uploadPath).append(", ");
		if (uploadFileNm != null)
			builder.append("uploadFileNm=").append(uploadFileNm).append(", ");
		if (originalFileNm != null)
			builder.append("originalFileNm=").append(originalFileNm);
		builder.append("]");
		return builder.toString();
	}
}
