package com.zhangk.babysitter.utils.common;

import java.util.List;

public class Pagination<T> {
	private int pageNo = 1;
	private int pageSize = 10;
	private long pageCount = 1;
	private long resultSize = 0;
	private List<T> result = null;
	private String pageStr = null;
	private String requestUri = null;

	public Pagination() {
		// TODO Auto-generated constructor stub
	}

	public Pagination(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
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

	public long getPageCount() {
		pageCount = resultSize > 0 ? (resultSize % pageSize == 0 ? resultSize / pageSize : resultSize / pageSize + 1) : 0;
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public String getPageStr() {
		if (pageStr == null)
			pageStr = buildPageStr().toString();
		return pageStr;
	}

	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public long getResultSize() {
		return resultSize;
	}

	public void setResultSize(long resultSize) {
		this.resultSize = resultSize;
	}

	private StringBuilder buildPageStr() {
		StringBuilder pageStrBuilder = new StringBuilder();
		pageStrBuilder.append("<nav><ul class=\"pagination\">");
		buildPrevious(pageStrBuilder);
		if (getPageCount() <= 7) {
			for (int i = 1; i <= getPageCount(); i++) {
				buildPage(pageStrBuilder, i);
			}
		} else {
			buildPageList(pageStrBuilder, 6, 3);
		}
		buildNext(pageStrBuilder);
		pageStrBuilder.append("</ul></nav>");
		return pageStrBuilder;
	}

	private void buildPageList(StringBuilder pageStrBuilder, int frount, int endMinus) {
		if (getPageNo() < frount) {
			for (int i = 1; i <= frount; i++) {
				buildPage(pageStrBuilder, i);
			}
			pageStrBuilder.append("<li><a>...</a></li>");
		} else if (getPageNo() >= frount && getPageNo() < getPageCount() - endMinus) {
			for (int i = 1; i < 3; i++) {
				buildPage(pageStrBuilder, i);
			}
			pageStrBuilder.append("<li><a>...</a></li>");
			for (int i = getPageNo() - 2; i <= getPageNo() + 2; i++) {
				buildPage(pageStrBuilder, i);
			}
			pageStrBuilder.append("<li><a>...</a></li>");
		} else {
			for (int i = 1; i < 3; i++) {
				buildPage(pageStrBuilder, i);
			}
			pageStrBuilder.append("<li><a>...</a></li>");
			for (int i = getPageNo() - 2; i <= getPageCount(); i++) {
				buildPage(pageStrBuilder, i);
			}
		}
	}

	private void buildNext(StringBuilder pageStrBuilder) {
		pageStrBuilder.append("<li><a href=\"").append(getRequestUri()).append("?pageNo=").append(getPageNo() + 1 > getPageCount() ? getPageCount() : getPageNo() + 1)
				.append("&pageSize=").append(getPageSize()).append("\" aria-label=\"Next\"> ").append("<i class=\"glyphicon glyphicon-forward\"></i></a></li>");
		pageStrBuilder.append("<li><a href=\"").append(getRequestUri()).append("?pageNo=").append(getPageCount()).append("&pageSize=").append(getPageSize())
				.append("\" aria-label=\"Next\"> ").append("<i class=\"glyphicon glyphicon-fast-forward\"></i></a></li>");
	}

	private void buildPrevious(StringBuilder pageStrBuilder) {
		pageStrBuilder.append("<li><a href=\"").append(getRequestUri()).append("?pageNo=").append(1).append("&pageSize=").append(getPageSize()).append("\"> ")
				.append("<i class=\"glyphicon glyphicon-fast-backward\"></i></a></li>");
		pageStrBuilder.append("<li><a href=\"").append(getRequestUri()).append("?pageNo=").append(getPageNo() - 1 <= 0 ? 1 : getPageNo() - 1).append("&pageSize=")
				.append(getPageSize()).append("\"> ").append("<i class=\"glyphicon glyphicon-backward\"></i></a></li>");
	}

	private void buildPage(StringBuilder pageStrBuilder, int i) {
		if (getPageNo() == i) {
			pageStrBuilder.append("<li class=\"active\"><a href=\"").append(getRequestUri()).append("?pageNo=").append(i).append("&pageSize=").append(getPageSize()).append("\">")
					.append(i).append("<span class=\"sr-only\">(current)</span></a></li>");
		} else {
			pageStrBuilder.append("<li><a href=\"").append(getRequestUri()).append("?pageNo=").append(i).append("&pageSize=").append(getPageSize()).append("\">").append(i)
					.append("</a></li>");
		}
	}
}
