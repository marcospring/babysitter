package com.zhangk.babysitter.service.user;

import java.util.List;

import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.viewmodel.MenuView;
import com.zhangk.babysitter.viewmodel.UserInfoView;

public interface UserService {
	UserInfo login(String username, String password);

	void userRegister(UserInfo user);

	List<MenuView> getUserMenus(UserInfo user);

	void updateUser(UserInfo user);

	Pagination<UserInfoView> getPageRoleList(Pagination<UserInfo> page);

	UserInfo addUser(String username, String password, String name, long[] roleids);

	UserInfo getUser(long id);

	void deleteMenu(long id);

	void updateUser(String id, String username, String password, String name, long[] roleids);
}
