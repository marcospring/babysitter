package com.zhangk.babysitter.controller.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.service.user.UserService;
import com.zhangk.babysitter.utils.common.ResultInfo;

@Controller
@RequestMapping("/user")
public class LoginController extends BaseController {
	Logger log = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping("/login")
	public PageResult login(String username, String password) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			log.warn("登陆用户名或密码为空！");
			return getErrRes(ResultInfo.VALID_USER_PASS.getCode());
		}
		UserInfo userinfo = userService.login(username, password);
		if (userinfo == null) {
			log.warn("用户名{},用户名或密码错误！", username);
			return getErrRes(ResultInfo.VALID_USER_PASS.getCode());
		}
		res.login(userinfo);
		return res;
	}
}
