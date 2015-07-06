package com.zhangk.babysitter.service.user;

import java.util.List;

import com.zhangk.babysitter.entity.Menu;
import com.zhangk.babysitter.entity.UserInfo;

public interface UserService {
	UserInfo login(String username, String password);

	void userRegister(UserInfo user);

	List<Menu> getUserMenus(UserInfo user);

	void updateUser(UserInfo user);
}
