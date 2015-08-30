package com.zhangk.babysitter.controller.manage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.CustomerManager;
import com.zhangk.babysitter.service.manager.CustomerManagerService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CustomerManagerView;

@Controller
@RequestMapping("/manage/customerManager")
public class ManageCustomerManagerController extends BaseController {
	@Autowired
	private CustomerManagerService managerService;

	@RequestMapping("/go")
	public String go() {
		return "/manage/customManager/customerManager";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest request, int page, int rows,
			String name, String companyName, String countyid, String state) {
		Pagination<CustomerManager> managers = new Pagination<CustomerManager>(
				page, rows);
		Pagination<CustomerManagerView> views = managerService
				.manageCompanyList(managers, name, companyName, countyid, state);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", views.getResult());
		result.put("total", views.getResultSize());
		return result;
	}

	@RequestMapping("/goAdd")
	public Object goAdd(HttpServletRequest request) {

		return "manage/customManager/addCustomerManager";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Object add(String username, String password, String name,
			String telephone, long countyid) {
		ResultInfo result = managerService.manageAddCustomerManager(username,
				password, name, telephone, countyid);
		if (result == ResultInfo.MANAGER_NOT_NULL)
			return MyResponse.errorResponse(
					ResultInfo.MANAGER_NOT_NULL.getCode(),
					ResultInfo.MANAGER_NOT_NULL.getMsg());
		return MyResponse.successResponse();
	}

	@RequestMapping("/goManage")
	public Object goManage(HttpServletRequest request) {

		return "manage/customManager/manageCustomerManager";
	}

	@RequestMapping("/goVerify")
	public Object goVerify(HttpServletRequest request) {

		return "manage/customManager/verifyCustomerManager";
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(String ids) {
		managerService.deleteCustomerManager(ids);
		return MyResponse.successResponse();
	}

	@ResponseBody
	@RequestMapping("/verify")
	public Object verify(String ids, String state) {
		managerService.verify(ids, state);
		return MyResponse.successResponse();
	}

	@RequestMapping("/goEdit")
	public Object goEdit(HttpServletRequest request, String id) {
		CustomerManager manager = managerService.getCustomerManager(id);
		request.setAttribute("vo", manager);
		return "manage/customManager/editCustomerManager";
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Object edit(String id, String password, String name,
			String telephone, long countyid) {
		ResultInfo result = managerService.manageEditCustomerManager(id,
				password, name, telephone, countyid);
		if (result == ResultInfo.MANAGER_NULL)
			return MyResponse.errorResponse(ResultInfo.MANAGER_NULL.getCode(),
					ResultInfo.MANAGER_NULL.getMsg());
		return MyResponse.successResponse();
	}
}
