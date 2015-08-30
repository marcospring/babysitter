package com.zhangk.babysitter.viewmodel;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.zhangk.babysitter.entity.Company;
import com.zhangk.babysitter.utils.common.Constants;

public class CompanyView {
	private long id;
	private String guid;
	private Date createDate;
	private Date updateDate;
	private String name;
	private int state;
	private String telephone;
	private String address;
	private String countyName;
	private String imgUrl;
	private String type;
	private int countyLevel;

	public CompanyView() {
		// TODO Auto-generated constructor stub
	}

	public CompanyView(Company company) {
		setId(company.getId());
		setGuid(company.getGuid());
		setCreateDate(company.getCreateDate());
		setUpdateDate(company.getUpdateDate());
		setName(company.getName());
		setTelephone(company.getTelephone());
		setAddress(company.getAddress());
		setCountyName(company.getCounty() == null ? "无" : company.getCounty()
				.getName());
		setImgUrl(StringUtils.isEmpty(company.getImageUrl()) ? ""
				: Constants.IMG_DOMAIN + company.getImageUrl());
		setType(company.getType() == Constants.COMPANY_REGULAR_CHAIN ? "直营店"
				: "加盟店");
		setCountyLevel(company.getCountyLevel());
		setState(company.getState());
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCountyLevel() {
		return countyLevel;
	}

	public void setCountyLevel(int countyLevel) {
		this.countyLevel = countyLevel;
	}

}
