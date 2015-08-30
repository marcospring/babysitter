package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.CustomerManager;

public class CustomerManagerView {
	private long id;
	private String guid;
	private String name;
	private String countyName;
	private String companyName;
	private int state;
	private String telephone;

	public CustomerManagerView() {
		// TODO Auto-generated constructor stub
	}

	public CustomerManagerView(CustomerManager manager) {
		setId(manager.getId());
		setGuid(manager.getGuid());
		setName(manager.getName());
		setCountyName(manager.getCounty() == null ? "" : manager.getCounty()
				.getName());
		setCompanyName(manager.getCompany() == null ? "" : manager.getCompany()
				.getName());
		setState(manager.getState());
		setTelephone(manager.getTelephone());
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
