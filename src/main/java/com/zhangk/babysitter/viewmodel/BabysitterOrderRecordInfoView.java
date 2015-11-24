package com.zhangk.babysitter.viewmodel;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zhangk.babysitter.entity.BabysitterOrderRecordInfo;
import com.zhangk.babysitter.utils.mapper.JsonDateSerializer;

public class BabysitterOrderRecordInfoView {
	private String guid;
	private Date createDate;
	private Date updateDate;
	private int state;

	public BabysitterOrderRecordInfoView() {
		// TODO Auto-generated constructor stub
	}

	public BabysitterOrderRecordInfoView(BabysitterOrderRecordInfo info) {
		setGuid(info.getGuid());
		setCreateDate(info.getCreateDate());
		setUpdateDate(info.getUpdateDate());
		setState(info.getState());
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

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
