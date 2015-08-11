package com.zhangk.babysitter.controller.manage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.Menu;
import com.zhangk.babysitter.service.menu.MenuService;
import com.zhangk.babysitter.viewmodel.MenuView;

@Controller
@RequestMapping("/manage/menu")
public class ManageMenuController extends BaseController {
	@Autowired
	private MenuService menuService;

	@RequestMapping("/go")
	public String go() {
		return "/manage/menu/menu";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list() {
		List<Menu> menus = menuService.menuList();
		List<MenuView> views = new ArrayList<MenuView>();
		for (Menu menu : menus) {
			views.add(menu.view());
		}
		return views;
	}

	@RequestMapping("/goAdd")
	public Object goAdd() {
		return "manage/menu/menuEdit";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Object add(String title, String url, String pid) {
		menuService.addMenu(title, url, pid);
		return MyResponse.successResponse();
	}

	@RequestMapping("/goEdit")
	public Object goEdit(HttpServletRequest request, long id) {
		request.setAttribute("vo", menuService.getMenu(id));
		return "manage/menu/menuEdit";
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Object update(String id, String title, String url, String pid) {
		menuService.updateMenu(title, url, pid, id);
		return MyResponse.successResponse();
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		menuService.deleteMenu(id);
		return MyResponse.successResponse();
	}
}
