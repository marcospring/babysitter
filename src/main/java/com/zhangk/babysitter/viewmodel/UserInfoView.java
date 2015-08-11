package com.zhangk.babysitter.viewmodel;

import java.util.List;

import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;

public class UserInfoView {
	private long id;
	private String guid;
	private String createDate;
	private String username;
	private String name;
	private String roles;

	public UserInfoView() {
		// TODO Auto-generated constructor stub
	}

	public UserInfoView(UserInfo user) {
		setId(user.getId());
		setGuid(user.getGuid());
		setCreateDate(ExpectedDateCreate.formatDate(user.getCreateDate()));
		setUsername(user.getUsername());
		setName(user.getName());
		String rolesStr = getRoleStr(user);
		setRoles(rolesStr);
	}

	private String getRoleStr(UserInfo user) {
		StringBuffer rolesStr = new StringBuffer();
		List<Role> roles = user.getRoles();
		int index = 0;
		for (Role role : roles) {
			if (index != roles.size() - 1)
				rolesStr.append(role.getName()).append(",");
			else
				rolesStr.append(role.getName());
			++index;
		}
		return rolesStr.toString();
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
