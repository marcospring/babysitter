package com.zhangk.babysitter.utils.common;

import java.util.List;

public class Pagination<T> {
	private int pageNo = 1;
	private int pageSize = 10;
	private int pageCount = 1;
	private List<T> result = null;

	public Pagination() {
		// TODO Auto-generated constructor stub
	}

	public Pagination(List<T> result, int pageNo, int pageSize) {
		this.result = result;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Pagination(List<T> result, int pageNo) {
		this.result = result;
		this.pageNo = pageNo;
	}

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

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getPageCount() {
		pageCount = result != null && result.size() > 0 ? (result.size()
				% pageSize == 0 ? result.size() % pageSize : result.size()
				% pageSize + 1) : 0;
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
