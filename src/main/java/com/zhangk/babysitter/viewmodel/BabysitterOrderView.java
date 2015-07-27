package com.zhangk.babysitter.viewmodel;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zhangk.babysitter.utils.mapper.JsonDateSerializer;

public class BabysitterOrderView {
	private String guid;
	private Date beginDate;
	private Date endDate;
	private String employerName;
	private String price;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
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

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
