package com.zhangk.babysitter.service.user;

import com.zhangk.babysitter.entity.UserInfo;

public interface UserService {
	UserInfo login(String username, String password);
}
