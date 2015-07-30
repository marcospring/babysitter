package com.zhangk.babysitter.viewmodel;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zhangk.babysitter.entity.RestInfo;
import com.zhangk.babysitter.utils.mapper.JsonDateSerializer;

public class RestInfoView {
	private String guid;
	private String memo;
	private Date beginDate;
	private Date endDate;

	public RestInfoView() {
		// TODO Auto-generated constructor stub
	}

	public RestInfoView(RestInfo info) {
		setGuid(info.getGuid());
		setMemo(info.getMemo());
		setBeginDate(info.getRestBeginDate());
		setEndDate(info.getRestEndDate());
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
