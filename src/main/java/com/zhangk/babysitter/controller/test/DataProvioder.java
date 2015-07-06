package com.zhangk.babysitter.controller.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.web.BaseController;
import com.zhangk.babysitter.entity.Menu;
import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.service.menu.MenuService;
import com.zhangk.babysitter.service.role.RoleService;
import com.zhangk.babysitter.service.user.UserService;
import com.zhangk.babysitter.utils.data.UserInfoData;

@Controller
@RequestMapping("/dataProvider")
public class DataProvioder extends BaseController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;

	@ResponseBody
	@RequestMapping("/userData")
	public PageResult userData() {
		UserInfoData userData = new UserInfoData();
		List<UserInfo> list = userData.initUserData();
		for (UserInfo userInfo : list) {
			userService.userRegister(userInfo);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping("/roleData")
	public PageResult roleData() {
		Role superAdmin = Role.getInstance();
		superAdmin.setName("超级管理员");
		roleService.addRole(superAdmin);

		Role xingzheng = Role.getInstance();
		xingzheng.setName("总部行政");
		roleService.addRole(xingzheng);

		Role zhiyingAreaManager = Role.getInstance();
		zhiyingAreaManager.setName("区域经理");
		roleService.addRole(zhiyingAreaManager);

		Role jiamengAreaManager = Role.getInstance();
		jiamengAreaManager.setName("加盟区域经理");
		roleService.addRole(jiamengAreaManager);

		Role zhiyingGuestManager = Role.getInstance();
		zhiyingGuestManager.setName("直营客户经理");
		roleService.addRole(zhiyingGuestManager);

		Role jiamengGuestManager = Role.getInstance();
		jiamengGuestManager.setName("加盟客户经理");
		roleService.addRole(jiamengGuestManager);

		Role zhiyingShenji = Role.getInstance();
		zhiyingShenji.setName("直营审计");
		roleService.addRole(zhiyingShenji);

		Role jiamengShenji = Role.getInstance();
		jiamengShenji.setName("加盟审计");
		roleService.addRole(jiamengShenji);

		Role chuna = Role.getInstance();
		chuna.setName("总部出纳");
		roleService.addRole(chuna);

		Role jiamengChuna = Role.getInstance();
		jiamengChuna.setName("加盟出纳");
		roleService.addRole(jiamengChuna);
		return res;
	}

	@ResponseBody
	@RequestMapping("/menuData")
	public PageResult menuData() {
		Menu role = Menu.getInstance();
		role.setUrl("/role/roleList.html");
		role.setTitle("角色管理");
		menuService.addMenu(role);
		Menu menu = Menu.getInstance();
		menu.setUrl("/menu/menuList.html");
		menu.setTitle("菜单管理");
		menuService.addMenu(menu);
		return res;
	}

	@ResponseBody
	@RequestMapping("/userRoleMenuData")
	public PageResult userRoleMenuData() {
		List<Menu> menus = menuService.menuList();
		Role role = roleService.getRole(1);
		role.setMenus(menus);
		roleService.updateRole(role);
		UserInfo u = userService.login("n10", "123");
		List<Role> roles = u.getRoles();
		roles.add(role);
		u.setRoles(roles);
		userService.updateUser(u);
		return res;
	}
}