package com.zhangk.babysitter.controller.web.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhangk.babysitter.controller.web.BaseController;
import com.zhangk.babysitter.entity.CustomerManager;
import com.zhangk.babysitter.service.manager.CustomerManagerService;
import com.zhangk.babysitter.utils.common.ResultInfo;

@Controller
@RequestMapping("/manager")
public class CustomManagerController extends BaseController {

	@Autowired
	private CustomerManagerService managerService;

	@RequestMapping("/login")
	public PageResult login(String username, String password) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return getErrRes(ResultInfo.VALID_USER_PASS);
		}
		CustomerManager manager = managerService.login(username, password);
		if (manager == null) {
			return getErrRes(ResultInfo.VALID_USER_PASS);
		}
		res.login(manager);
		return res;
	}
}
