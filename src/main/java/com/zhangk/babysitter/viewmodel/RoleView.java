package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.Role;

public class RoleView {
	private long id;
	private String name;
	private String guid;
	private int checked = 0;
	private int ovld;

	public RoleView() {
		// TODO Auto-generated constructor stub
	}

	public RoleView(Role role) {
		setId(role.getId());
		setGuid(role.getGuid());
		setName(role.getName());
		setOvld(role.isOvld() ? 1 : 0);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public int getOvld() {
		return ovld;
	}

	public void setOvld(int ovld) {
		this.ovld = ovld;
	}

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

}
