package com.zhangk.babysitter.controller.web.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.Role;
import com.zhangk.babysitter.service.role.RoleService;
import com.zhangk.babysitter.utils.common.Pagination;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	@Autowired
	private RoleService roleService;

	@RequestMapping("/roleList")
	public String roleList(HttpServletRequest request,
			HttpServletResponse response, Pagination<Role> page) {
		// page.setPageSize(1);
		page = roleService.getPageRoleList(page);
		request.setAttribute("page", page);
		page.setRequestUri(request.getRequestURI());
		return "/role/roleList";
	}

	@ResponseBody
	@RequestMapping("/addRole")
	public PageResult addRole(String roleName) {
		if (StringUtils.isEmpty(roleName)) {
			return getErrRes(2, "信息不全！");
		}
		Role role = Role.getInstance();
		role.setName(roleName);
		roleService.addRole(role);
		return res;
	}

}
