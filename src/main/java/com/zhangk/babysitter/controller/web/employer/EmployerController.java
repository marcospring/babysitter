package com.zhangk.babysitter.controller.web.employer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.utils.common.Pagination;

@Controller
@RequestMapping("/employer")
public class EmployerController extends BaseController {

	@Autowired
	private EmployerService exployerService;

	@RequestMapping("/employerList")
	public String exployerList(HttpServletRequest request,
			HttpServletResponse response, Pagination<Employer> page) {
		page = exployerService.getPageEmployerList(page);
		request.setAttribute("page", page);
		page.setRequestUri(request.getRequestURI());
		return "/employer/employerList";
	}
}
