package kr.co.poscoict.card.batch.model;

/**
 * 페이징 파라미터
 * @author Sangjun, Park
 *
 */
public class PageParam {
	private int page = 0;
	private int pageSize = 50;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page == 0 ? 1 : page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStartRow() {
		return ((page - 1) * pageSize) + 1;
	}
	public int getEndRow() {
		return page * pageSize;
	}
}
