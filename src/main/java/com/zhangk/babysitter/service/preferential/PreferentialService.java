package com.zhangk.babysitter.service.preferential;

import javax.servlet.http.HttpServletRequest;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.viewmodel.PreferentialVO;

public interface PreferentialService {
	PageResult addPromoter(HttpServletRequest request, String telephone,
			String typeGuid, PageResult result);

	PageResult preferentialTypes(PageResult result);

	PreferentialVO getPreferential(String uuid);

	PageResult addPreferentialReceiver(String preferentialValue,
			String toUserTelephone, String verifyCode, PageResult result);

	PageResult preferentialList(String phone, PageResult result);

	PageResult preferentialPromoterList(String phone, PageResult result);

	void addPreferentialType();

	void addPreferentialBehavior();
}
