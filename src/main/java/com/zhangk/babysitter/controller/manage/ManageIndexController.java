package com.zhangk.babysitter.controller.manage;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.service.user.UserService;

@Controller
@RequestMapping("/manage")
public class ManageIndexController extends BaseController {

	Logger log = LoggerFactory.getLogger(ManageIndexController.class);
	@Autowired
	private UserService userService;

	@RequestMapping("/main")
	public Object index() {
		return "/manage/main";
	}

	@RequestMapping("/toLogin")
	public Object toLogin() {
		return "/login";
	}

	@RequestMapping("/login")
	public Object login(HttpServletRequest request, String username, String pass) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(pass)) {
			log.warn("登陆用户名或密码为空！");
			request.getSession().setAttribute("error", "登陆用户名或密码为空！");
			return "redirect:/login.jsp";
		}
		UserInfo userinfo = userService.login(username, pass);
		if (userinfo == null) {
			log.warn("用户名{},用户名或密码错误！", username);
			request.getSession().setAttribute("error", "用户名或密码错误！");
			return "redirect:/login.jsp";
		}
		res.login(userinfo);
		return "redirect:/manage/main.html";
	}

	@RequestMapping("/logout")
	public Object userLogout() {
		getResult().logout();
		return "redirect:/login.jsp";
	}

	@RequestMapping("/layout/west")
	public Object west() {
		return "manage/menu";
	}
}
