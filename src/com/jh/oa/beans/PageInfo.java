package com.jh.oa.beans;

public class PageInfo {
	private int pageNo = 0;              //��ǰҳ��
	private int pageSize = 0;            //ҳ���С
	private int records = 0;             //�ܼ�¼��
	private int totalPages = 0;          //��ҳ��
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	
}
