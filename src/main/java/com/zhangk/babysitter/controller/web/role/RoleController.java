package com.zhangk.babysitter.controller.web.role;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhangk.babysitter.controller.web.BaseController;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	@RequestMapping("/roleList")
	public String roleList() {
		return "/role/roleList";
	}
}
