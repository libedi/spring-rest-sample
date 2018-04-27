package kr.co.poscoict.card.common.model;

import java.io.InputStream;

import org.springframework.http.HttpHeaders;

/**
 * 다운로드 정보
 * @author Sangjun, Park
 *
 */
public class DownloadInfo {
	private InputStream inputStream;
	private HttpHeaders httpHeaders;
	private String contentType;
	private int contentLength;
	
	private DownloadInfo(InputStream inputStream, HttpHeaders httpHeaders, String contentType, int contentLength) {
		this.inputStream = inputStream;
		this.httpHeaders = httpHeaders;
		this.contentType = contentType;
		this.contentLength = contentLength;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public HttpHeaders getHttpHeaders() {
		return httpHeaders;
	}
	public void setHttpHeaders(HttpHeaders httpHeaders) {
		this.httpHeaders = httpHeaders;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public int getContentLength() {
		return contentLength;
	}
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}
	
	public static class DownloadInfoBuilder {
		private InputStream inputStream;
		private HttpHeaders httpHeaders;
		private String contentType;
		private int contentLength;
		
		public DownloadInfoBuilder setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
			return this;
		}
		public DownloadInfoBuilder setHttpHeaders(HttpHeaders httpHeaders) {
			this.httpHeaders = httpHeaders;
			return this;
		}
		public DownloadInfoBuilder setContentType(String contentType) {
			this.contentType = contentType;
			return this;
		}
		public DownloadInfoBuilder setContentLength(int contentLength) {
			this.contentLength = contentLength;
			return this;
		}
		public DownloadInfo build() {
			return new DownloadInfo(this.inputStream, this.httpHeaders, this.contentType, this.contentLength);
		}
	}
}
