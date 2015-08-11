package com.zhangk.babysitter.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.Menu;
import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.service.menu.MenuService;
import com.zhangk.babysitter.service.role.RoleService;
import com.zhangk.babysitter.utils.common.JSONUtils;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.viewmodel.MenuView;
import com.zhangk.babysitter.viewmodel.RoleView;

@Controller
@RequestMapping("/manage/role")
public class ManageRoleController extends BaseController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	@RequestMapping("/go")
	public String go() {
		return "/manage/role/role";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(int page, int rows) {
		Pagination<Role> roles = new Pagination<Role>(page, rows);
		Pagination<RoleView> views = roleService.getPageRoleList(roles);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", views.getResult());
		result.put("total", views.getResultSize());
		return result;
	}

	@RequestMapping("/goAdd")
	public Object goAdd() {
		return "manage/role/edit";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Object add(String name) {
		Role role = Role.getInstance();
		role.setName(name);
		roleService.addRole(role);
		return MyResponse.successResponse();
	}

	@RequestMapping("/goEdit")
	public Object goEdit(HttpServletRequest request, long id) {
		Role role = roleService.getRole(id);
		request.setAttribute("vo", role);
		return "manage/role/edit";
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Object edit(Role role) {
		roleService.updateRole(role);
		return MyResponse.successResponse();
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(long id) {
		roleService.deleteRole(id);
		return MyResponse.successResponse();
	}

	@RequestMapping("/toGrant")
	public Object toGrant(int id, HttpServletRequest reqeust) {
		List<Menu> menuList = menuService.menuList();
		List<Menu> roleMenuList = menuService.roleMenus(id);
		if (menuList != null && menuList.size() > 0) {
			List<MenuView> menuViews = new ArrayList<MenuView>();
			for (Menu menu : menuList) {
				MenuView view = menu.view();
				view.setOpen(true);
				if (roleMenuList != null && roleMenuList.size() > 0) {
					for (Menu roleMenu : roleMenuList) {
						if (menu.getId() == roleMenu.getId()) {
							view.setChecked(true);
						}
					}
				}
				menuViews.add(view);
			}
			request.setAttribute("resourceJson", JSONUtils.toJSONString(menuViews));
		}
		Role role = roleService.getRole(id);
		request.setAttribute("role", role);
		return "manage/role/roleGrant";
	}

	@ResponseBody
	@RequestMapping("/grant")
	public Object grant(long id, long[] resourceTreeIds) throws Exception {
		roleService.grant(id, resourceTreeIds);
		return MyResponse.successResponse();
	}
}
