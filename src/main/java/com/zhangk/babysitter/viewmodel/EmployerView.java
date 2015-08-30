package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.Employer;

public class EmployerView {
	private long id;
	private String guid;
	private String name;
	private String address;
	private String telphone;

	public EmployerView() {
		// TODO Auto-generated constructor stub
	}

	public EmployerView(Employer employer) {
		setId(employer.getId());
		setGuid(employer.getGuid());
		setName(employer.getUsername());
		setAddress(employer.getAddress());
		setTelphone(employer.getMobilePhone());
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

}
