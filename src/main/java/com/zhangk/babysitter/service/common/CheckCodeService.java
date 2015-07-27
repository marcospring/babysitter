package com.zhangk.babysitter.service.common;

import com.zhangk.babysitter.entity.CheckCode;

public interface CheckCodeService {
	int REGISTER = 1;
	int PUBLISH_ORDER = 2;
	int LOGIN = 3;

	void addCheckCode(CheckCode code);

	boolean updateCheckCode(String mobilePhone, String code, int type);
}
