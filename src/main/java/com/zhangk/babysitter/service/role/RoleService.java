package com.zhangk.babysitter.service.role;

import java.util.List;

import com.zhangk.babysitter.entity.Role;

public interface RoleService {
	List<Role> roleList();

	void addRole(Role role);

	void deleteRole(long id);

	void updateRole(Role role);

	Role getRole(long id);

}
