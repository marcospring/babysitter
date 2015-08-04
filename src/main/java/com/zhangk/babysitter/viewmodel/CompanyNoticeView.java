package com.zhangk.babysitter.viewmodel;

import java.util.Date;

import com.zhangk.babysitter.entity.CompanyNotice;

public class CompanyNoticeView {
	private String guid;
	private Date createDate;
	private String memo;
	private String title;
	private int state;

	public CompanyNoticeView() {
		// TODO Auto-generated constructor stub
	}

	public CompanyNoticeView(CompanyNotice notice) {
		setGuid(notice.getGuid());
		setCreateDate(createDate);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
