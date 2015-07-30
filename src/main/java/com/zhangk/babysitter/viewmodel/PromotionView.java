package com.zhangk.babysitter.viewmodel;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zhangk.babysitter.entity.PromotionInfo;
import com.zhangk.babysitter.utils.mapper.JsonDateSerializer;

public class PromotionView {
	private String guid;
	private String title;
	private String memo;
	private Date beginDate;
	private Date endDate;

	public PromotionView() {
		// TODO Auto-generated constructor stub
	}

	public PromotionView(PromotionInfo info) {
		setGuid(info.getGuid());
		setTitle(info.getTitle());
		setMemo(info.getMemo());
		setBeginDate(info.getBeginDate());
		setEndDate(info.getEndDate());
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
