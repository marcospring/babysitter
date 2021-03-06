package com.zhangk.babysitter.service.role;

import java.util.List;

import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.viewmodel.RoleView;

public interface RoleService {
	List<Role> roleList();

	Pagination<RoleView> getPageRoleList(Pagination<Role> page);

	void addRole(Role role);

	void deleteRole(long id);

	void updateRole(Role role);

	Role getRole(long id);

	void grant(long id, long[] resourceTreeIds);

}
