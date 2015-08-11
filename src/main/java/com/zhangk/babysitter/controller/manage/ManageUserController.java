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
import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.service.role.RoleService;
import com.zhangk.babysitter.service.user.UserService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.MenuView;
import com.zhangk.babysitter.viewmodel.RoleView;
import com.zhangk.babysitter.viewmodel.UserInfoView;

@Controller
@RequestMapping("/manage/user")
public class ManageUserController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@ResponseBody
	@RequestMapping("/userMenu")
	public Object findUserMenu(HttpServletRequest reqeust) {
		UserInfo user = (UserInfo) reqeust.getSession().getAttribute(Constants.SESSION_USER);
		List<MenuView> treeList = userService.getUserMenus(user);

		return treeList;
	}

	@RequestMapping("/go")
	public String go() {
		return "/manage/user/user";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest request, int page, int rows) {
		Pagination<UserInfo> users = new Pagination<UserInfo>(page, rows);
		Pagination<UserInfoView> views = userService.getPageRoleList(users);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", views.getResult());
		result.put("total", views.getResultSize());
		return result;
	}

	@RequestMapping("/goAdd")
	public Object goAdd(HttpServletRequest request) {
		List<Role> roles = roleService.roleList();
		List<RoleView> views = new ArrayList<RoleView>();
		for (Role role : roles) {
			RoleView view = role.view();
			views.add(view);
		}
		request.setAttribute("roles", views);
		return "manage/user/userEdit";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Object add(String username, String password, String name, long[] roleids) {
		UserInfo u = userService.addUser(username, password, name, roleids);
		if (u == null)
			return MyResponse.errorResponse(ResultInfo.USER_NULL.getCode(), ResultInfo.USER_NULL.getMsg());
		return MyResponse.successResponse();
	}

	@RequestMapping("/goEdit")
	public Object goEdit(HttpServletRequest request, long id) {
		UserInfo u = userService.getUser(id);
		List<Role> roles = roleService.roleList();
		List<Role> userRoles = u.getRoles();
		List<RoleView> views = new ArrayList<RoleView>();
		for (Role role : roles) {
			RoleView view = role.view();
			for (Role userRole : userRoles) {
				if (role.getId() == userRole.getId()) {
					view.setChecked(1);
				}
			}
			views.add(view);
		}
		request.setAttribute("vo", u);
		request.setAttribute("roles", views);
		return "manage/user/userEdit";
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Object update(String id, String username, String password, String name, long[] roleids) {
		userService.updateUser(id, username, password, name, roleids);
		return MyResponse.successResponse();
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(long id) {
		userService.deleteMenu(id);
		return MyResponse.successResponse();
	}

}
