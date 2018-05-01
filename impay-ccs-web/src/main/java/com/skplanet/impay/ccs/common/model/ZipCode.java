package com.skplanet.impay.ccs.common.model;

/**
 * 우편 번호 정보
 * @author Junehee, Jang
 *
 */
public class ZipCode {

	private String query;
	
	private String zipCode;
	private String address;
	
	PageParam pageParam;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public PageParam getPageParam() {
		return pageParam;
	}

	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	}	
}
