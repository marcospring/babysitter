package com.zhangk.babysitter.viewmodel;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zhangk.babysitter.entity.CompanyNotice;
import com.zhangk.babysitter.utils.mapper.JsonDateSerializer;

public class CompanyNoticeView {
	private String guid;
	private Date createDate;
	private String memo;
	private String babysitterName;
	private String title;
	private int state;

	public CompanyNoticeView() {
		// TODO Auto-generated constructor stub
	}

	public CompanyNoticeView(CompanyNotice notice) {
		setGuid(notice.getGuid());
		setCreateDate(notice.getCreateDate());
		setBabysitterName(notice.getBabysitter().getName());
		setTitle(notice.getTitle());
		setMemo(notice.getMemo());
		setState(notice.getState());
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getBabysitterName() {
		return babysitterName;
	}

	public void setBabysitterName(String babysitterName) {
		this.babysitterName = babysitterName;
	}

}
