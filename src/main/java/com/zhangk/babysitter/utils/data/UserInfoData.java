package com.zhangk.babysitter.utils.data;

import java.util.List;

import com.zhangk.babysitter.entity.UserInfo;

public class UserInfoData {
	FileDataProvider dataProvider = new FileDataProvider();

	public List<UserInfo> initUserData() {
		List<UserInfo> list = dataProvider.getUserInfoData();
		return list;
	}
}
