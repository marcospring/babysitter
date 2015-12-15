package com.zhangk.babysitter.service.common;

import com.zhangk.babysitter.entity.CheckCode;

public interface CheckCodeService {
	int REGISTER = 1;
	int PUBLISH_ORDER = 2;
	int LOGIN = 3;
	int CHANGE_PASS = 4;
	int PREFERENTIAL = 5;

	CheckCode addCheckCode(String telephone, int type);

	boolean updateCheckCode(String mobilePhone, String code, int type);
}
