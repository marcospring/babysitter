package com.zhangk.babysitter.controller.web.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.web.BaseController;
import com.zhangk.babysitter.entity.Menu;
import com.zhangk.babysitter.service.menu.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
	@Autowired
	private MenuService menuService;

	@RequestMapping("/menuList")
	public String roleList(HttpServletRequest request,
			HttpServletResponse response) {
		List<Menu> menus = menuService.menuList();
		request.setAttribute("menus", menus);
		return "/menu/menuList";
	}

	@ResponseBody
	@RequestMapping("/addMenu")
	public PageResult addMenu(String title, String url) {
		Menu menu = Menu.getInstance();
		menu.setTitle(title);
		menu.setUrl(url);
		menuService.addMenu(menu);
		return res;
	}
}
