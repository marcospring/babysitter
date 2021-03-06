package com.zhangk.babysitter.controller.web.page;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.service.user.UserService;

@Controller
@RequestMapping("/main")
public class MainController extends BaseController {

	@Autowired
	private UserService userService;

	// @RequestMapping("/menu")
	// public String menu(HttpServletRequest request) {
	// UserInfo user = (UserInfo) request.getSession().getAttribute(
	// Constants.SESSION_USER);
	// List<Menu> menus = userService.getUserMenus(user);
	// request.setAttribute("menus", menus);
	// return "common/menu";
	// }
	//
	// @RequestMapping("/m")
	// public String main() {
	// UserInfo user = (UserInfo) request.getSession().getAttribute(
	// Constants.SESSION_USER);
	// List<Menu> menus = userService.getUserMenus(user);
	// request.setAttribute("menus", menus);
	// return "main";
	// }

	@RequestMapping("/top")
	public String top(HttpServletRequest request) {
		return "common/top";
	}

	@RequestMapping("/bottom")
	public String bottom(HttpServletRequest request) {
		return "common/bottom";
	}
}
